package views.forms;

import play.data.validation.Constraints.Required;
import java.util.*;

/**
 * 
 * @author Doug Kinnes
 * 
 */
public class DegreeProgramForm {
	@Required
	public String name;
	@Required
	public List<String> requirementList;
}
