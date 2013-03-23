package controllers.constructors;

import play.data.Form;
import play.mvc.*;
import views.forms.CourseForm;
import views.forms.CourseEditForm;
import model.*;
import model.Course;
import controllers.util.*;

public class CoursesController extends Controller {

	private static int NUMBER_OF_SEMESTERS = 6;

	public static Result getCourses() {
		return ok(views.html.courseList.render());
	}

	public static Result editCourse(Integer courseId) {
		Form<CourseForm> form = form(CourseForm.class).bindFromRequest();
		if (form.hasErrors()){
			return badRequest();
		}else{
			Course course = Course.getById(courseId);
			CourseForm courseform = form.get();
			course.update(courseform.title, courseform.credits);
		}
		
		return ok(views.html.courseList.render());
	}
	
	public static Result getEditCoursePage(Integer courseId){
		return ok(views.html.courseEdit.render(form(CourseEditForm.class), Course.getById(courseId.intValue()), "", ""));
	}

	public static Result getAddCoursePage() {

		return ok(views.html.courseAdd.render(form(CourseForm.class)));
	}
	
	public static Result deleteCourse(Integer courseId){
		return ok();
	}

	public static Result addCourse() {
		Form<CourseForm> form = form(CourseForm.class).bindFromRequest();
		if (form.hasErrors()) {
			return badRequest();
		} else {
			CourseForm courseForm = form.get();
			Integer course_level_init = courseForm.courseNumber / 100;
			Integer course_level = course_level_init * 100;
			Prefix prefix = Prefix.getById(courseForm.coursePrefix);
			Course c = Course.create(prefix,
					courseForm.courseNumber, courseForm.title,
					courseForm.credits, course_level, null, null);

			// TODO: year is not implemented in view so 2013 is passed for convenience
			Integer year = 2013;
			// FIXME: ask Doug to rename checkboxes such as Fall,Spring,Summer1,... according to strings in DB, so that (Semester.FALL.toString() == str ).
			// Now we have some mismatches like "Summer I" (in DB) and Summer1 (in UI).

			for (int i = 0; i < NUMBER_OF_SEMESTERS; i++) {
				String onCampusStr = ((courseForm.offeredOnCampus != null) && (i < courseForm.offeredOnCampus.size())) ? courseForm.offeredOnCampus.get(i) : null;
				if (onCampusStr == null)
					onCampusStr = "";
				String onLineStr = ((courseForm.offeredOnline != null) &&(i < courseForm.offeredOnline.size())) ? courseForm.offeredOnline.get(i) : null;
				if (onLineStr == null)
					onLineStr = "";

				// if both checkboxes marked
				for (Semester sem : Semester.getAll()){
					if (checkIfBoth(sem, onCampusStr, onLineStr))
						c.setCourseAvailability(sem, year, true, Course.Location.BOTH);
				}
				// if onCampus only
				for (Semester sem : Semester.getAll()){
					if (checkIfOnCampusOnly(sem, onCampusStr, onLineStr))
						c.setCourseAvailability(sem, year, true, Course.Location.ONCAMPUS);
				}
				// if onLine only
				for (Semester sem : Semester.getAll()){
					if (checkIfOnLineOnly(sem, onCampusStr, onLineStr))
						c.setCourseAvailability(sem, year, true, Course.Location.ONLINE);
				}
				// if neither onLine or onCampus then ignore
			}
			return ok(views.html.courseList.render());
		}
	}

	private static boolean checkIfBoth(Semester sem, String onCampusStr,
			String onLineStr) {
		if (onCampusStr.equalsIgnoreCase(sem.toString())
				&& onLineStr.equals(sem.toString()))
			return true;
		else
			return false;
	}
	private static boolean checkIfOnCampusOnly(Semester sem, String onCampusStr,
			String onLineStr) {
		if (onCampusStr.equalsIgnoreCase(sem.toString())
				&& !onLineStr.equals(sem.toString()))
			return true;
		else
			return false;
	}
	private static boolean checkIfOnLineOnly(Semester sem, String onCampusStr,
			String onLineStr) {
		if (!onCampusStr.equalsIgnoreCase(sem.toString())
				&& onLineStr.equals(sem.toString()))
			return true;
		else
			return false;
	}
	
	public static Result getDocumentation(){
		return ok("Soon to be implemented - Static page for both Students and Course Directors");
	}
}
