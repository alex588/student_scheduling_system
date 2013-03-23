package model;

import model.entities.ECourseGroup;

class CourseGroupFactory {
	static CourseGroup wrap(ECourseGroup entity) {
		CourseGroup courseGroup = null;
		if (entity.getCg_is_simple())
			courseGroup = new SimpleCourseGroup();
		else
			courseGroup = new ComplexCourseGroup();
		courseGroup.entity = entity;		
		return courseGroup;
	}
}
