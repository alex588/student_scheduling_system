package controllers.solver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import model.CoRequisite;
import model.Course;
import model.DegreeProgram;
import model.DesireForCourse;
import model.GeneralSettings;
import model.PreRequisite;
import model.Requirement;
import model.SimpleRequirement;
import model.StudentDesire;
import model.StudyPlan;
import model.Term;
import model.Course.Location;

/**
 * 
 * @author Alexey Tregubov
 * 
 */
/* package */class StudyPlanMatrix extends StudyPlan {

	/* protected List<Term> terms; // in super class */
	private Cell[][] matrix;
	private List<Course> courses;
	private List<Cell> decisionVariables;
	private DegreeProgram program;
	private List<Requirement> requirements;
	private HashSet<StudentDesire> desires;
	private GeneralSettings settings;
	// stores association requirements with courses (sections):
	private HashMap<Course, RequirementConstraint> associatedReqConstrs;
	private HashSet<RequirementConstraint> reqConstrs;
	private HashSet<IncompleteDesireConstraint> incomplDesireConstrs;
	private HashMap<Course, PrereqConstraint> prereqConstraints;
	private HashMap<Course, CoreqConstraint> coreqConstraints;
	// stores all the courses that were selected during frontracking:
	private HashSet<Course> coursesMarkedAsTaken;
	// stores associated requirements for coursesMarkedAsTaken:
	private HashMap<Course, RequirementConstraint> associatedReqmntConstrsForCoursesMarkedAsTaken;
	private List<List<Course>> sections;

	/**
	 * Cursor is the first not initialized decision variable in
	 * decisionVariables array.
	 */
	private int cursorIndex;

	/**
	 * Creates a matrix S and initializes it. It also sets course availability.
	 * If course is not available then associated cell is set as not taken.
	 * 
	 */
	StudyPlanMatrix(DegreeProgram program, List<StudentDesire> desires,
			GeneralSettings settings) {
		super();
		this.program = program;
		this.desires = new HashSet<StudentDesire>(desires);
		this.settings = settings;
		this.terms = settings.getTerms();
		this.terms.add(0, Term.create(null, null)); // add zero term for courses
													// that were already taken
													// (eg. AP credits). this
													// term will be removed
													// after conversion.
		this.courses = collectCourses();
		// order initialization procedures matters
		reorderRequirements();
		reorderCourses();
		initNewMatrix(); // course availability initialized here.
		initTakenCourses(); // init according student desires.
		initDesiresForCourses();
		this.decisionVariables = collectDecisionVariables();
		this.cursorIndex = 0;
		initRequirementConstrs();
		initIncompleteDesiresConstrs();
		initRequisiteConstrs();
		addCompleteDesiresInTakenCourses();
		this.associatedReqmntConstrsForCoursesMarkedAsTaken = new HashMap<Course, RequirementConstraint>(
				this.terms.size() * this.courses.size());
	}

	public StudyPlan convertToStudyPlan() {
		this.scheduleMatrix = new ArrayList<List<Course>>(this.terms.size());
		int maxRowNum = terms.size();
		int maxColNum = courses.size();
		for (int i = 1; i < maxRowNum; i++) {
			List<Course> row = new ArrayList<Course>(maxColNum);
			for (int j = 0; j < maxColNum; j++) {
				Cell curCell = matrix[i][j];
				if (curCell.isInitialized() && curCell.isTaken())
					row.add(courses.get(j));
			}
			scheduleMatrix.add(row);
		}
		this.forToString = this.matrixToString();
		this.terms.remove(0);
		return this;
	}

	/**
	 * 
	 * @return current position of cursor.
	 */
	public int getCursorIndex() {
		return this.cursorIndex;
	}

	public Cell getCursorCell() {
		return this.decisionVariables.get(this.cursorIndex);
	}

	public Course getCursorCourse() {
		Cell curCell = getCursorCell();
		return courses.get(curCell.getCol());
	}

	private Cell getLastAssignedCell() {
		if (this.cursorIndex > 0)
			return this.decisionVariables.get(this.cursorIndex - 1);
		else
			return null;
	}

	// //////////////////////////////////////////////
	// constraint satisfaction check
	// //////////////////////////////////////////////

	// General constraint check section
	public boolean checkRowConstrs() {
		Cell lastAssignedCell = getLastAssignedCell();
		if (lastAssignedCell != null) {
			int rowNum = lastAssignedCell.getRow();
			int maxColNum = courses.size();
			int numberOfCoursesTaken = 0;
			int numberOfCreditsTaken = 0;
			for (int j = 0; j < maxColNum; j++) {
				Cell curCell = matrix[rowNum][j];
				numberOfCoursesTaken += curCell.value();
				if (curCell.isInitialized() && curCell.isTaken())
					numberOfCreditsTaken += this.courses.get(j).getCredits();
			}
			if ((numberOfCoursesTaken < this.settings
					.getMinNumberOfCoursesPerSemester().get(rowNum - 1))
			// - 1 because of the first row (first row is for taken courses)
					|| (numberOfCoursesTaken > this.settings
							.getMaxNumberOfCoursesPerSemester().get(rowNum - 1)))
				return false;
			if ((numberOfCreditsTaken < this.settings
					.getMinNumberOfCreditsPerSemester().get(rowNum - 1))
					&& (numberOfCreditsTaken > this.settings
							.getMaxNumberOfCreditsPerSemester().get(rowNum - 1)))
				return false;
		}
		return true;
	}

	public boolean checkColConstrs() {
		Cell lastAssignedCell = getLastAssignedCell();
		if (lastAssignedCell != null) {
			int colNum = lastAssignedCell.getCol();
			int maxRowNum = terms.size();
			int numberOfCoursesTaken = 0;
			for (int i = 0; i < maxRowNum; i++) {
				Cell curCell = matrix[i][colNum];
				numberOfCoursesTaken += curCell.value();
			}
			if (numberOfCoursesTaken > 1)
				return false;
		}
		return true;
	}

	// Degree requirements check section
	/**
	 * checks constraints consistency
	 * 
	 * @return true if all degree requirement constraints satisfied or cannot be
	 *         checked. Otherwise returns false
	 */
	public boolean checkRequirementConstrs() {
		for (RequirementConstraint constr : this.reqConstrs)
			if (constr.check(this) == CheckResult.NOT_SATISFIED)
				return false;
		return true;
	}

	public boolean checkRequirementConstrs(Course course) {
		RequirementConstraint constr = this.associatedReqConstrs.get(course);
		if (constr != null)
			if (constr.check(this) == CheckResult.SATISFIED)
				return true;
			else
				return false;
		return false;
	}

	/**
	 * checks if all prerequisites and corequisites are satisfied for courses
	 * that are marked as taken.
	 * 
	 * @return
	 */
	public boolean checkReuisiteConstrs() {
		for (Course course : this.coursesMarkedAsTaken) {
			IConstraint constr = this.prereqConstraints.get(course);
			if ((constr != null)
					&& (constr.check(this) == CheckResult.NOT_SATISFIED))
				return false;
			constr = this.coreqConstraints.get(course);
			if ((constr != null)
					&& (constr.check(this) == CheckResult.NOT_SATISFIED))
				return false;
		}
		return true;
	}

	// Degree student desires for courses (incomplete desires)
	public boolean checkStudentDesiresConstrs() {
		for (IConstraint constr : this.incomplDesireConstrs)
			if (constr.check(this) == CheckResult.NOT_SATISFIED)
				return false;
		return true;
	}

	// /////////////////////////////////////////////

	// //////////////////////////////////////////////
	// constraint satisfaction check
	// //////////////////////////////////////////////
	/**
	 * 
	 * @return true if all decision variables have value.
	 */
	public boolean isMatixComplete() {
		if (this.decisionVariables.size() > 0) {
			if (this.cursorIndex == this.decisionVariables.size())
				return true;
			return false;
		} else {
			if (this.decisionVariables.size() == 0)
				return true;
		}
		throw new IllegalStateException(
				"Decision variable not initialized yet.");
	}

	/**
	 * Initializes courses that were taken in the past (first row of matrix S,
	 * see algorithm description for details.)
	 * 
	 * @param desires
	 */
	private void initTakenCourses() {
		for (StudentDesire desire : desires) {
			if (desire.isTaken()) {
				Course takenCourse = desire.getCourse();
				int col = courses.indexOf(takenCourse);
				if (col != -1) {
					for (int i = 0; i < matrix.length; i++) {
						if (i == 0)
							this.matrix[i][col].take();
						else
							this.matrix[i][col].skip();
					}
				}
			}
		}
	}

	/**
	 * Initializes student desires for courses in the matrix. If desire is
	 * complete (term != null) then associated cell is marked as taken (assigned
	 * to 1)
	 * 
	 */
	private void initDesiresForCourses() {
		for (StudentDesire desire : desires) {
			if (desire instanceof DesireForCourse) {
				DesireForCourse desireForCourse = (DesireForCourse) desire;
				if (desireForCourse.isComplete()) {
					int col = courses.indexOf(desireForCourse.getCourse());
					int row = terms.indexOf(desireForCourse.getTerm());
					if (col != -1) {
						for (int i = 0; i < terms.size(); i++)
							this.matrix[i][col].skip();
						if (row != -1) {
							this.matrix[row][col].take();
						}
					}
				}
			}
		}
		return;
	}

	/**
	 * Initializes matrix S and course unavailability.
	 * 
	 */
	private void initNewMatrix() {
		this.matrix = new Cell[terms.size()][courses.size()];
		int maxRowNum = terms.size();
		int maxColNum = courses.size();
		for (int j = 0; j < maxColNum; j++) {
			Course course = courses.get(j);
			for (int i = 0; i < maxRowNum; i++) {
				matrix[i][j] = new Cell(j, i);
				if ((i == 0)
						|| !course.isAvailable(terms.get(i), Location.BOTH)) {
					matrix[i][j].skip();
				}
			}
		}
		return;
	}

	private class RequirementComparator implements Comparator<Requirement> {
		/**
		 * requirements with bigger number of courses come first.
		 */
		@Override
		public int compare(Requirement req1, Requirement req2) {
			int numberOfCoursesInReq1 = req1.getCourses().size();
			int numberOfCoursesInReq2 = req2.getCourses().size();

			if (req1.isSimple() && req2.isSimple()) {
				int numberOfCoursesRequired1 = ((SimpleRequirement) req1)
						.getNumberOfCourses();
				int numberOfCoursesRequired2 = ((SimpleRequirement) req2)
						.getNumberOfCourses();
				int delta1 = numberOfCoursesInReq1 - numberOfCoursesRequired1;
				int delta2 = numberOfCoursesInReq2 - numberOfCoursesRequired2;

				if ((delta1 == 0) && (delta2 == 0)) {
					return (numberOfCoursesInReq1 < numberOfCoursesInReq2) ? -1
							: ((numberOfCoursesInReq1 == numberOfCoursesInReq2) ? 0
									: 1);
				} else if ((delta1 == 0) && (delta2 != 0)) {
					return 1;
				} else if ((delta1 != 0) && (delta2 == 0)) {
					return -1;
				} else /* ((delta1 != 0) && (delta2 != 0)) */{
					return (numberOfCoursesInReq1 < numberOfCoursesInReq2) ? -1
							: ((numberOfCoursesInReq1 == numberOfCoursesInReq2) ? 0
									: 1);
				}
			} else if (!req1.isSimple() && !req2.isSimple()) {
				return (numberOfCoursesInReq1 < numberOfCoursesInReq2) ? -1
						: ((numberOfCoursesInReq1 == numberOfCoursesInReq2) ? 0
								: 1);
			} else if (req1.isSimple() && !req2.isSimple()) {
				// int numberOfCoursesRequired1 =
				// ((SimpleRequirement)req1).getNumberOfCourses();
				return 1;
			} else /* (!req1.isSimple() && req2.isSimple()) */{
				// int numberOfCoursesRequired2 =
				// ((SimpleRequirement)req2).getNumberOfCourses();
				return -1;
			}
		}
	}

	private void reorderRequirements() {
		this.requirements = new ArrayList<Requirement>();
		requirements.addAll(this.program.getRequirements());
		Collections.sort(requirements, new RequirementComparator());
		return;
	}

	/**
	 * reordering of courses (columns of the matrix) according to requirements
	 * and chronological order of the semesters
	 */

	private void reorderCourses() {
		this.sections = new ArrayList<List<Course>>(
				this.requirements.size() + 1);
		List<Course> newListOfCourses = new ArrayList<Course>(
				this.courses.size());
		Set<Course> alreadyUsedReqs = new HashSet<Course>(this.courses.size());
		for (Requirement req : this.requirements) {
			List<Course> section = new ArrayList<Course>(16);
			for (Course course : req.getCourses()) {
				// prereqs
				PreRequisite prereq = course.getPreRequisite();
				if (prereq != null) {
					List<Course> prereqCourses = prereq
							.getAllCoursesIncludingSubReqs();
					for (Course prereqCourse : prereqCourses)
						if ((!alreadyUsedReqs.contains(prereqCourse))
								&& (!section.contains(prereqCourse))) {
							newListOfCourses.add(prereqCourse);
							section.add(prereqCourse);
							alreadyUsedReqs.add(prereqCourse);
						}
				}
				// coreqs
				CoRequisite coreq = course.getCoRequisite();
				if (coreq != null) {
					List<Course> coreqCourses = coreq
							.getAllCoursesIncludingSubReqs();
					for (Course coreqCourse : coreqCourses)
						if ((!alreadyUsedReqs.contains(coreqCourse))
								&& (!section.contains(coreqCourse))) {
							newListOfCourses.add(coreqCourse);
							section.add(coreqCourse);
							alreadyUsedReqs.add(coreqCourse);
						}
				}
				if (!alreadyUsedReqs.contains(course)) {
					newListOfCourses.add(course);
					section.add(course);
					alreadyUsedReqs.add(course);
				}
			}
			this.sections.add(section);
		}
		this.courses = newListOfCourses;
		return;
	}

	/**
	 * 
	 * @return list of all cells that are not initialized.
	 */
	private List<Cell> collectDecisionVariables() {
		List<Cell> decVar = new ArrayList<Cell>();
		for (List<Course> section : this.sections) {
			for (Term term : this.terms) {
				int row = this.terms.indexOf(term);
				for (Course course : section) {
					int col = this.courses.indexOf(course);
					Cell curCell = this.matrix[row][col];
					if (!curCell.isInitialized())
						decVar.add(curCell);
				}
			}
		}
		return decVar;
	}

	private String matrixToString() {
		int maxRowNum = terms == null ? 0 : terms.size();
		int maxColNum = courses == null ? 0 : courses.size();
		StringBuilder strBldr = new StringBuilder();
		strBldr.append("    ");
		for (int j = 0; j < maxColNum; j++) {
			strBldr.append(courses.get(j).getTitle());
			strBldr.append("   ");
		}
		strBldr.append("\n");
		for (int i = 0; i < maxRowNum; i++) {
			strBldr.append(terms.get(i).toShortString());
			strBldr.append(" ");
			for (int j = 0; j < maxColNum; j++) {
				Cell cell = matrix[i][j];
				strBldr.append(cell.toString());
				strBldr.append("   ");
			}
			strBldr.append("\n");
		}
		return strBldr.toString();
	}

	/**
	 * 
	 * @param program
	 * @return all courses that are covered by all the requirement in the
	 *         program. Requisites of these courses also included.
	 */
	private List<Course> collectCourses() {
		Set<Course> courses = new HashSet<Course>();
		Set<Course> coursesFromRequisites = new HashSet<Course>();
		for (Requirement req : program.getRequirements()) {
			courses.addAll(req.getCourses());
		}
		for (Course course : courses) {
			PreRequisite prereq = course.getPreRequisite();
			if (prereq != null) {
				List<Course> courseList = prereq
						.getAllCoursesIncludingSubReqs();
				coursesFromRequisites.addAll(courseList);
			}
			CoRequisite coreq = course.getCoRequisite();
			if (coreq != null) {
				List<Course> courseList = coreq.getAllCoursesIncludingSubReqs();
				coursesFromRequisites.addAll(courseList);
			}
		}
		courses.addAll(coursesFromRequisites);
		return Arrays.asList(courses.toArray(new Course[courses.size()]));
	}

	// //////////////////////////////////////////////

	// /////////////////////////////////////////////
	// constraints initialization
	// /////////////////////////////////////////////
	/**
	 * Initializes constraints set for courses marked as taken in complete
	 * desires.
	 */
	private void addCompleteDesiresInTakenCourses() {
		this.coursesMarkedAsTaken = new HashSet<Course>(this.courses.size());
		for (StudentDesire desire : this.desires) {
			if (!desire.isTaken()) {
				DesireForCourse desireForCourse = (DesireForCourse) desire;
				if (desireForCourse.isComplete())
					this.coursesMarkedAsTaken.add(desireForCourse.getCourse());
			}
		}
	}

	/**
	 * Initializes constraints associated with prerequisites and corequisites of
	 * all courses
	 */
	private void initRequisiteConstrs() {
		this.prereqConstraints = new HashMap<Course, PrereqConstraint>(
				this.courses.size());
		this.coreqConstraints = new HashMap<Course, CoreqConstraint>(
				this.courses.size());
		for (Course course : this.courses) {
			PreRequisite prereq = course.getPreRequisite();
			if (prereq != null)
				this.prereqConstraints.put(course, new PrereqConstraint(prereq,
						course));
			CoRequisite coreq = course.getCoRequisite();
			if (coreq != null)
				this.coreqConstraints.put(course, new CoreqConstraint(coreq,
						course));
		}
	}

	/**
	 * Initializes constraints associated with incomplete student desires for
	 * courses. Desire is incomplete if semester is not specified.
	 */
	private void initIncompleteDesiresConstrs() {
		this.incomplDesireConstrs = new HashSet<IncompleteDesireConstraint>(
				this.desires.size());
		for (StudentDesire desire : this.desires) {
			if (!desire.isTaken()) {
				DesireForCourse desireForCourse = (DesireForCourse) desire;
				if (!desireForCourse.isComplete()) {
					IncompleteDesireConstraint constr = new IncompleteDesireConstraint(
							desireForCourse);
					this.incomplDesireConstrs.add(constr);
					// update course availability according to desire
					// (considering location).
					int maxRowNum = terms.size();
					int col = this.getCol(desireForCourse.getCourse());
					for (int i = 0; i < maxRowNum; i++) {
						Course course = courses.get(col);
						if (!course.isAvailable(terms.get(i),
								desireForCourse.getLocation()))
							matrix[i][col].skip();
					}
				}
			}
		}
	}

	/**
	 * Initializes constraints associated with degree requirements.
	 */
	private void initRequirementConstrs() {
		this.reqConstrs = new HashSet<RequirementConstraint>(
				this.requirements.size());
		this.associatedReqConstrs = new HashMap<Course, RequirementConstraint>(
				this.requirements.size());
		for (Requirement req : this.requirements) {
			RequirementConstraint constr = new RequirementConstraint(req, true);
			this.reqConstrs.add(constr);
			for (Course course : req.getCourses()) {
				if (!this.associatedReqConstrs.containsKey(course))
					this.associatedReqConstrs.put(course, constr);
			}
		}
	}

	// /////////////////////////////////////////////

	// /////////////////////////////////////////////
	// operations that are used in constraints
	// /////////////////////////////////////////////

	/**
	 * Calculates sum of cells in the specified column (course) Cell == 1 if
	 * course is taken and cell == 0 if course is not take. If cell is not
	 * initialized yet than it is ignored (cell == 0). Both end of the interval
	 * are included in sum.
	 * 
	 * @param col
	 *            column number
	 * @param startRow
	 *            index of the first row sum
	 * @param endRow
	 *            index of the last row in sum
	 * @return (sum > 0) ? sum : numberOfNotInitizlizedValues; where
	 *         numberOfNotInitizlizedValues is negative
	 */
	public int columnSum(int col, int startRow, int endRow) {
		int sum = 0;
		int numberOfNotInitizlizedValues = 0;
		for (int i = startRow; i <= endRow; i++) {
			Cell curCell = matrix[i][col];
			sum += curCell.value();
			if (!curCell.isInitialized())
				numberOfNotInitizlizedValues--;
		}
		return (sum > 0) ? sum : numberOfNotInitizlizedValues;
	}

	/**
	 * checks if course is taken.
	 * 
	 * @param course
	 * @return
	 */
	public CheckResult isTaken(Course course) {
		int col = courses.indexOf(course);
		boolean haveNotInitializedValues = false;
		int maxRowNum = terms.size();
		for (int i = 0; i < maxRowNum; i++) {
			if (matrix[i][col].isInitialized() && matrix[i][col].isTaken())
				return CheckResult.SATISFIED;
			if (!matrix[i][col].isInitialized())
				haveNotInitializedValues = true;
		}
		if (haveNotInitializedValues)
			return CheckResult.UNDEFINED;
		else
			return CheckResult.NOT_SATISFIED;
	}

	public CheckResult isTakenInRequirement(Course course,
			RequirementConstraint constr) {
		RequirementConstraint req = this.associatedReqmntConstrsForCoursesMarkedAsTaken
				.get(course);
		if (req != null)
			if (req.equals(constr))
				return CheckResult.SATISFIED;
			else
				return CheckResult.NOT_SATISFIED;
		else
			return isTaken(course);
	}

	public int getCol(Course course) {
		return courses.indexOf(course);
	}

	/**
	 * 
	 * @param col
	 *            column
	 * @return row number where the cell is one. Otherwise returns -1
	 */
	public int findCellEqualsToOne(int col) {
		int maxRowNum = terms.size();
		for (int i = 0; i < maxRowNum; i++) {
			if (matrix[i][col].isTaken())
				return i;
		}
		return -1;
	}

	/**
	 * put zero in the next decision variable
	 * 
	 * @return current position of cursor
	 */
	public int putZeroInTheNextCell() {
		Cell currentCell = this.decisionVariables.get(cursorIndex);
		currentCell.skip();
		Course course = this.courses.get(currentCell.getCol());
		this.cursorIndex++;
		this.associatedReqmntConstrsForCoursesMarkedAsTaken.remove(course);
		return this.cursorIndex;
	}

	/**
	 * put one in the next decision variable
	 * 
	 * @return current position of cursor
	 */
	public int putOneInTheNextCell() {
		Cell currentCell = this.decisionVariables.get(cursorIndex);
		currentCell.take();
		Course course = this.courses.get(currentCell.getCol());
		this.coursesMarkedAsTaken.add(course);
		this.cursorIndex++;
		RequirementConstraint constr = this.associatedReqConstrs.get(course);
		this.associatedReqmntConstrsForCoursesMarkedAsTaken.put(course, constr);
		return this.cursorIndex;
	}

	/**
	 * 
	 * @return current position of cursor
	 */
	public int rollBackLastAssignment() {
		if ((cursorIndex > 0) && (decisionVariables != null)) {
			this.cursorIndex--;
			Cell currentCell = this.decisionVariables.get(cursorIndex);
			Course course = this.courses.get(currentCell.getCol());
			this.coursesMarkedAsTaken.remove(course);
			currentCell.reset();
		} else {
			if ((decisionVariables == null) || (decisionVariables.size() == 0))
				throw new IllegalStateException(
						"Decision variables have not beet initialized yet.");
			else
				throw new IllegalStateException(
						"Cursor in zero position. Nothing to rollback.");
		}
		return this.cursorIndex;
	}

	// /////////////////////////////////////////////

}
