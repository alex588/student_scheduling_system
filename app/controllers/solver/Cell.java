package controllers.solver;

class Cell {
	private Boolean isTaken;
	private Boolean isInitialized;
	
	protected Cell() {
		super();
		this.isTaken = false;
		this.isInitialized = false;
	}

	public Boolean isTaken() {
		return isTaken;
	}
	
	public Boolean isInitialized() {
		return isInitialized;
	}
	
	public void take(){
		this.isTaken = true;
		this.isInitialized = true;
	}
	
	public void skip(){
		this.isTaken = false;
		this.isInitialized = true;
	}
	
	public void reset() {
		this.isInitialized = false;
		this.isTaken = false;
	}
	
	
}
