package views.forms;

import play.data.validation.Constraints.Required;
import java.util.*;

public class RequirementForm {
	@Required public String name;
	@Required public String requirementType;
	public String courseGroup;
	public String degreePrereq;
	public String degreeCoreq;
	public String semester;
	public String semesterCourse;
	public String comboBool;
	public Long id;
}
