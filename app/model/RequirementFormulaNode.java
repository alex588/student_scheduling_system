package model;

/**
 * @author Mihir Daptardar
 * 
 */
public class RequirementFormulaNode {
	private Requirement req;
	private Boolean isPositive;
	
	RequirementFormulaNode(Requirement req, boolean isPositive) {
		super();
		this.req = req;
		this.isPositive = isPositive;
	}

	public Requirement getRequirement() {
		return req;
	}

	public Boolean isPositive() {
		return isPositive;
	}

	
}
