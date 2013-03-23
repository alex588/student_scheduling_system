package model.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;
@Entity
@Table(name="requirements")
public class ERequirement extends Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="req_id_pk")
	public Integer req_id;
	
	public Boolean req_is_simple;
	public String req_title;
	public Integer req_number_of_courses;
	public Boolean req_is_visible;
	public String req_junction_type;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="req_cg_id_fk")
	public ECourseGroup courseGroup;

	@OneToMany(mappedBy="child")
	public List<ERequirementFormula> parents;
	
	@OneToMany(mappedBy="parent")
	public List<ERequirementFormula> children;
	
	public void addChild(ERequirement child, boolean isPositive){
		ERequirementFormula formula = new ERequirementFormula();
		formula.setChild(child);
		formula.setParent(this);
		formula.setRf_is_positive(isPositive);
		formula.save();
		
		this.refresh();
		child.refresh();		
	}
	
	public void addParent(ERequirement parent, boolean isPositive){
		ERequirementFormula formula = new ERequirementFormula();
		formula.setChild(this);
		formula.setParent(parent);
		formula.setRf_is_positive(isPositive);
		formula.save();
		
		this.refresh();
		parent.refresh();
	}
	
	public static Finder<Long, ERequirement> find = new Finder<Long, ERequirement>(Long.class, ERequirement.class);

	public Integer getReq_id() {
		return req_id;
	}

	public Boolean getReq_is_simple() {
		return req_is_simple;
	}

	public String getReq_title() {
		return req_title;
	}

	public Integer getReq_number_of_courses() {
		return req_number_of_courses;
	}

	public Boolean getReq_is_visible() {
		return req_is_visible;
	}

	public String getReq_junction_type() {
		return req_junction_type;
	}

	public void setReq_id(Integer req_id) {
		this.req_id = req_id;
	}

	public void setReq_is_simple(Boolean req_is_simple) {
		this.req_is_simple = req_is_simple;
	}

	public void setReq_title(String req_title) {
		this.req_title = req_title;
	}

	public void setReq_number_of_courses(Integer req_number_of_courses) {
		this.req_number_of_courses = req_number_of_courses;
	}

	public void setReq_is_visible(Boolean req_is_visible) {
		this.req_is_visible = req_is_visible;
	}

	public void setReq_junction_type(String req_junction_type) {
		this.req_junction_type = req_junction_type;
	}

	public ECourseGroup getCourseGroup() {
		return courseGroup;
	}

	public void setCourseGroup(ECourseGroup courseGroup) {
		this.courseGroup = courseGroup;
	}

	public List<ERequirementFormula> getParents() {
		return parents;
	}

	public List<ERequirementFormula> getChildren() {
		return children;
	}

	public void setParents(List<ERequirementFormula> parents) {
		this.parents = parents;
	}

	public void setChildren(List<ERequirementFormula> children) {
		this.children = children;
	}

	
	
}
