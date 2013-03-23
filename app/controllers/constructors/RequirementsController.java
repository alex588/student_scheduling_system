package controllers.constructors;

import play.mvc.Controller;
import play.data.Form;
import play.mvc.*;
import play.mvc.Http.Session;

import views.forms.CourseForm;
import views.forms.RequirementForm;
import views.html.*;
import model.*;
import java.util.*;

public class RequirementsController extends Controller {
	
	public static Result getRequirements(){
		
		return ok(views.html.requirementList.render());		
	}
	
	
	public static Result getAddRequirementPage(){
		return ok(views.html.requirementAdd.render(form(RequirementForm.class)));
	}
	
	public static Result addRequirement(){
		Form<RequirementForm> form = form(RequirementForm.class).bindFromRequest();
		if (form.hasErrors()) {
			return badRequest();
		}else{
			RequirementForm reqForm = form.get();
			
			return ok(views.html.requirementList.render());	
		}
	}
	
	public static Result deleteRequirement(Integer requirementId){
		return ok();
	}
	
	public static Result getEditRequirementPage(Integer id){
		return ok();
	}
	
	public static Result editRequirement(Integer id){
		return ok();
	}

}
