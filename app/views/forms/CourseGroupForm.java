package views.forms;

import play.data.validation.Constraints.Required;
import java.util.*;

public class CourseGroupForm {
	@Required public String name;
	@Required public String description;
	public String courseNumbers;
	@Required public String groupType;
	public String groupComboBool;
	public Long id;
}
