package model.entities;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import play.db.ebean.Model;

/**
 * 
 * @author Mihir Daptardar
 * 
 */
@Entity
@Table(name = "prereq_coreq_formulas")
public class EPrereqCoreqFormula extends Model {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "pcf_id_pk")
	private Integer id;
	@ManyToOne
	@JoinColumn(name = "pcf_parent_id_fk")
	private EPrereqCoreqFormula parent;
	@OneToMany(mappedBy = "parent")
	private List<EPrereqCoreqFormula> children;
	@Column(name = "pcf_junction_type")
	private String junction;
	@Column(name = "pcf_is_course")
	private Boolean isCourse;
	@ManyToOne
	@JoinColumn(name = "pcf_course_id_fk")
	private ECourse course;
	@Column(name = "pcf_is_positive")
	private Boolean isPositive;

	public Integer getId() {
		return id;
	}

	public String getJunction() {
		return junction;
	}

	public Boolean isCourse() {
		return isCourse;
	}

	public Boolean isPositive() {
		return isPositive;
	}

	public void setJunction(String junction) {
		this.junction = junction;
	}

	public void setIsCourse(Boolean isCourse) {
		this.isCourse = isCourse;
	}

	public void setIsPositive(Boolean isPositive) {
		this.isPositive = isPositive;
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
