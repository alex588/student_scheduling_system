package views.forms;

import java.util.List;

import play.data.validation.Constraints.Required;

/**
 * 
 * @author Doug Kinnes
 * 
 *         Student side form. This form contains all student desires.
 */
public class StudentRequirementForm {
	public List<Integer> termMin;
	public List<Integer> termMax;
	public List<String> course;
	public List<String> isTaken;
	public List<String> location;
	public List<String> term;
	@Required
	public int numberOfSemesters;
	@Required
	public int enterYear;
	@Required
	public int degreeProgramId;
}
