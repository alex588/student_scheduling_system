package controllers.constructors;

import java.text.ParseException;

import play.mvc.Controller;
import play.mvc.Security;
import play.data.Form;
import play.mvc.*;
import views.forms.RequirementForm;
import views.forms.RequirementEditForm;
import model.*;
import controllers.auth.AdminCheckSecurity;
import controllers.util.Converter;

/**
 * 
 * @author Ihsan Tolga
 * 
 */
@Security.Authenticated(AdminCheckSecurity.class)
public class RequirementsController extends Controller {

	public static Result getRequirements() {
		return ok(views.html.requirementList.render());
	}

	public static Result getAddRequirementPage() {
		return ok(views.html.requirementAdd.render(form(RequirementForm.class)));
	}

	public static Result addRequirement() {
		Form<RequirementForm> form = form(RequirementForm.class)
				.bindFromRequest();
		if (form.hasErrors()) {
			return badRequest(views.html.errorPage.render("Errors in form"));
		} else {
			RequirementForm requirementForm = form.get();
			String abbr = Converter
					.requirementAbbreviationGen(requirementForm.name);
			if (requirementForm.requirementType.equalsIgnoreCase("simple")) {
				SimpleRequirement
						.create(requirementForm.name, abbr, Integer
								.parseInt(requirementForm.fromCourseGroup),
								CourseGroup.getById(Integer
										.parseInt(requirementForm.courseGroup)));

			} else if (requirementForm.requirementType
					.equalsIgnoreCase("combination")) {
				try {
					Converter.convertRequirementCombo(
							requirementForm.comboBool, requirementForm.name,
							abbr, true);
				} catch (ParseException e) {
					return badRequest(views.html.errorPage
							.render("Parse error: " + e.toString()));
				} catch (IllegalArgumentException e) {
					return badRequest(views.html.errorPage
							.render("Parse error: " + e.toString()));
				}
			} else {
				return badRequest(views.html.errorPage
						.render("Wrong radio button selected."));
			}

			return ok(views.html.requirementList.render());
		}
	}

	public static Result deleteRequirement(Integer requirementId) {
		Requirement requirement = Requirement.getById(requirementId);
		if (requirement == null)
			return badRequest(views.html.errorPage
					.render("DB error: requirement with id= " + requirementId
							+ " wasn't found."));
		Requirement.delete(requirement);
		return ok(views.html.requirementList.render());
	}

	public static Result getEditRequirementPage(Integer id) {
		Requirement requirement = Requirement.getById(id);
		if (requirement == null)
			return badRequest(views.html.errorPage
					.render("DB error: requirement with id= " + id
							+ " wasn't found."));
		return ok(views.html.requirementEdit.render(
				form(RequirementEditForm.class), requirement));
	}

	public static Result editRequirement(Integer id) {
		Form<RequirementEditForm> form = form(RequirementEditForm.class)
				.bindFromRequest();
		if (form.hasErrors()) {
			return badRequest(views.html.errorPage.render("Errors in form"));
		} else {
			RequirementEditForm requirementEditForm = form.get();
			if (requirementEditForm.requirementType.equalsIgnoreCase("simple")) {
				SimpleRequirement simpleRequirement = (SimpleRequirement) SimpleRequirement
						.getById(id);
				if (simpleRequirement == null)
					return badRequest(views.html.errorPage
							.render("DB error: requirement with id= " + id
									+ " wasn't found."));
				String abbr;
				if (simpleRequirement.getTitle().equalsIgnoreCase(
						requirementEditForm.name))
					abbr = simpleRequirement.getAbbreviation();
				else
					abbr = Converter
							.cgAbbreviationGen(requirementEditForm.name);

				simpleRequirement.update(requirementEditForm.name, abbr,
						Integer.parseInt(requirementEditForm.fromCourseGroup),
						CourseGroup.getById(Integer
								.parseInt(requirementEditForm.courseGroup)));

			} else if (requirementEditForm.requirementType
					.equalsIgnoreCase("combination")) {
				ComplexRequirement complexRequirement = (ComplexRequirement) ComplexRequirement
						.getById(id);
				if (complexRequirement == null)
					return badRequest(views.html.errorPage
							.render("DB error: requirement with id= " + id
									+ " wasn't found."));
				String abrevTemp = new String(Requirement.getById(id)
						.getAbbreviation());
				try {
					ComplexRequirement updatedReq = Converter
							.convertRequirementCombo(
									requirementEditForm.comboBool,
									requirementEditForm.name, abrevTemp, false);
					complexRequirement.update(updatedReq, true);
				} catch (Exception e) {
					return badRequest(e.toString());
				}
			} else {
				return badRequest("Wrong radio button selected");
			}
			return ok(views.html.requirementList.render());
		}
	}

}
