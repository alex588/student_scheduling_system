package views.forms;

import play.data.validation.Constraints.Required;

/**
 * 
 * @author Doug Kinnes
 * 
 */
public class RequirementForm {
	@Required
	public String name;
	@Required
	public String requirementType;
	public String fromCourseGroup;
	public String courseGroup;
	public String degreePrereq;
	public String degreeCoreq;
	public String semester;
	public String semesterCourse;
	public String comboBool;
}
