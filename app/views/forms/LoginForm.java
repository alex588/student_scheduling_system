package views.forms;

import play.data.validation.Constraints.Required;

public class LoginForm {
	@Required public String login;
	@Required public String password;
}
