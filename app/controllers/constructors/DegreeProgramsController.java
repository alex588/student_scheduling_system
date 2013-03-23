package controllers.constructors;

import java.util.ArrayList;
import java.util.List;

import model.*;
import play.*;
import play.data.Form;
import play.mvc.*;
import views.forms.CourseGroupAddCourseForm;
import views.forms.CourseGroupForm;
import views.forms.DegreeProgramForm;
import views.forms.DegreeProgramAddRequirementForm;
import views.forms.SelectedDegreeForm;

import views.html.*;

public class DegreeProgramsController extends Controller {
	public static Result getDegreeProgramsListForStudent() {
		// TODO: This a getDegreeList() action stub.
		return ok("This a getDegreeList() action stub.");
	}
	
	public static Result getAllDegreePrograms() {
		
		return ok("ok");
	}
	
	public static Result editDegreeProgram(Integer degreeId){
		return ok();
	}
	
	public static Result setDegreeProgram(){
		//TODO: store degree program selections in session
		Form<SelectedDegreeForm> form = form(SelectedDegreeForm.class).bindFromRequest(); 
		return ok("Degree Selected");
	}
	
	public static Result deleteDegreeProgram(Integer degreeId){
		return ok();
	}
	
	public static Result getAddDegreeProgramPage(){
		return ok(views.html.degreeProgramAdd.render(form(DegreeProgramForm.class), "", ""));
	}
	
	public static Result getEditDegreeProgramPage(Integer degreeId){
		return ok();
	}
	
	public static Result addDegreeProgram(){
		Form<DegreeProgramForm> form = form(DegreeProgramForm.class).bindFromRequest();
		if (form.hasErrors()) {
			return badRequest();
		}else{
			DegreeProgramForm degreeProgramForm = form.get();
			
			return ok(views.html.degreeProgramList.render());
		}
	}
	
	public static Result getDegreePrograms(){
		
		return ok(views.html.degreeProgramList.render());
	}
	
	public static Result generateForm(){
		
		Form<DegreeProgramAddRequirementForm> form = form(DegreeProgramAddRequirementForm.class).bindFromRequest();
		if (form.hasErrors()) {
			return badRequest();
		}else{
			DegreeProgramAddRequirementForm data = form.get();
			String degreeProgramHiddenFields = "";
			String degreeProgramTextBoxes = "";
			String newRequirementName = "awesome";//Lookup requirement name by data.requirementIdAdd.
			if(data.requirementNames != null){
				for(int i = 0; i < data.requirementNames.size(); i++){
					degreeProgramHiddenFields = degreeProgramHiddenFields + "<input type=\"hidden\" name=\"requirementNames[" + Integer.toString(i) + "]\" value=\"" + data.requirementNames.get(i) + "\"/>"
																			+ "<input type=\"hidden\" name=\"requirementIds[" + Integer.toString(i) + "]\" value=\"" + data.requirementIds.get(i) + "\"/>";
					degreeProgramTextBoxes = degreeProgramTextBoxes + "<input type=\"text\" name=\"requirementNames[" + Integer.toString(i) + "]\" value=\"" + data.requirementNames.get(i) + "\"/>"
																	  + "<input type=\"hidden\" name=\"requirementIds[" + Integer.toString(i) + "]\" value=\"" + data.requirementIds.get(i) + "\"/><br>";
				}
				degreeProgramHiddenFields = degreeProgramHiddenFields + "<input type=\"hidden\" name=\"requirementNames[" + data.requirementNames.size() + "]\" value=\"" + newRequirementName + "\"/>"
																		+ "<input type=\"hidden\" name=\"requirementIds[" + data.requirementNames.size() + "]\" value=\"" + data.requirementIdAdd + "\"/>";
				degreeProgramTextBoxes = degreeProgramTextBoxes + "<input type=\"text\" name=\"requirementNames[" + data.requirementNames.size() + "]\" value=\"" + newRequirementName + "\"/>"				
																  + "<input type=\"hidden\" name=\"requirementIds[" + data.requirementNames.size() + "]\" value=\"" + data.requirementIdAdd + "\"/><br>";
			}
			else{
				degreeProgramHiddenFields = degreeProgramHiddenFields + "<input type=\"hidden\" name=\"requirementNames[0]\" value=\"" + newRequirementName + "\"/>"
																		+ "<input type=\"hidden\" name=\"requirementIds[0]\" value=\"" + data.requirementIdAdd + "\"/>";
				degreeProgramTextBoxes = degreeProgramTextBoxes + "<input type=\"text\" name=\"requirementNames[0]\" value=\"" + newRequirementName + "\"/>"				
																  + "<input type=\"hidden\" name=\"requirementIds[0]\" value=\"" + data.requirementIdAdd + "\"/><br>";
			}
			
			return ok(views.html.degreeProgramAdd.render(form(DegreeProgramForm.class), degreeProgramHiddenFields, degreeProgramTextBoxes));
		}
	}

	public static Result getDegreeRequirementsForStudent() {
		// TODO: This a getDegreeRequirements() action stub.
		return ok("This a getDegreeRequirements() action stub.DegreeId = ");
	}
}
