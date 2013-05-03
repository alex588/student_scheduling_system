package controllers.solver;

import java.util.List;
import model.Course;
import model.DegreeProgram;
import model.GeneralSettings;
import model.SchedulingResult;
import model.StudentDesire;
import model.StudyPlan;

/**
 * 
 * @author Alexey Tregubov
 * 
 */
public class ConstraintProcessor {
	private static long TIME_OUT_LENTH = 60000; // in milliseconds
	private static long startTime;
	private static boolean stopFlag;

	public static SchedulingResult solve(DegreeProgram program,
			List<StudentDesire> desires, GeneralSettings settings) {
		startTime = System.currentTimeMillis();
		stopFlag = false;

		StudyPlanMatrix matrix;
		SchedulingResult res;
		try {
			matrix = new StudyPlanMatrix(program, desires, settings);
			boolean isFound = backtrack(matrix);
			StudyPlan schedule = matrix.convertToStudyPlan();
			System.out.println(schedule.toString());
			if (isFound) {
				res = SchedulingResult.create(
						"Study plan constructed successfully", schedule,
						isFound);
			} else
				res = SchedulingResult
						.create(stopFlag ? "No valid study plan found: stopped by time out"
								: "No valid study plan found.", matrix
								.convertToStudyPlan(), isFound);

			return res;
		} catch (IllegalArgumentException e) {
			res = SchedulingResult.create(e.toString(), null, false);
			return res;

		}
	}

	/**
	 * 
	 * @param matrix
	 *            - contains current state of the schedule and all the
	 *            constraints.
	 * @return
	 */
	private static boolean backtrack(StudyPlanMatrix matrix) {
		long currTime = System.currentTimeMillis();
		if ((currTime - startTime) > TIME_OUT_LENTH) {
			stopFlag = true;
			return false;
		}
		if (matrix.isMatixComplete())
			return true;
		Course course = matrix.getCursorCourse();
		if (!matrix.checkRequirementConstrs(course)) {
			matrix.putOneInTheNextCell();
			if (consistencyCechIfCourseTaken(matrix))
				if (backtrack(matrix))
					return true;
				else if (stopFlag)
					return false;
			matrix.rollBackLastAssignment();
			matrix.putZeroInTheNextCell();
			if (consistencyCechIfCourseSkipped(matrix))
				if (backtrack(matrix))
					return true;
				else if (stopFlag)
					return false;
			matrix.rollBackLastAssignment();
		} else {
			matrix.putZeroInTheNextCell();
			if (consistencyCechIfCourseSkipped(matrix))
				if (backtrack(matrix))
					return true;
				else if (stopFlag)
					return false;
			matrix.rollBackLastAssignment();
			matrix.putOneInTheNextCell();
			if (consistencyCechIfCourseTaken(matrix))
				if (backtrack(matrix))
					return true;
				else if (stopFlag)
					return false;
			matrix.rollBackLastAssignment();
		}
		return false;
	}

	private static boolean consistencyCechIfCourseTaken(StudyPlanMatrix matrix) {
		if (!matrix.checkRowConstrs())
			return false;
		if (!matrix.checkColConstrs())
			return false;
		if (!matrix.checkReuisiteConstrs())
			return false;
		if (!matrix.checkRequirementConstrs())
			return false;
		if (!matrix.checkStudentDesiresConstrs())
			return false;
		return true;
	}

	private static boolean consistencyCechIfCourseSkipped(StudyPlanMatrix matrix) {
		if (!matrix.checkRowConstrs())
			return false;
		if (!matrix.checkReuisiteConstrs())
			return false;
		if (!matrix.checkRequirementConstrs())
			return false;
		if (!matrix.checkStudentDesiresConstrs())
			return false;
		return true;
	}

}
