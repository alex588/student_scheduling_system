package controllers.solver;

import model.Course;
import model.PreRequisite;
/**
 * 
 * @author Alexey Tregubov
 * 
 */
class PrereqConstraint extends RequisiteConstraint {

	public PrereqConstraint(PreRequisite req, Course course) {
		super(req, course);
	}

	@Override
	protected CheckResult checkSimple(StudyPlanMatrix matrix) {
		int courseCol = matrix.getCol(this.course);
		int courseInRequisiteCol = matrix.getCol(this.courseInRequisite);
		int endRow = matrix.findCellEqualsToOne(courseCol) - 1;
		if (endRow >= 0){
			int sum = matrix.columnSum(courseInRequisiteCol, 0, endRow);
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
