package controllers.auth;

import play.data.Form;
import play.mvc.*;
import views.forms.LoginForm;
import views.forms.SelectedDegreeForm;

/**
 * 
 * @author Mihir Daptardar
 * 
 */
public class LoginController extends Controller {
	/**
	 * Home page
	 */
	public static Result index() {
		return ok(views.html.index.render(form(LoginForm.class)));
	}

	/**
	 * Login action. Called by form submission.
	 * 
	 * @return
	 */
	public static Result login() {
		Form<LoginForm> form = form(LoginForm.class).bindFromRequest();
		if (form.hasErrors()) {
			return badRequest();
		} else {
			LoginForm data = form.get();
			if ((data.login.equals("admin")) && (data.password.equals("admin"))) {
				session("username", "admin");
				session("auth", Integer.toString("admin".hashCode()));
				return ok(views.html.courseDirectorWelcomePage.render());
			} else if ((data.login.equals("student"))
					&& (data.password.equals("student"))) {
				session("username", "student");
				session("auth", Integer.toString("student".hashCode()));
				return ok(views.html.degreeProgramSelection
						.render(form(SelectedDegreeForm.class)));
			} else
				return redirect(controllers.auth.routes.LoginController.index());
		}
	}

	public static Result logout() {
		session().clear();
		flash("success", "You've been logged out");
		return redirect(controllers.auth.routes.LoginController.index());
	}

	/**
	 * Action for student home page link
	 * 
	 * @return
	 */
	public static Result getStudentHomePage() {
		return ok(views.html.degreeProgramSelection
				.render(form(SelectedDegreeForm.class)));
	}

	/**
	 * Action for course director home page link
	 * 
	 * @return
	 */
	public static Result getAdminHomePage() {
		return ok(views.html.courseDirectorWelcomePage.render());
	}
	
	/**
	 * Action for documentation page link
	 * 
	 * @return
	 */
	public static Result getDocumentationPage() {
		return ok(views.html.userManual.render());
	}
}
