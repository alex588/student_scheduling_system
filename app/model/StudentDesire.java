package model;

import model.Course.Location;

/**
 * @author Tregubov Alexey
 */
public class StudentDesire {
	private Course course;
	private boolean isTaken;
	private Requirement req;
	private Location location;

	protected StudentDesire() {
		super();
	}

	/**
	 * Object that represents students desires for study plan
	 * 
	 * @param course
	 *            - course that student has taken or wants to take
	 * @param term
	 *            - term when course must be taken. If null then this argument
	 *            ignored (incomplete desire).
	 * @param isTaken
	 *            true if this object represents course that student has taken.
	 * @param req
	 *            - associated requirement. Student specifies for what
	 *            requirement this course should be used. If requirement not
	 *            specified then null expected.
	 * @return
	 */
	public static StudentDesire create(Course course, boolean isTaken,
			Requirement req, Term term, Location location) {
		StudentDesire desire = (!isTaken) ? new DesireForCourse(term)
				: new StudentDesire();
		desire.course = course;
		desire.isTaken = isTaken;
		desire.req = req;
		desire.location = location;
		return desire;
	}

	public Course getCourse() {
		return course;
	}

	/**
	 * if true then it mean that this course has been taken by the student in
	 * the past.
	 * 
	 * @return
	 */
	public boolean isTaken() {
		return isTaken;
	}

	/**
	 * Student can explicitly specify what requirement should be specified by
	 * this course.
	 * 
	 * @return requirement associated with this desire.
	 */
	public Requirement getRequirement() {
		return this.req;
	}

	@Override
	public int hashCode() {
		return this.course == null ? 0 : this.course.hashCode();
	}

	public Location getLocation() {
		return location;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof StudentDesire) {
			StudentDesire anotherObject = (StudentDesire) o;
			if ((this.isTaken == anotherObject.isTaken)
					&& ((this.course != null && this.course
							.equals(anotherObject.course)) || (this.course == anotherObject.course)))
				return true;
			else
				return false;
		}
		return false;
	}

	@Override
	public String toString() {
		if (this.isTaken)
			return "Coures " + this.course + "has been taken.";
		else
			return "Desire for " + this.course;
	}
}
