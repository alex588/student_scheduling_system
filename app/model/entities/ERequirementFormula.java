package model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
@Table(name = "req_formulas")
public class ERequirementFormula extends Model{
	
	public Boolean rf_is_positive;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="rf_child_id_pk_fk")
	public ERequirement child;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="rf_parent_id_pk_fk")
	public ERequirement parent;
	
	public Boolean getRf_is_positive() {
		return rf_is_positive;
	}

	public ERequirement getParent() {
		return parent;
	}

	public ERequirement getChild() {
		return child;
	}

	public void setRf_is_positive(Boolean rf_is_positive) {
		this.rf_is_positive = rf_is_positive;
	}

	public void setParent(ERequirement parent) {
		this.parent = parent;
	}

	public void setChild(ERequirement child) {
		this.child = child;
	}
	
}
