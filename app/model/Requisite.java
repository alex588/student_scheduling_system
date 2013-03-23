package model;

import model.entities.EPrereqCoreqFormula;

import com.avaje.ebean.Ebean;

public abstract class Requisite {
	protected EPrereqCoreqFormula entity;

	protected Requisite(){
		super();
	}

	protected Requisite(Requisite parent, Junction junction,
			Boolean pcf_is_course, Course course,
			Boolean pcf_is_positive) {
		this.entity = new EPrereqCoreqFormula();
	
		this.entity.setParent(parent != null ? parent.entity : null);
		this.entity.setPcf_junction_type(junction != null ? junction.name() : null );
		this.entity.setPcf_is_course(pcf_is_course);
		this.entity.setCourse(Course.unwrap(course));
		this.entity.setPcf_is_positive(pcf_is_positive);

		Ebean.save(this.entity);
	}

	public Junction getJunction() {
		return Junction.valueOf(entity.getPcf_junction_type());
	}

	public Boolean getIsPositive() {
		if (entity.pcf_is_positive)
			return true;
		else
			return false;
	}

	public Boolean getIsLeaf() {
		if (entity.getCourse().getCourse_id().equals(null))
			return false;
		else
			return true;
	}
	
	public Integer getId(){
		return entity.getPcf_id();
	}
	
	/**
	 * 
	 * @return the associated course if this node is a leaf, otherwise returns null;
	 */
	public Course getCourse(){
		return Course.wrap(entity.getCourse());
	}
}
