package model.entities;

import java.util.List;
import play.db.ebean.Model;
import javax.persistence.*;

/**
 * 
 * @author Mihir Daptardar
 * 
 */
@Entity
@Table(name = "course_groups")
public class ECourseGroup extends Model {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "cg_id_pk")
	private Integer id;
	@Column(name = "cg_title")
	private String title;
	@Column(name = "cg_abbr")
	private String abbreviation;
	@Column(name = "cg_is_simple")
	private Boolean isSimple;
	@Column(name = "cg_is_visible")
	private Boolean isVisible;
	@Column(name = "cg_junction_type")
	private String junction;
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "course_grp_courses", joinColumns = { @JoinColumn(name = "course_group_id_fk", referencedColumnName = "cg_id_pk") }, inverseJoinColumns = { @JoinColumn(name = "course_id_fk", referencedColumnName = "course_id_pk") })
	private List<ECourse> courseList;
	@OneToMany(mappedBy = "child")
	private List<ECourseGroupFormula> parents;
	@OneToMany(mappedBy = "parent")
	private List<ECourseGroupFormula> children;

	public void addChild(ECourseGroup child, boolean isPositive) {
		ECourseGroupFormula formula = new ECourseGroupFormula();
		formula.setChild(child);
		formula.setParent(this);
		formula.setIsPositive(isPositive);
		formula.save();
		this.children.add(formula);
		this.update();
		child.parents.add(formula);
		child.update();
	}

	public void addParent(ECourseGroup parent, boolean isPositive) {
		ECourseGroupFormula formula = new ECourseGroupFormula();
		formula.setChild(this);
		formula.setParent(parent);
		formula.setIsPositive(isPositive);
		formula.save();
		this.parents.add(formula);
		this.update();
		parent.children.add(formula);
		parent.update();
	}

	public Integer getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public Boolean isSimple() {
		return isSimple;
	}

	public String getJunction() {
		return junction;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setIsSimple(Boolean isSimple) {
		this.isSimple = isSimple;
	}

	public void setJnction(String junction) {
		this.junction = junction;
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

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public Boolean isVisible() {
		return isVisible;
	}

	public void setIsVisible(Boolean isVisible) {
		this.isVisible = isVisible;
	}

}
