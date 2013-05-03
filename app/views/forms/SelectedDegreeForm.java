package views.forms;

import play.data.validation.Constraints.Required;

/**
 * 
 * @author Doug Kinnes
 * 
 *         Student side form.
 */
public class SelectedDegreeForm {
	@Required
	public Integer degreeId;
	@Required
	public Integer numberOfSemesters;
	@Required
	public Integer enterYear;
}
