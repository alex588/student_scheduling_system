package model.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
@Table(name = "prereq_coreq_formulas")
public class EPrereqCoreqFormula extends Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "pcf_id_pk")
	public Integer pcf_id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pcf_parent_id_fk")
	public EPrereqCoreqFormula parent;

	@OneToMany(mappedBy = "parent")
	public List<EPrereqCoreqFormula> children;

	public String pcf_junction_type;
	public Boolean pcf_is_course;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pcf_course_id_fk")
	public ECourse course;

	public Boolean pcf_is_positive;

	public static Finder<Long, EPrereqCoreqFormula> find = new Finder<Long, EPrereqCoreqFormula>(
			Long.class, EPrereqCoreqFormula.class);

	public Integer getPcf_id() {
		return pcf_id;
	}

	public String getPcf_junction_type() {
		return pcf_junction_type;
	}

	public Boolean getPcf_is_course() {
		return pcf_is_course;
	}

	
	public Boolean getPcf_is_positive() {
		return pcf_is_positive;
	}

	public void setPcf_id(Integer pcf_id) {
		this.pcf_id = pcf_id;
	}

	public void setPcf_junction_type(String pcf_junction_type) {
		this.pcf_junction_type = pcf_junction_type;
	}

	public void setPcf_is_course(Boolean pcf_is_course) {
		this.pcf_is_course = pcf_is_course;
	}


	public void setPcf_is_positive(Boolean pcf_is_positive) {
		this.pcf_is_positive = pcf_is_positive;
	}

	public EPrereqCoreqFormula getParent() {
		return parent;
	}

	public List<EPrereqCoreqFormula> getChildren() {
		return children;
	}

	public void setParent(EPrereqCoreqFormula parent) {
		this.parent = parent;
	}

	public void setChildren(List<EPrereqCoreqFormula> children) {
		this.children = children;
	}

	public ECourse getCourse() {
		return course;
	}

	public void setCourse(ECourse course) {
		this.course = course;
	}

}
