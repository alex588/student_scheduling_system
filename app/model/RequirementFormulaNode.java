package model;

public class RequirementFormulaNode {
	private Requirement req;
	boolean isPositive;
	
	RequirementFormulaNode(Requirement req, boolean isPositive) {
		super();
		this.req = req;
		this.isPositive = isPositive;
	}

	public Requirement getRequirement() {
		return req;
	}

	public boolean isPositive() {
		return isPositive;
	}

	
}
