package controllers.solver;

import model.CoRequisite;
import model.Course;
/**
 * 
 * @author Alexey Tregubov
 * 
 */
class CoreqConstraint extends RequisiteConstraint {

	public CoreqConstraint(CoRequisite req, Course course) {
		super(req, course);
	}

	@Override
	protected CheckResult checkSimple(StudyPlanMatrix matrix) {
		int courseCol = matrix.getCol(this.course);
		int courseInRequisiteCol = matrix.getCol(this.courseInRequisite);
		int courseRow = matrix.findCellEqualsToOne(courseCol);
		if (courseRow == 0)
			return CheckResult.SATISFIED;
		else if (courseRow > 0){
			int sum = matrix.columnSum(courseInRequisiteCol, courseRow, courseRow);
			if (sum > 0)
				return CheckResult.SATISFIED;
			if (sum == 0)
				return CheckResult.NOT_SATISFIED;
			if (sum < 0)
				return CheckResult.UNDEFINED;
			throw new IllegalStateException("This code should never be executed.");
		}
		else
			return CheckResult.UNDEFINED;
	}

}
