package views.forms;

import play.data.validation.Constraints.Required;
import java.util.*;

/**
 * 
 * @author Doug Kinnes
 * 
 */
public class CourseForm {
	@Required
	public Integer coursePrefix;
	@Required
	public Integer courseNumber;
	@Required
	public String title;
	public String prereqEquation;
	public String coreqEquation;
	@Required
	public Integer credits;
	public List<String> offeredOnCampus;
	public List<String> offeredOnline;
	public List<String> offeredBoth;
}
