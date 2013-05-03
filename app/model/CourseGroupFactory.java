package model;

import model.entities.ECourseGroup;

/**
 * @author Alexey Tregubov
 */
/* package */class CourseGroupFactory {
	static CourseGroup wrap(ECourseGroup entity) {
		if (entity == null)
			return null;
		CourseGroup courseGroup = null;
		if (entity.isSimple())
			courseGroup = new SimpleCourseGroup();
		else
			courseGroup = new ComplexCourseGroup();
		courseGroup.entity = entity;
		return courseGroup;
	}
}
