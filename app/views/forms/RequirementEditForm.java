package views.forms;

import play.data.validation.Constraints.Required;

/**
 * 
 * @author Doug Kinnes
 * 
 */
public class RequirementEditForm {
	@Required
	public String name;
	@Required
	public String requirementType;
	public String courseGroup;
	public String comboBool;
	public String fromCourseGroup;
}
