package controllers.constructors;

import java.util.List;
import javax.persistence.PersistenceException;
import play.data.Form;
import play.mvc.*;
import views.forms.CourseForm;
import views.forms.CourseEditForm;
import model.*;
import controllers.auth.AdminCheckSecurity;
import controllers.util.*;

/**
 * 
 * @author Ihsan Tolga
 * 
 */
@Security.Authenticated(AdminCheckSecurity.class)
public class CoursesController extends Controller {

	public static Result getCourses() {
		return ok(views.html.courseList.render());
	}

	public static Result editCourse(Integer courseId) {
		Form<CourseEditForm> form = form(CourseEditForm.class)
				.bindFromRequest();
		if (form.hasErrors())
			return badRequest(views.html.errorPage.render("Not all mandatory fields entered."));
		else {
			try {
				CourseEditForm courseForm = form.get();
				Course course = Course.getById(courseId);
				if (course == null){
					return badRequest(views.html.errorPage
							.render("DB error: course with id=" + courseId
									+ " wasn't found."));
				}
				course.update(courseForm.title, courseForm.credits);
				cleanAvailability(course);
				setAvailabilityAndRequisites(courseForm.offeredOnCampus,
						courseForm.offeredOnline, courseForm.offeredBoth,
						courseForm.prereqEquation, courseForm.coreqEquation,
						course);
			} catch (PersistenceException e) {
				return badRequest(views.html.errorPage
						.render("DB error: course with id=" + courseId
								+ " cannot be updated."));
			} catch (Exception e) {
				return badRequest(views.html.errorPage.render(e.toString()));
			}
		}
		return ok(views.html.courseList.render());
	}

	public static Result getEditCoursePage(Integer courseId) {
		Course course = Course.getById(courseId);
		if (course == null){
			return badRequest(views.html.errorPage
					.render("DB error: course with id=" + courseId
							+ " wasn't found."));
		}
		return ok(views.html.courseEdit.render(form(CourseEditForm.class),
				course));
	}

	public static Result getAddCoursePage() {
		return ok(views.html.courseAdd.render(form(CourseForm.class)));
	}

	public static Result deleteCourse(Integer courseId) {
		try {
			Course.delete(courseId);
		} catch (PersistenceException e) {
			return badRequest(views.html.errorPage
					.render("DB error: course with Id=" + courseId
							+ " was not found."));
		}
		return ok(views.html.courseList.render());
	}

	public static Result addCourse() {
		Form<CourseForm> form = form(CourseForm.class).bindFromRequest();
		if (form.hasErrors()) {
			return badRequest(views.html.errorPage.render("Not all mandatory fields entered."));
		} else {
			CourseForm courseForm = form.get();
			Integer course_level_init = courseForm.courseNumber / 100;
			Integer course_level = course_level_init * 100;
			Prefix prefix = Prefix.getById(courseForm.coursePrefix);
			Course c = Course.create(prefix, courseForm.courseNumber,
					courseForm.title, courseForm.credits, course_level, null,
					null);
			try {
				setAvailabilityAndRequisites(courseForm.offeredOnCampus,
						courseForm.offeredOnline, courseForm.offeredBoth,
						courseForm.prereqEquation, courseForm.coreqEquation, c);
			} catch (Exception e) {
				return badRequest(views.html.errorPage.render(e.toString()));
			}
			return ok(views.html.courseList.render());
		}
	}

	private static void cleanAvailability(Course c) {
		// year is not implemented in UI
		Integer year = null;
		for (Semester sem : Semester.getAll()) {
			c.setCourseAvailability(sem, year, false, Course.Location.BOTH);
		}
	}

	private static void setAvailabilityAndRequisites(
			List<String> offeredOnCampus, List<String> offeredOnline,
			List<String> offeredBoth, String prereqEquation,
			String coreqEquation, Course c) throws Exception {
		// year is not implemented in UI
		Integer year = null;

		Integer semNumber = Semester.getNumberOfSemesters();
		for (int i = 0; i < semNumber; i++) {
			String onCampusStr = ((offeredOnCampus != null) && (i < offeredOnCampus
					.size())) ? offeredOnCampus.get(i) : null;
			if (onCampusStr == null)
				onCampusStr = "";
			String onLineStr = ((offeredOnline != null) && (i < offeredOnline
					.size())) ? offeredOnline.get(i) : null;
			if (onLineStr == null)
				onLineStr = "";

			// if both checkboxes marked
			for (Semester sem : Semester.getAll()) {
				if (checkIfBoth(sem, onCampusStr, onLineStr))
					c.setCourseAvailability(sem, year, true,
							Course.Location.BOTH);
			}
			// if onCampus only
			for (Semester sem : Semester.getAll()) {
				if (checkIfOnCampusOnly(sem, onCampusStr, onLineStr))
					c.setCourseAvailability(sem, year, true,
							Course.Location.ONCAMPUS);
			}
			// if onLine only
			for (Semester sem : Semester.getAll()) {
				if (checkIfOnLineOnly(sem, onCampusStr, onLineStr))
					c.setCourseAvailability(sem, year, true,
							Course.Location.ONLINE);
			}
			// if neither onLine or onCampus then ignore
		}

		// converting from string to requisite object
		try {
			PreRequisite existingPrereq = c.getPreRequisite();
			String prereqTemp = prereqEquation;
			if (existingPrereq != null) {
				if (prereqTemp == null || prereqTemp.equals(""))
					c.setPrereq(null);
				else
					c.setPrereq((PreRequisite) Converter.convertPreCorReq(
							prereqTemp, true));
			} else {
				if (prereqTemp != null && !prereqTemp.equals(""))
					c.setPrereq((PreRequisite) Converter.convertPreCorReq(
							prereqTemp, true));
			}

			CoRequisite existingCoreq = c.getCoRequisite();
			String coreqTemp = coreqEquation;
			if (existingCoreq != null) {
				if (coreqTemp == null || coreqTemp.equals(""))
					c.setCoreq(null);
				else
					c.setCoreq((CoRequisite) Converter.convertPreCorReq(
							coreqTemp, false));
			} else {
				if (coreqTemp != null && !coreqTemp.equals(""))
					c.setCoreq((CoRequisite) Converter.convertPreCorReq(
							coreqTemp, false));
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
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

	private static boolean checkIfOnCampusOnly(Semester sem,
			String onCampusStr, String onLineStr) {
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

	public static Result getDocumentation() {
		return ok("Soon to be implemented - Static page for both Students and Course Directors");
	}
}
