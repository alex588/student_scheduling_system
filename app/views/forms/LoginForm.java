package views.forms;

import play.data.validation.Constraints.Required;

/**
 * 
 * @author Doug Kinnes
 * 
 */
public class LoginForm {
	@Required
	public String login;
	@Required
	public String password;
}
