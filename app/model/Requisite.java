package model;

import java.util.ArrayList;
import java.util.List;
import model.entities.EPrereqCoreqFormula;

/**
 * @author Mihir Daptardar
 */
public abstract class Requisite {
	protected EPrereqCoreqFormula entity;

	protected Requisite() {
		super();
	}

	protected Requisite(Requisite parent, Junction junction,
			Boolean pcf_is_course, Course course, Boolean isPositive) {
		this.entity = new EPrereqCoreqFormula();

		this.entity.setParent(parent != null ? parent.entity : null);
		this.entity.setJunction(junction != null ? junction.name()
				: null);
		this.entity.setIsCourse(pcf_is_course);
		this.entity.setCourse(Course.unwrap(course));
		this.entity.setIsPositive(isPositive);
		this.entity.save();
		if (parent != null)
			parent.entity.refresh();
	}

	public Junction getJunction() {
		return Junction.valueOf(entity.getJunction());
	}

	public Boolean getIsPositive() {
		if (entity.isPositive())
			return true;
		else
			return false;
	}

	/**
	 * This method is used in getChildren() method. it allows us to restore
	 * correct type of the children.
	 */
	protected abstract Requisite innerWrap(EPrereqCoreqFormula entity);

	public List<Requisite> getChildren() {
		entity.refresh();
		List<Requisite> children = new ArrayList<>(entity.getChildren().size());
		for (EPrereqCoreqFormula child : entity.getChildren())
			children.add(innerWrap(child));
		return children;
	}

	public Boolean getIsLeaf() {
		if (entity.getCourse() == null)
			return false;
		else
			return true;
	}

	public Integer getId() {
		return entity.getId();
	}

	/**
	 * 
	 * @return the associated course if this node is a leaf, otherwise returns
	 *         null;
	 */
	public Course getCourse() {
		return (entity.getCourse() != null) ? Course.wrap(entity.getCourse())
				: null;
	}

	/**
	 * 
	 * @return list of all courses in the formula
	 */
	public List<Course> getAllCourses() {
		List<Course> result = new ArrayList<Course>();
		if (this.getIsLeaf()) {
			result.add(getCourse());
		} else {
			for (Requisite req : getChildren()) {
				result.addAll(req.getAllCourses());
			}
		}
		return result;
	}

	/**
	 * This method does the same as getAllCourses(), but also retrieves courses
	 * involved in requisites of classes in the formula.
	 * 
	 * @return
	 */
	public List<Course> getAllCoursesIncludingSubReqs() {
		List<Course> result = new ArrayList<Course>();
		if (this.getIsLeaf()) {
			result.add(getCourse());
			if (this.getCourse().getPreRequisite() != null)
				result.addAll(this.getCourse().getPreRequisite()
						.getAllCoursesIncludingSubReqs());
			if (this.getCourse().getCoRequisite() != null)
				result.addAll(this.getCourse().getCoRequisite()
						.getAllCoursesIncludingSubReqs());
		} else {
			for (Requisite req : getChildren()) {
				result.addAll(req.getAllCoursesIncludingSubReqs());
			}
		}
		return result;
	}
}
