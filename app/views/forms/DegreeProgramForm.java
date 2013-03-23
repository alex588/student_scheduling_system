package views.forms;

import play.data.validation.Constraints.Required;
import java.util.*;

public class DegreeProgramForm {
	@Required public String name;
	@Required public List<String> requirementNames;
	@Required public List<Integer> requirementIds;
	public Long id;
}
