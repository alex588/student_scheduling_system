package controllers.solver;

import java.util.ArrayList;
import java.util.List;
import model.CoRequisite;
import model.Course;
import model.Junction;
import model.PreRequisite;
import model.Requisite;

/**
 * 
 * @author Alexey Tregubov
 * 
 */
abstract class RequisiteConstraint extends Constraint {

	// general
	private boolean isSimple;
	protected boolean isPositive;
	protected Course course;
	// if simple
	protected Course courseInRequisite;
	// if complex
	private Junction junction;
	private List<RequisiteConstraint> children;

	protected RequisiteConstraint(Requisite req, Course course) {
		super(req.getId(), req.toString());
		// general
		this.isSimple = req.getIsLeaf();
		this.isPositive = req.getIsPositive();
		this.course = course;
		// if simple
		if (this.isSimple)
			this.courseInRequisite = req.getCourse();
		// if complex
		else {
			this.junction = req.getJunction();
			this.children = new ArrayList<RequisiteConstraint>(req
					.getChildren().size());
			for (Requisite requisite : req.getChildren()) {
				RequisiteConstraint constr;
				if (requisite instanceof PreRequisite)
					constr = new PrereqConstraint((PreRequisite) requisite,
							course);
				else
					constr = new CoreqConstraint((CoRequisite) requisite,
							course);
				this.children.add(constr);
			}
		}
	}

	/**
	 * this method implies that course is taken. So there must be one cell in
	 * the matrix for this course that marked as taken.
	 */
	@Override
	public CheckResult check(StudyPlanMatrix matrix) {
		if (this.isSimple)
			return checkSimple(matrix);
		else {
			if (this.junction == Junction.AND) {
				for (RequisiteConstraint constr : this.children) {
					CheckResult isChildSatisfied = constr.check(matrix);
					if (isChildSatisfied == CheckResult.SATISFIED)
						continue;
					if (isChildSatisfied == CheckResult.NOT_SATISFIED)
						return this.isPositive ? CheckResult.NOT_SATISFIED
								: CheckResult.SATISFIED;
					if (isChildSatisfied == CheckResult.UNDEFINED)
						return CheckResult.UNDEFINED;
				}
				return this.isPositive ? CheckResult.SATISFIED
						: CheckResult.NOT_SATISFIED;
			}
			if (this.junction == Junction.OR) {
				int numberOfUndefinedFound = 0;
				for (RequisiteConstraint constr : this.children) {
					CheckResult isChildSatisfied = constr.check(matrix);
					if (isChildSatisfied == CheckResult.SATISFIED)
						return this.isPositive ? CheckResult.SATISFIED
								: CheckResult.NOT_SATISFIED;
					if (isChildSatisfied == CheckResult.NOT_SATISFIED)
						continue;
					if (isChildSatisfied == CheckResult.UNDEFINED)
						numberOfUndefinedFound++;
				}
				if (numberOfUndefinedFound > 0)
					return CheckResult.UNDEFINED;
				else
					return this.isPositive ? CheckResult.NOT_SATISFIED
							: CheckResult.SATISFIED;
			}
			throw new IllegalStateException(
					"Unexpected junction type received: " + this.junction);
		}
	}

	protected abstract CheckResult checkSimple(StudyPlanMatrix matrix);
}
