package views.forms;

import play.data.validation.Constraints.Required;
import java.util.*;

public class CourseGroupAddCourseForm {
	@Required public String courseAdd;
	public List<String> courses;
}
