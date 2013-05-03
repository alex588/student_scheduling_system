package controllers.solver;

import model.Course;
import model.DesireForCourse;
import model.Requirement;

/**
 * 
 * @author Alexey Tregubov
 * 
 */
class IncompleteDesireConstraint extends Constraint {

	private static int idCounter = 1;
	private Course course;
	private RequirementConstraint constr;

	public IncompleteDesireConstraint(DesireForCourse desire) {
		super(idCounter++, desire.getCourse().toString());
		if (idCounter > 100000)
			idCounter = 1;
		if (desire.isTaken())
			throw new IllegalArgumentException(
					"Incomplete desire for course expected. Recieved: "
							+ desire.toString());
		if (desire.isComplete())
			throw new IllegalArgumentException(
					"Incomplete desire for course expected. Complete desire received");
		this.course = desire.getCourse();
		Requirement req = desire.getRequirement();
		if (req != null)
			this.constr = new RequirementConstraint(req, true);
		else
			this.constr = null;
	}

	@Override
	public CheckResult check(StudyPlanMatrix matrix) {
		return matrix.isTaken(course);
	}

	/**
	 * 
	 * @return associated constraint if student specified for what degree
	 *         requirement this course should be used. Returns null if not
	 *         student didn't specify this.
	 */
	public RequirementConstraint getAssociatedConstraint() {
		return this.constr;
	}
}
