package model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import play.db.ebean.Model;

/**
 * 
 * @author Mihir Daptardar
 * 
 */
@Entity
@Table(name = "course_groups_formulas")
public class ECourseGroupFormula extends Model {
	private static final long serialVersionUID = 1L;
	@Column(name = "cgf_is_positive")
	private Boolean isPositive;
	@ManyToOne
	@JoinColumn(name = "cgf_child_id_pk_fk")
	private ECourseGroup child;
	@ManyToOne
	@JoinColumn(name = "cgf_parent_id_pk_fk")
	private ECourseGroup parent;

	public Boolean isPositive() {
		return isPositive;
	}

	public ECourseGroup getChild() {
		return child;
	}

	public ECourseGroup getParent() {
		return parent;
	}

	public void setIsPositive(Boolean isPositive) {
		this.isPositive = isPositive;
	}

	public void setChild(ECourseGroup child) {
		this.child = child;
	}

	public void setParent(ECourseGroup parent) {
		this.parent = parent;
	}

}
