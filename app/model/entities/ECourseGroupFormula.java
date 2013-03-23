package model.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import play.db.ebean.Model;
@Entity
@Table(name="course_groups_formulas")

public class ECourseGroupFormula extends Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Boolean cgf_is_positive;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="cgf_child_id_pk_fk")
	public ECourseGroup child;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="cgf_parent_id_pk_fk")
	public ECourseGroup parent;

	public Boolean getCgf_is_positive() {
		return cgf_is_positive;
	}

	public ECourseGroup getChild() {
		return child;
	}

	public ECourseGroup getParent() {
		return parent;
	}

	public void setCgf_is_positive(Boolean cgf_is_positive) {
		this.cgf_is_positive = cgf_is_positive;
	}

	public void setChild(ECourseGroup child) {
		this.child = child;
	}

	public void setParent(ECourseGroup parent) {
		this.parent = parent;
	}
	
	

}
