package controllers.constructors;

import java.util.ArrayList;
import java.util.List;
import controllers.auth.AuthCheckSecurity;
import controllers.util.Converter;
import model.*;
import play.data.Form;
import play.mvc.*;
import views.forms.DegreeProgramForm;
import views.forms.DegreeProgramEditForm;
import views.forms.StudentRequirementForm;
import views.forms.SelectedDegreeForm;

/**
 * 
 * @author Ihsan Tolga
 * 
 */
@Security.Authenticated(AuthCheckSecurity.class)
public class DegreeProgramsController extends Controller {

	public static Result editDegreeProgram(Integer degreeId) {
		Form<DegreeProgramForm> form = form(DegreeProgramForm.class)
				.bindFromRequest();
		if (form.hasErrors()) {
			return badRequest(views.html.errorPage
					.render("Not all mandatory fields entered."));
		} else {
			DegreeProgramForm degreeProgramForm = form.get();
			DegreeProgram dp = DegreeProgram.getById(degreeId);
			if (dp == null)
				return badRequest(views.html.errorPage
						.render("DB error: degree program with id= " + degreeId
								+ " wasn't found."));
			String name = degreeProgramForm.name;

			dp.removeAllRequirements();

			List<String> strReqList = degreeProgramForm.requirementList;
			List<Requirement> reqList = new ArrayList<>();
			for (String eachReq : strReqList) {
				if (eachReq != null) {
					Requirement req = Requirement.getById(Integer
							.parseInt(eachReq));
					reqList.add(req);
				}
			}
			dp.update(name, reqList);
			return ok(views.html.degreeProgramList.render());
		}
	}

	public static Result setDegreeProgram() {
		// TODO: store degree program selections in session
		Form<SelectedDegreeForm> form = form(SelectedDegreeForm.class)
				.bindFromRequest();
		if (form.hasErrors()) {
			return badRequest();
		} else {
			SelectedDegreeForm degreeProgramForm = form.get();
			List<Term> listOfTerms = Converter.TermGenerator(
					degreeProgramForm.enterYear,
					degreeProgramForm.numberOfSemesters);

			DegreeProgram deg = DegreeProgram
					.getById(degreeProgramForm.degreeId);
			return ok(views.html.studentRequirementSelection.render(
					form(StudentRequirementForm.class), deg,
					degreeProgramForm.enterYear,
					degreeProgramForm.numberOfSemesters, listOfTerms));
		}
	}

	public static Result deleteDegreeProgram(Integer degreeId) {
		DegreeProgram dp = DegreeProgram.getById(degreeId);
		if (dp == null)
			return badRequest(views.html.errorPage
					.render("DB error: degree program with id= " + degreeId
							+ " wasn't found."));
		DegreeProgram.delete(degreeId);
		return ok(views.html.degreeProgramList.render());

	}

	public static Result getAddDegreeProgramPage() {
		return ok(views.html.degreeProgramAdd
				.render(form(DegreeProgramForm.class)));
	}

	public static Result getEditDegreeProgramPage(Integer degreeId) {
		DegreeProgram dp = DegreeProgram.getById(degreeId);
		if (dp == null)
			return badRequest(views.html.errorPage
					.render("DB error: degree program with id= " + degreeId
							+ " wasn't found."));
		return ok(views.html.degreeProgramEdit.render(
				form(DegreeProgramEditForm.class), dp));
	}

	public static Result addDegreeProgram() {
		Form<DegreeProgramForm> form = form(DegreeProgramForm.class)
				.bindFromRequest();
		if (form.hasErrors()) {
			return badRequest(views.html.errorPage
					.render("Not all mandatory fields entered."));
		} else {
			DegreeProgramForm degreeProgramForm = form.get();
			String name = degreeProgramForm.name;
			List<String> strReqList = degreeProgramForm.requirementList;
			List<Requirement> reqList = new ArrayList<>();
			for (String eachReq : strReqList) {
				if (eachReq != null) {
					Requirement req = Requirement.getById(Integer
							.parseInt(eachReq));
					reqList.add(req);
				}
			}
			DegreeProgram.create(name, reqList);
			return ok(views.html.degreeProgramList.render());
		}
	}

	public static Result getDegreePrograms() {
		return ok(views.html.degreeProgramList.render());
	}

}
