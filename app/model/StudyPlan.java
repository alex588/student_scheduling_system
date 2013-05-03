package model;

import java.util.List;

/**
 * @author Alexey Tregubov
 * 
 */
public class StudyPlan {
	protected List<List<Course>> scheduleMatrix;
	protected List<Term> terms;
	protected String forToString;

	protected StudyPlan() {
		super();
	}

	public List<List<Course>> getCourseListsForEachSemester() {
		return scheduleMatrix;
	}

	public List<Term> getAllTerms() {
		return terms;
	}

	public List<Course> getCourseListForTerm(Term term) {
		if (terms.contains(term)) {
			int indexOfSem = terms.indexOf(term);
			return scheduleMatrix.get(indexOfSem);
		}
		return null;
	}

	@Override
	public String toString() {
		return forToString;
	}

}
