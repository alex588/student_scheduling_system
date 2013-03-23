package model;

import java.util.ArrayList;
import java.util.List;

import model.entities.ECourseGroup;

public class ComplexCourseGroup extends CourseGroup {
	protected ComplexCourseGroup(){
		super();
	}
	
	public static ComplexCourseGroup create(String cg_title, Junction cg_junction_type) {
		ComplexCourseGroup coursegroup = new ComplexCourseGroup();
		ECourseGroup newCourseGroup = new ECourseGroup();
		newCourseGroup.setCg_title(cg_title);
		newCourseGroup.setCg_is_simple(false);
		newCourseGroup.setCg_junction_type(cg_junction_type.name());
		newCourseGroup.save();
		coursegroup.entity = newCourseGroup;
		
		return coursegroup;
	}
	
	public void addChildNode(CourseGroup courseGroup, Boolean isPositive){
		this.entity.addChild(courseGroup.entity, isPositive);		
	}
	
	@Override
	public List<Course> getAllCourses() {
		//TODO: implement course agrigation from set formula
		List<Course> courseList = new ArrayList<>(0);
		return courseList;
	}	
}
