package model;

public class RequiremenFormulaNode {
	private Requirement req;
	boolean isPositive;
	
	RequiremenFormulaNode(Requirement req, boolean isPositive) {
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
