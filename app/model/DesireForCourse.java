package model;

/**
 * @author Tregubov Alexey
 */
public class DesireForCourse extends StudentDesire {

	private Term term;

	protected DesireForCourse(Term term) {
		this.term = term;
	}

	/**
	 * Desire is complete if student specified the term (semester and year) when
	 * he/she wants to take the course.
	 * 
	 * @return
	 */
	public Boolean isComplete() {
		if (this.getTerm() == null)
			return false;
		return true;
	}

	public Term getTerm() {
		return term;
	}

}
