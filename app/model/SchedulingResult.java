package model;

/**
 * @author Alexey Tregubov
 */
public class SchedulingResult {
	private String message;
	private StudyPlan studyPlan;
	private Boolean isValidPlan;

	/**
	 * 
	 * @param resultStatusMessage
	 * @param studyPlan
	 * @param isValidPlan
	 *            true if study plan was constructed successfully. Otherwise it
	 *            is false.
	 */
	private SchedulingResult(String resultStatusMessage, StudyPlan studyPlan,
			Boolean isValidPlan) {
		super();
		this.message = resultStatusMessage;
		this.studyPlan = studyPlan;
		this.isValidPlan = isValidPlan;
	}

	public static SchedulingResult create(String message, StudyPlan studyPlan,
			Boolean isValidPlan) {
		return new SchedulingResult(message, studyPlan, isValidPlan);
	}

	public String getMessage() {
		return message;
	}

	public StudyPlan getStudyPlan() {
		return studyPlan;
	}

	public Boolean getIsValidPlan() {
		return isValidPlan;
	}

}
