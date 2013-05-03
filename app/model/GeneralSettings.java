package model;

import java.util.List;

/**
 * @author Alexey Tregubov
 */
public class GeneralSettings {
	private List<Integer> maxNumberOfCoursesPerSemester;
	private List<Integer> minNumberOfCoursesPerSemester;
	private List<Integer> maxNumberOfCreditsPerSemester;
	private List<Integer> minNumberOfCreditsPerSemester;
	private List<Term> terms;

	protected GeneralSettings(List<Integer> maxNumberOfCoursesPerSemester,
			List<Integer> minNumberOfCoursesPerSemester,
			List<Integer> maxNumberOfCreditsPerSemester,
			List<Integer> minNumberOfCreditsPerSemester, List<Term> terms) {
		super();
		this.maxNumberOfCoursesPerSemester = maxNumberOfCoursesPerSemester;
		this.minNumberOfCoursesPerSemester = minNumberOfCoursesPerSemester;
		this.maxNumberOfCreditsPerSemester = maxNumberOfCreditsPerSemester;
		this.minNumberOfCreditsPerSemester = minNumberOfCreditsPerSemester;
		this.terms = terms;
	}

	public static GeneralSettings create(List<Integer> maxNumberOfCoursesPerSemester,
			List<Integer> minNumberOfCoursesPerSemester,
			List<Integer> maxNumberOfCreditsPerSemester,
			List<Integer> minNumberOfCreditsPerSemester, List<Term> terms) {
		return new GeneralSettings(maxNumberOfCoursesPerSemester,
				minNumberOfCoursesPerSemester, maxNumberOfCreditsPerSemester,
				minNumberOfCreditsPerSemester, terms);
	}

	public List<Integer> getMaxNumberOfCoursesPerSemester() {
		return maxNumberOfCoursesPerSemester;
	}

	public List<Integer> getMinNumberOfCoursesPerSemester() {
		return minNumberOfCoursesPerSemester;
	}

	public List<Integer> getMaxNumberOfCreditsPerSemester() {
		return maxNumberOfCreditsPerSemester;
	}

	public List<Integer> getMinNumberOfCreditsPerSemester() {
		return minNumberOfCreditsPerSemester;
	}

	public List<Term> getTerms() {
		return terms;
	}
}
