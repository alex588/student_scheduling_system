package controllers.solver;

import java.util.ArrayList;
import java.util.List;
import model.ComplexRequirement;
import model.Course;
import model.Junction;
import model.Requirement;
import model.RequirementFormulaNode;
import model.SimpleRequirement;

/**
 * 
 * @author Alexey Tregubov
 * 
 */
/* package */class RequirementConstraint extends Constraint {

	// general
	private boolean isPositive;
	private boolean isSimple;
	// if simple
	private List<Course> courseGroup;
	private int numberOfCourses;
	private boolean isMandatory;
	// if complex
	private Junction junction;
	private List<RequirementConstraint> children;

	public RequirementConstraint(Requirement requirement, Boolean isPositive) {
		super(requirement.getId(), requirement.getTitle());
		this.isPositive = isPositive;
		if (requirement.isSimple()) {
			this.isSimple = true;
			this.courseGroup = ((SimpleRequirement) requirement).getCourses();
			this.numberOfCourses = ((SimpleRequirement) requirement)
					.getNumberOfCourses();
			this.isMandatory = (this.numberOfCourses == this.courseGroup.size()) ? true
					: false;
			this.junction = null;
			this.children = null;
		} else {
			this.isSimple = false;
			this.courseGroup = null;
			this.numberOfCourses = -1;
			this.junction = ((ComplexRequirement) requirement).getJunction();
			List<RequirementFormulaNode> formulaNodes = ((ComplexRequirement) requirement)
					.getChildrenNodes();
			this.children = new ArrayList<RequirementConstraint>(
					formulaNodes.size());
			for (RequirementFormulaNode node : formulaNodes) {
				Requirement req = node.getRequirement();
				Boolean isNodePositive = node.isPositive();
				RequirementConstraint childConstr = new RequirementConstraint(
						req, isNodePositive);
				this.children.add(childConstr);
			}
		}
	}

	@Override
	public CheckResult check(StudyPlanMatrix matrix) {
		if (this.isSimple) {
			int numberOfCoursesFound = 0;
			int numberOfUndefinedFound = 0;
			for (Course course : this.courseGroup) {
				CheckResult isCourseTaken = matrix.isTakenInRequirement(course,
						this);
				if (isCourseTaken == CheckResult.SATISFIED)
					numberOfCoursesFound++;
				if (isCourseTaken == CheckResult.UNDEFINED)
					numberOfUndefinedFound++;
				if (this.isMandatory
						&& (isCourseTaken == CheckResult.NOT_SATISFIED))
					return CheckResult.NOT_SATISFIED;
			}
			if (numberOfCoursesFound >= numberOfCourses)
				return this.isPositive ? CheckResult.SATISFIED
						: CheckResult.NOT_SATISFIED;
			if (numberOfUndefinedFound > 0)
				return CheckResult.UNDEFINED;
			else
				return this.isPositive ? CheckResult.NOT_SATISFIED
						: CheckResult.SATISFIED;
		} else {
			if (this.junction == Junction.AND) {
				for (RequirementConstraint constr : this.children) {
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
				for (RequirementConstraint constr : this.children) {
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
}
