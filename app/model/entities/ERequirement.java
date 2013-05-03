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
@Table(name = "requirements")
public class ERequirement extends Model {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "req_id_pk")
	private Integer id;
	@Column(name = "req_is_simple")
	private Boolean isSimple;
	@Column(name = "req_title")
	private String title;
	@Column(name = "req_abbr")
	private String abbreviation;
	@Column(name = "req_number_of_courses")
	private Integer numberOfCourses;
	@Column(name = "req_is_visible")
	private Boolean isVisible;
	@Column(name = "req_junction_type")
	private String junction;
	@ManyToOne
	@JoinColumn(name = "req_cg_id_fk")
	private ECourseGroup courseGroup;
	@OneToMany(mappedBy = "child")
	private List<ERequirementFormula> parents;
	@OneToMany(mappedBy = "parent")
	private List<ERequirementFormula> children;

	public void addChild(ERequirement child, boolean isPositive) {
		ERequirementFormula formula = new ERequirementFormula();
		formula.setChild(child);
		formula.setParent(this);
		formula.setIsPositive(isPositive);
		formula.save();
		this.children.add(formula);
		this.update();
		child.parents.add(formula);
		child.update();
	}

	public void addParent(ERequirement parent, boolean isPositive) {
		ERequirementFormula formula = new ERequirementFormula();
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

	public Boolean isSimple() {
		return isSimple;
	}

	public String getTitle() {
		return title;
	}

	public Integer getNumberOfCourses() {
		return numberOfCourses;
	}

	public Boolean isVisible() {
		return isVisible;
	}

	public String getJunction() {
		return junction;
	}

	public void setIsSimple(Boolean isSimple) {
		this.isSimple = isSimple;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setNumberOfCourses(Integer numberOfCourses) {
		this.numberOfCourses = numberOfCourses;
	}

	public void setIsVisible(Boolean isVisible) {
		this.isVisible = isVisible;
	}

	public void setJunction(String junction) {
		this.junction = junction;
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

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

}
