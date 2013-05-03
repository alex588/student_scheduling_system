package controllers.auth;

import play.mvc.Result;
import play.mvc.Http.Context;

/**
 * 
 * @author Mihir Daptardar
 * 
 *         This class first make user authentication check and then checks
 *         course director privileges (authorization check).
 * 
 */
public class AdminCheckSecurity extends AuthCheckSecurity {

	@Override
	public String getUsername(Context ctx) {
		String username = ctx.session().get(USERNAME);

		if (isAuthenticated(username, ctx.session().get(AUTH)))
			if (isCourseDirector(username))
				return username;
		return null;
	}

	@Override
	public Result onUnauthorized(Context ctx) {
		String username = ctx.session().get(USERNAME);
		if (!isAuthenticated(username, ctx.session().get(AUTH)))
			return redirect(controllers.auth.routes.LoginController.index());
		else
			return forbidden("You do not have the proper previlages to view this page");
	}

}
