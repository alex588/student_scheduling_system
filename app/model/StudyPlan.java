package model;

import java.util.List;

public class StudyPlan {
	protected List<List<Course>> scheduleMatrix;
	protected List<Term> terms;
	
	protected StudyPlan() {
	
	}

	List<List<Course>> getCourseListsForEachSemester() {
		return scheduleMatrix;
	}

	List<Term> getAllTerms() {
		return terms;
	}

	List<Course> getCourseListForTerm(Term term) {
		if (terms.contains(term)){
			int indexOfSem = terms.indexOf(term);
			return scheduleMatrix.get(indexOfSem);
		}
		return null;
	}
}
