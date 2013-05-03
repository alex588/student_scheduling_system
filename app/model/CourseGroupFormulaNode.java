package model;

/**
 * @author Mihir Daptardar
 */
public class CourseGroupFormulaNode {
	private CourseGroup cg;
	private Boolean isPositive;

	public CourseGroupFormulaNode(CourseGroup cg, Boolean isPositive) {
		super();
		this.cg = cg;
		this.isPositive = isPositive;
	}

	public CourseGroup getCg() {
		return cg;
	}

	public Boolean getIsPositive() {
		return isPositive;
	}

}
