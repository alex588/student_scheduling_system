package controllers.solver;

/**
 * 
 * @author Alexey Tregubov
 * 
 */
class Cell {
	private Boolean isTaken;
	private Boolean isInitialized;
	private int col;
	private int row;

	protected Cell(int col, int row) {
		super();
		this.isTaken = false;
		this.isInitialized = false;
		// these are the coordinates in the table. Top left corner is zero.
		this.col = col;
		this.row = row;
	}

	/**
	 * Cell is taken if it equals to 1. Cell is not taken if it equals to 0.
	 * 
	 * @return
	 */
	public Boolean isTaken() {
		return isTaken;
	}

	/**
	 * Cell is initialized if it equals to 1 or 0 (was taken or was not taken).
	 * 
	 * @return
	 */
	public Boolean isInitialized() {
		return isInitialized;
	}

	/**
	 * put 1 in the cell. This means that course is taken.
	 */
	public void take() {
		this.isTaken = true;
		this.isInitialized = true;
	}

	/**
	 * put 0 in the cell. This means that course is not taken.
	 */
	public void skip() {
		this.isTaken = false;
		this.isInitialized = true;
	}

	/**
	 * make this call not initialized again.
	 */
	public void reset() {
		this.isInitialized = false;
		this.isTaken = false;
	}

	/**
	 * 
	 * @return 1 if taken, 0 in all other cases.
	 */
	public int value() {
		return this.isTaken ? 1 : 0;
	}

	public int getCol() {
		return col;
	}

	public int getRow() {
		return row;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Cell) {
			Cell anotherCell = (Cell) o;
			if ((this.col == anotherCell.col) && (this.row == anotherCell.row))
				return true;
			else
				return false;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.row;
	}

	@Override
	public String toString() {
		return this.isInitialized ? (this.isTaken ? "1" : "0") : "*";
	}
}
