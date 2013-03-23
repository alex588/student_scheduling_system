package model;

import java.util.List;

import model.entities.ERequirement;

public class SimpleRequirement extends Requirement {
	protected SimpleRequirement(){
		super();
	}
	
	public static SimpleRequirement create(String req_title,
			Integer req_number_of_courses, CourseGroup group) {
		SimpleRequirement requirement = new SimpleRequirement();
		ERequirement newRequirement = new ERequirement();
		newRequirement.setReq_is_simple(true);
		newRequirement.setReq_title(req_title);
		newRequirement.setReq_number_of_courses(req_number_of_courses);
		newRequirement.setReq_is_visible(true);
		newRequirement.setReq_junction_type(null);
		newRequirement.setCourseGroup(group.entity);
		newRequirement.save();
		requirement.entity = newRequirement;
		return requirement;
	}
	

	public void update(String req_title, Integer req_number_of_courses, CourseGroup group) {
		this.entity.setReq_title(req_title);		
		this.entity.setReq_number_of_courses(req_number_of_courses);
		this.entity.setCourseGroup(group.entity);
		this.entity.save();
		return;
	}

	public Integer getNumberOfCourses(){
		return this.entity.getReq_number_of_courses();
	}
	
	public CourseGroup getCourseGroup(){
		return CourseGroupFactory.wrap(this.entity.courseGroup);
	}

	@Override
	public List<Course> getCourses() {
		return getCourseGroup().getAllCourses();
	}
}
