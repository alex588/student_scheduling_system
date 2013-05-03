package views.forms;

import play.data.validation.Constraints.Required;

/**
 * 
 * @author Doug Kinnes
 * 
 */
public class CourseGroupEditForm {
	@Required
	public String name;
	public String courseNumbers;
	@Required
	public String groupType;
	public String groupComboBool;
}
