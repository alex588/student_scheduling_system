package controllers.auth;

import play.data.Form;
import play.mvc.*;
import play.mvc.Http.Session;

import views.forms.LoginForm;
import views.forms.SelectedDegreeForm;
import views.html.*;

public class LoginController extends Controller {
	/**
	 * Home page
	 */
	public static Result index() {
		return ok(views.html.index.render(form(LoginForm.class)));
	}

	public static Result login() {
		Form<LoginForm> form = form(LoginForm.class).bindFromRequest();
		if (form.hasErrors()) {
			return badRequest();
		} else {
			LoginForm data = form.get();
			if ( (data.login.equals("admin")) && (data.password.equals("admin")))
			{
				session("username", "admin");
				session("auth", "admin");
				return ok(views.html.courseDirectorWelcomePage.render());
			}
			else if( (data.login.equals("student")) && (data.password.equals("student")))
			{
				session("username", "student");
				session("auth", "student");
				return ok(views.html.degreeProgramSelection.render(form(SelectedDegreeForm.class)));
			}
			else
			{
				return ok("login action: login = " + data.login + ", password = "
					+ data.password);
			}
		}
	}

	public static Result logout() {
		// TODO: This a logout action stub.
		session().clear();
		return ok("log out action.");
	}
}
