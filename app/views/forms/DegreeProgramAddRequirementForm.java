package views.forms;

import play.data.validation.Constraints.Required;
import java.util.*;

public class DegreeProgramAddRequirementForm {
	@Required public Integer requirementIdAdd;
	public List<String> requirementNames;
	public List<Integer> requirementIds;
}
