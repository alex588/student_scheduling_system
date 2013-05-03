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
@Table(name = "req_formulas")
public class ERequirementFormula extends Model {
	private static final long serialVersionUID = 1L;
	@Column(name = "rf_is_positive")
	private Boolean isPositive;
	@ManyToOne
	@JoinColumn(name = "rf_child_id_pk_fk")
	private ERequirement child;
	@ManyToOne
	@JoinColumn(name = "rf_parent_id_pk_fk")
	private ERequirement parent;

	public Boolean isPositive() {
		return isPositive;
	}

	public ERequirement getParent() {
		return parent;
	}

	public ERequirement getChild() {
		return child;
	}

	public void setIsPositive(Boolean isPositive) {
		this.isPositive = isPositive;
	}

	public void setParent(ERequirement parent) {
		this.parent = parent;
	}

	public void setChild(ERequirement child) {
		this.child = child;
	}

}
