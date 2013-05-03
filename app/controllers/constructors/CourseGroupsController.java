package controllers.constructors;

import java.text.ParseException;
import java.util.List;
import controllers.auth.AdminCheckSecurity;
import controllers.util.Converter;
import model.*;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.forms.CourseGroupEditForm;
import views.forms.CourseGroupForm;

/**
 * 
 * @author Ihsan Tolga
 * 
 */
@Security.Authenticated(AdminCheckSecurity.class)
public class CourseGroupsController extends Controller {

	public static Result getCourseGroups() {
		return ok(views.html.courseGroupList.render());
	}

	public static Result getEditCourseGroupPage(Integer courseGroupId) {
		CourseGroup cg = CourseGroup.getById(courseGroupId);
		if (cg == null)
			return badRequest(views.html.errorPage
					.render("DB error: course group with id= " + courseGroupId
							+ " wasn't found."));
		return ok(views.html.courseGroupEdit.render(
				form(CourseGroupEditForm.class), cg));
	}

	public static Result editCourseGroup(Integer courseGroupId) {
		Form<CourseGroupEditForm> form = form(CourseGroupEditForm.class)
				.bindFromRequest();
		if (form.hasErrors()) {
			return badRequest(views.html.errorPage
					.render("Not all mandatory fields entered."));
		} else {
			CourseGroupEditForm courseGroupEditForm = form.get();
			if (courseGroupEditForm.groupType.equalsIgnoreCase("courseList")) {
				SimpleCourseGroup courseGroup = (SimpleCourseGroup) SimpleCourseGroup
						.getById(courseGroupId);
				if (courseGroup == null)
					return badRequest(views.html.errorPage
							.render("DB error: course group with id= "
									+ courseGroupId + " wasn't found."));
				String abbr;
				if (courseGroup.getTitle().equalsIgnoreCase(
						courseGroupEditForm.name))
					abbr = courseGroup.getAbbreviation();
				else
					abbr = Converter
							.cgAbbreviationGen(courseGroupEditForm.name);

				courseGroup.update(courseGroupEditForm.name, true, null, abbr,
						true);

				List<Course> coursesWithin = courseGroup.getAllCourses();
				for (Course course : coursesWithin) {
					courseGroup.removeCourse(course);
				}

				String courseOfGroup = courseGroupEditForm.courseNumbers
						.toString();
				List<Course> coursesToAdd = Converter
						.parseCourseList(courseOfGroup);
				for (Course course : coursesToAdd) {
					courseGroup.addCourse(course);
				}
			} else if (courseGroupEditForm.groupType
					.equalsIgnoreCase("groupList")) {
				ComplexCourseGroup courseGroup = (ComplexCourseGroup) ComplexCourseGroup
						.getById(courseGroupId);
				String abrevTemp = new String(CourseGroup
						.getById(courseGroupId).getAbbreviation());
				try {
					ComplexCourseGroup updated = Converter.convertGroupCombo(
							courseGroupEditForm.groupComboBool,
							courseGroupEditForm.name, abrevTemp, false);
					courseGroup.update(updated, true);
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
		}
		return ok(views.html.courseGroupList.render());
	}

	public static Result getAddCourseGroupPage() {
		return ok(views.html.courseGroupAdd.render(form(CourseGroupForm.class)));
	}

	public static Result deleteCourseGroup(Integer courseGroupId) {

		CourseGroup courseGroup = CourseGroup.getById(courseGroupId);
		if (courseGroup == null)
			return badRequest(views.html.errorPage
					.render("DB error: course group with id= " + courseGroupId
							+ " wasn't found."));
		CourseGroup.delete(courseGroup);
		return ok(views.html.courseGroupList.render());
	}

	public static Result addCourseGroup() {
		Form<CourseGroupForm> form = form(CourseGroupForm.class)
				.bindFromRequest();
		if (form.hasErrors()) {
			return badRequest(views.html.errorPage
					.render("Not all mandatory fields entered."));
		} else {
			CourseGroupForm courseGroupForm = form.get();
			String abbr = Converter.cgAbbreviationGen(courseGroupForm.name);
			if (courseGroupForm.groupType.equalsIgnoreCase("courseList")) {
				SimpleCourseGroup courseGroup = SimpleCourseGroup.create(
						courseGroupForm.name, abbr);

				String courseOfGroup = courseGroupForm.courseNumbers.toString();
				try {
					List<Course> coursesToAdd = Converter
							.parseCourseList(courseOfGroup);

					for (Course course : coursesToAdd) {
						courseGroup.addCourse(course);
					}
				} catch (IllegalArgumentException e) {
					return badRequest(views.html.errorPage
							.render("Parse error: " + e.toString()));
				}
			} else if (courseGroupForm.groupType.equalsIgnoreCase("groupList")) {
				try {
					Converter.convertGroupCombo(courseGroupForm.groupComboBool,
							courseGroupForm.name, abbr, true);
				} catch (ParseException e) {
					return badRequest(views.html.errorPage
							.render("Parse error: " + e.toString()));
				} catch (IllegalArgumentException e) {
					return badRequest(views.html.errorPage
							.render("Parse error: " + e.toString()));
				}
			} else {
				return badRequest(views.html.errorPage
						.render("Wrong radio button selection"));
			}
			return ok(views.html.courseGroupList.render());
		}
	}
}
