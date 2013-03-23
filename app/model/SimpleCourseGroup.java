package model;

import java.util.ArrayList;
import java.util.List;
import model.entities.ECourse;
import model.entities.ECourseGroup;

public class SimpleCourseGroup extends CourseGroup {
		protected SimpleCourseGroup(){
			super();
		}

		public static SimpleCourseGroup create(String cg_title) {
			SimpleCourseGroup coursegroup = new SimpleCourseGroup();
			ECourseGroup newCourseGroup = new ECourseGroup();
			newCourseGroup.setCg_title(cg_title);
			newCourseGroup.setCg_is_simple(true);
			newCourseGroup.setCg_junction_type(null);
			newCourseGroup.save();
			coursegroup.entity = newCourseGroup;
			return coursegroup;
		}

		
		public void addCourse(Course course) {

			// first check in courseList whether the particular course exists for
			// the coursegroup
			// If not then add...
			List<ECourse> eCourseList = entity.getCourseList();

			if (eCourseList.contains(Course.unwrap(course)))
				return;
			else{
				eCourseList.add(Course.unwrap(course));
				entity.update();
			}
		}

		public void removeCourse(Course course) {
			List<ECourse> eCourseList = entity.getCourseList();

			if (!eCourseList.contains(Course.unwrap(course)))
				return;
			else{
					eCourseList.remove(Course.unwrap(course)); 
					entity.update();
			}

		}

		@Override
		public List<Course> getAllCourses() {
			List<ECourse> list = entity.getCourseList();
			List<Course> courseList = new ArrayList<>(list.size());

			for (ECourse ecourse : list) {
				courseList.add(Course.wrap(ecourse));
			}

			return courseList;
		}

}
