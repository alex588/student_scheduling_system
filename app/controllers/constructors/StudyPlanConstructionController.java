package controllers.constructors;

import java.util.ArrayList;
import java.util.List;
import controllers.util.Converter;
import model.Course;
import model.Course.Location;
import model.DegreeProgram;
import model.GeneralSettings;
import model.SchedulingResult;
import model.Semester;
import model.StudentDesire;
import model.Term;
import controllers.auth.AuthCheckSecurity;
import controllers.solver.*;
import play.data.Form;
import play.mvc.*;
import views.forms.*;

/**
 * 
 * @author Mihir Daptardar
 * 
 */
@Security.Authenticated(AuthCheckSecurity.class)
public class StudyPlanConstructionController extends Controller {

	public static Result constructStudyPlan() {
		Form<StudentRequirementForm> form = form(StudentRequirementForm.class)
				.bindFromRequest();
		if (form.hasErrors()) {
			return badRequest(views.html.errorPage.render("Errors in form"));
		} else {
			StudentRequirementForm data = form.get();
			// Split term string to give semester and year
			List<String> tempTermList = data.term;
			List<String> sSemesterList = new ArrayList<String>();
			List<Integer> iYearList = new ArrayList<Integer>();
			for (String term : tempTermList) {
				if ((term == null) || term.equals("")) {
					sSemesterList.add("");
					iYearList.add(null);
				} else {
					String[] temp = term.split(" ");
					sSemesterList.add(temp[0]);
					iYearList.add(Integer.parseInt(temp[1]));
				}
			}
			// -------------------

			List<String> sCourseList = data.course;
			List<String> sIsTakenList = data.isTaken;
			List<String> slocation = data.location;

			// Algorithm Standpoint:
			List<StudentDesire> studentDesireList = new ArrayList<>();
			DegreeProgram degreeProgram = DegreeProgram
					.getById(data.degreeProgramId);
			Integer enterYear = data.enterYear;
			Integer noOfSemester = data.numberOfSemesters;
			List<Integer> maxCourses = data.termMax;
			List<Integer> minCourses = data.termMin;

			SchedulingResult result = null;
			Integer dataLength = iYearList.size();
			/*
			 * as year doesnt truncate the arraylist size, it can be used as a
			 * measure of number of courses in a degree program if it starts
			 * truncating, then we need to manually get the list of courses in a
			 * degree program. Courses list truncates all the null values.. so
			 * we add them manually towards the end thats the reason we didnt
			 * chose course list as iterator..
			 */

			for (int i = 0; i < dataLength; i++) {
				// converting to specific data types and populating lists

				// Courses
				Course tempCourse;
				if (sCourseList == null)
					tempCourse = null;
				else if (sCourseList.size() > i
						&& sCourseList.size() != dataLength)
					tempCourse = Converter.parseCourse(sCourseList.get(i));
				else if (sCourseList.size() == dataLength)
					tempCourse = Converter.parseCourse(sCourseList.get(i));
				else
					tempCourse = null;

				// Location
				Location location = (slocation.get(i) != null) ? Location
						.getLocationForString(slocation.get(i)) : Location.BOTH;

				// IsTaken
				Boolean isTaken;
				if (sIsTakenList == null)
					isTaken = false;
				else if (((i < sIsTakenList.size()) && sIsTakenList.get(i) == null)
						|| (i >= sIsTakenList.size()))
					isTaken = false;
				else
					isTaken = true;

				// Semester
				Semester tempSemester;
				if (sSemesterList == null)
					tempSemester = null;
				else
					tempSemester = Semester.findByName(sSemesterList.get(i));

				// Year
				Integer tempYear;
				if (iYearList == null)
					tempYear = null;
				else
					tempYear = iYearList.get(i);

				// Term
				Term tempTerm;
				if (tempSemester != null || tempYear != null)
					tempTerm = Term.create(tempSemester, tempYear);
				else
					tempTerm = null;

				StudentDesire desire = StudentDesire.create(tempCourse,
						isTaken, null, tempTerm, location);

				if (tempCourse != null)
					studentDesireList.add(desire);
				// only add the courses checked.. in other words.. desired
				// courses

			}

			List<Term> termL = Converter.TermGenerator(enterYear, noOfSemester);

			/*
			 * For now, Assumption is as follows:
			 * MaxCourses: 5/semester MinCourses: 0/semester MaxCredits:
			 * 18/semester MinCredits: 0/semester
			 * 
			 * Also the All lists must be of the same sizes
			 */

			List<Integer> maxCredits = new ArrayList<Integer>();
			List<Integer> minCredits = new ArrayList<Integer>();

			for (int i = 0; i < noOfSemester; i++) {
				maxCredits.add(18);
				minCredits.add(0);
			}
			
			GeneralSettings settings = GeneralSettings.create(maxCourses,
					minCourses, maxCredits, minCredits, termL);
			result = ConstraintProcessor.solve(degreeProgram,
					studentDesireList, settings);

			return ok(views.html.studentScheduleResult.render(result,
					studentDesireList));
		}
	}
}
