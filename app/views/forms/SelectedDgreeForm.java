package views.forms;

import play.data.validation.Constraints.Required;

public class SelectedDgreeForm {
	@Required
	public Integer degreeId;
	@Required
	public Integer numberOfSemesters;
	@Required
	public Integer firstSemester; // how to calculate number of the semester?									
}
