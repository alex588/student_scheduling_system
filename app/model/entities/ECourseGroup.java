package model.entities;

import java.util.List;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

import javax.persistence.*;

@Entity
@Table(name="course_groups")
public class ECourseGroup extends Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="cg_id_pk")
	public Integer cg_id;
	public String cg_title;
	public Boolean cg_is_simple;
	public String cg_junction_type;

	@ManyToMany
	@JoinTable(
			name="course_grp_courses",
			joinColumns={@JoinColumn(name="course_group_id_fk", referencedColumnName="cg_id_pk")},
			inverseJoinColumns={@JoinColumn(name="course_id_fk", referencedColumnName="course_id_pk")}
			)
	public List<ECourse> courseList;
	
		
	@OneToMany(mappedBy="child")
	public List<ECourseGroupFormula> parents;
	
	@OneToMany(mappedBy="parent")
	public List<ECourseGroupFormula> children;
	
	public void addChild(ECourseGroup child, boolean isPositive){
		ECourseGroupFormula formula = new ECourseGroupFormula();
		formula.setChild(child);
		formula.setParent(this);
		formula.setCgf_is_positive(isPositive);
		formula.save();
		
		this.refresh();
		child.refresh();		
	}
	
	public void addParent(ECourseGroup parent, boolean isPositive){
		ECourseGroupFormula formula = new ECourseGroupFormula();
		formula.setChild(this);
		formula.setParent(parent);
		formula.setCgf_is_positive(isPositive);
		formula.save();
		
		this.refresh();
		parent.refresh();
	}

	
	
	
	public static Finder<Long, ECourseGroup> find = new Finder<Long, ECourseGroup>(Long.class, ECourseGroup.class);

	public Integer getCg_id() {
		return cg_id;
	}

	public String getCg_title() {
		return cg_title;
	}

	public Boolean getCg_is_simple() {
		return cg_is_simple;
	}

	public String getCg_junction_type() {
		return cg_junction_type;
	}

	public void setCg_id(Integer cg_id) {
		this.cg_id = cg_id;
	}

	public void setCg_title(String cg_title) {
		this.cg_title = cg_title;
	}

	public void setCg_is_simple(Boolean cg_is_simple) {
		this.cg_is_simple = cg_is_simple;
	}

	public void setCg_junction_type(String cg_junction_type) {
		this.cg_junction_type = cg_junction_type;
	}

	public List<ECourse> getCourseList() {
		return courseList;
	}

	public void setCourseList(List<ECourse> courseList) {
		this.courseList = courseList;
	}

	public List<ECourseGroupFormula> getParents() {
		return parents;
	}

	public List<ECourseGroupFormula> getChildren() {
		return children;
	}

	public void setParents(List<ECourseGroupFormula> parents) {
		this.parents = parents;
	}

	public void setChildren(List<ECourseGroupFormula> children) {
		this.children = children;
	}
	
}
