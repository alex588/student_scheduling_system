package controllers.constructors;

import java.util.ArrayList;
import java.util.List;

import model.*;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.forms.CourseGroupAddCourseForm;
import views.forms.CourseGroupForm;

public class CourseGroupsController extends Controller {

	public static Result getCourseGroups(){		
		return ok(views.html.courseGroupList.render());
	}
	
	public static Result getEditCourseGroupPage(Integer couseGroupId){
		
		return ok();
	}

	public static Result editCourseGroup(Integer courseGroupId){
		
		return ok();
	}
	
	public static Result getAddCourseGroupPage(){
		
		return ok(views.html.courseGroupAdd.render(form(CourseGroupForm.class)));
	}
	
	public static Result deleteCourseGroup(Integer courseGroupId){
		return ok();
	}
	
	public static Result addCourseGroup(){
		Form<CourseGroupForm> form = form(CourseGroupForm.class).bindFromRequest();
		if (form.hasErrors()) {
			return badRequest();
		}else{
			CourseGroupForm courseGroupForm = form.get();
			
			return ok(views.html.courseGroupList.render());
		}
	}

	//This generates fields that are sent back to the CourseGroupsAdd page so that the user can keep entering in classes	
	/*public static Result generateForm(){
		Form<CourseGroupAddCourseForm> form = form(CourseGroupAddCourseForm.class).bindFromRequest();
		if (form.hasErrors()) {
			return badRequest();
		}else{
			//Create variables to hold html data for storing courses added to the course group
			CourseGroupAddCourseForm data = form.get();
			String courseNumberHiddenFields = "";
			String courseNumberTextBoxes = "";
			//Check for bad data
			if(data.courses != null){
				//For each course in the posted form data, create a hidden field containing the course number so that when they add another course,
				//the courses already added stay alive instead of being wiped each post.
				//Also create a text box with the course number that will be part of the final course group creation form.
				for(int i = 0; i < data.courses.size(); i++){
					courseNumberHiddenFields = courseNumberHiddenFields + "<input type=\"hidden\" name=\"courses[" + Integer.toString(i) + "]\" value=\"" + data.courses.get(i) + "\"/>";
					courseNumberTextBoxes = courseNumberTextBoxes + "<input type=\"text\" name=\"courseNumbers[" + Integer.toString(i) + "]\" value=\"" + data.courses.get(i) + "\"/>";
				}
				courseNumberHiddenFields = courseNumberHiddenFields + "<input type=\"hidden\" name=\"courses[" + data.courses.size() + "]\" value=\"" + data.courseAdd + "\"/>";
				courseNumberTextBoxes = courseNumberTextBoxes + "<input type=\"text\" name=\"courseNumbers[" + data.courses.size() + "]\" value=\"" + data.courseAdd + "\"/>";				
				
			}//For the first course added
			else{
				courseNumberHiddenFields = courseNumberHiddenFields + "<input type=\"hidden\" name=\"courses[0]\" value=\"" + data.courseAdd + "\"/>";
				courseNumberTextBoxes = courseNumberTextBoxes + "<input type=\"text\" name=\"courseNumbers[0]\" value=\"" + data.courseAdd + "\"/>";
			}
			
			return ok(views.html.courseGroupAdd.render(form(CourseGroupForm.class)));
		}
	}*/
}
