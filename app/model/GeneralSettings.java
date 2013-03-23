package model;

import java.util.List;

public class GeneralSettings {
	private Integer maxNumberOfCoursesPerSemester;
	private Integer minNumberOfCoursesPerSemester;
	private Integer maxNumberOfCreditsPerSemester;
	private Integer minNumberOfCreditsPerSemester;
	private List<Term> terms;

	protected GeneralSettings(Integer maxNumberOfCoursesPerSemester,
			Integer minNumberOfCoursesPerSemester,
			Integer maxNumberOfCreditsPerSemester,
			Integer minNumberOfCreditsPerSemester, List<Term> terms) {
		super();
		this.maxNumberOfCoursesPerSemester = maxNumberOfCoursesPerSemester;
		this.minNumberOfCoursesPerSemester = minNumberOfCoursesPerSemester;
		this.maxNumberOfCreditsPerSemester = maxNumberOfCreditsPerSemester;
		this.minNumberOfCreditsPerSemester = minNumberOfCreditsPerSemester;
		this.terms = terms;
	}

	public static GeneralSettings create(Integer maxNumberOfCoursesPerSemester,
			Integer minNumberOfCoursesPerSemester,
			Integer maxNumberOfCreditsPerSemester,
			Integer minNumberOfCreditsPerSemester, List<Term> terms) {
		return new GeneralSettings(maxNumberOfCoursesPerSemester,
				minNumberOfCoursesPerSemester, maxNumberOfCreditsPerSemester,
				minNumberOfCreditsPerSemester, terms);
	}

	public Integer getMaxNumberOfCoursesPerSemester() {
		return maxNumberOfCoursesPerSemester;
	}

	public Integer getMinNumberOfCoursesPerSemester() {
		return minNumberOfCoursesPerSemester;
	}

	public Integer getMaxNumberOfCreditsPerSemester() {
		return maxNumberOfCreditsPerSemester;
	}

	public Integer getMinNumberOfCreditsPerSemester() {
		return minNumberOfCreditsPerSemester;
	}

	public List<Term> getTerms() {
		return terms;
	}
}
