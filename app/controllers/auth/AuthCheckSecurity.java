package controllers.auth;

import play.mvc.Result;
import play.mvc.Security;
import play.mvc.Http.Context;

/**
 * 
 * @author Mihir Daptardar
 * 
 */
public class AuthCheckSecurity extends Security.Authenticator {
	private static final String STUDENT = "student";
	private static final String COURSE_DIRECTOR = "admin";
	protected static final String USERNAME = "username";
	protected static final String AUTH = "auth";

	@Override
	public String getUsername(Context ctx) {
		String username = ctx.session().get(USERNAME);
		if (isAuthenticated(username, ctx.session().get(AUTH))) {
			return username;
		}
		return null;
	}

	@Override
	public Result onUnauthorized(Context ctx) {
		return redirect(controllers.auth.routes.LoginController.index());
	}

	protected Boolean isAuthenticated(String username, String auth) {
		if (username != null && auth != null) {
			// TODO: go to DB and check if username and password hash is present
			int passHash = Integer.parseInt(auth);
			if (username.equals(STUDENT) && (passHash == STUDENT.hashCode()))
				return true;
			if (username.equals(COURSE_DIRECTOR)
					&& (passHash == COURSE_DIRECTOR.hashCode()))
				return true;
		}
		return false;
	}

	protected Boolean isCourseDirector(String username) {
		if (username == null)
			throw new IllegalStateException();
		else {
			// TODO: once DB is implemented, role has to be retrieved from DB
			if (username.equals(COURSE_DIRECTOR))
				return true;
		}
		return false;
	}
}
