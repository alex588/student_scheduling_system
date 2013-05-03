package model;

import java.util.ArrayList;
import java.util.List;
import model.entities.ECourse;
import model.entities.ECourseGroup;

/**
 * @author Alexey Tregubov
 */
public class SimpleCourseGroup extends CourseGroup {
	protected SimpleCourseGroup() {
		super();
	}

	public static SimpleCourseGroup create(String cg_title, String cg_abbr) {
		SimpleCourseGroup coursegroup = new SimpleCourseGroup();
		ECourseGroup newCourseGroup = new ECourseGroup();
		newCourseGroup.setTitle(cg_title);
		newCourseGroup.setAbbreviation(cg_abbr);
		newCourseGroup.setIsSimple(true);
		newCourseGroup.setIsVisible(true);
		newCourseGroup.setJnction(null);
		newCourseGroup.save();
		coursegroup.entity = newCourseGroup;
		return coursegroup;
	}

	public void addCourse(Course course) {
		List<ECourse> eCourseList = entity.getCourseList();
		if (eCourseList.contains(Course.unwrap(course)))
			return;
		else {
			eCourseList.add(Course.unwrap(course));
			entity.setCourseList(eCourseList);
			entity.saveManyToManyAssociations("courseList");
		}
	}

	public void removeCourse(Course course) {
		List<ECourse> eCourseList = entity.getCourseList();
		if (!eCourseList.contains(Course.unwrap(course)))
			return;
		else {
			eCourseList.remove(Course.unwrap(course));
			entity.update();
		}
	}

	@Override
	public List<Course> getAllCourses() {
		List<ECourse> list = entity.getCourseList();
		List<Course> courseList = new ArrayList<Course>(list.size());

		for (ECourse ecourse : list) {
			courseList.add(Course.wrap(ecourse));
		}

		return courseList;
	}

}
