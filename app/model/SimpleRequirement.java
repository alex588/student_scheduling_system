package model;

import java.util.ArrayList;
import java.util.List;
import model.entities.ERequirement;

/**
 * @author Alexey Tregubov
 * 
 */
public class SimpleRequirement extends Requirement {
	protected SimpleRequirement() {
		super();
	}

	/**
	 * 
	 * @param title
	 * @param abbreviation
	 * @param numberOfCourses
	 *            that student must take from the group
	 * @param group
	 * @return new simple requirement.
	 */
	public static SimpleRequirement create(String title, String abbreviation,
			Integer numberOfCourses, CourseGroup group) {

		SimpleRequirement requirement = new SimpleRequirement();
		ERequirement newRequirement = new ERequirement();
		newRequirement.setIsSimple(true);
		newRequirement.setTitle(title);
		newRequirement.setAbbreviation(abbreviation);
		newRequirement.setNumberOfCourses(numberOfCourses);
		newRequirement.setIsVisible(true);
		newRequirement.setJunction(null);
		newRequirement.setCourseGroup(group.entity);
		newRequirement.save();
		requirement.entity = newRequirement;
		return requirement;
	}

	public void update(String req_title, String req_abbr,
			Integer req_number_of_courses, CourseGroup group) {
		this.entity.setTitle(req_title);
		this.entity.setAbbreviation(req_abbr);
		this.entity.setNumberOfCourses(req_number_of_courses);
		this.entity.setCourseGroup(group.entity);
		this.entity.save();
		return;
	}

	public Integer getNumberOfCourses() {
		return this.entity.getNumberOfCourses();
	}

	public CourseGroup getCourseGroup() {
		return CourseGroupFactory.wrap(this.entity.getCourseGroup());
	}

	@Override
	public List<Course> getCourses() {
		return getCourseGroup().getAllCourses();
	}

	@Override
	public List<Requirement> getAllSubrequirements() {
		List<Requirement> reqs = new ArrayList<Requirement>(1);
		reqs.add(this);
		return reqs;
	}
}
