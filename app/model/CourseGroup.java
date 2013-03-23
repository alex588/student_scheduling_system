package model;

import java.util.ArrayList;

import java.util.List;
import model.entities.ECourseGroup;

import com.avaje.ebean.Ebean;

public abstract class CourseGroup {
	protected ECourseGroup entity;

	protected CourseGroup() {
		super();
	}

	public static List<CourseGroup> getAll() {
		List<ECourseGroup> list = Ebean.find(ECourseGroup.class).findList();
		List<CourseGroup> courseGroupList = new ArrayList<>(list.size());

		for (ECourseGroup ecoursegroup : list) {
			CourseGroup coursegroup = CourseGroupFactory.wrap(ecoursegroup);
			courseGroupList.add(coursegroup);
		}
		return courseGroupList;

	}

	public void update(String cg_title, Boolean cg_is_simple,
			String cg_junction_type) {

		this.entity.setCg_title(cg_title);
		this.entity.setCg_junction_type(cg_junction_type);
		this.entity.setCg_is_simple(cg_is_simple);
		this.entity.update();
	}

	static public void delete(CourseGroup courseGroup) {
		courseGroup.entity.delete();
	}

	public boolean isSimple(){
		return entity.getCg_is_simple();
	}
	
	public Integer getId() {
		return entity.getCg_id();
	}

	public String getTitle() {
		return entity.getCg_title();
	}

	public abstract List<Course> getAllCourses();	

}