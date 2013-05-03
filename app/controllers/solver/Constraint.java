package controllers.solver;

/**
 * 
 * @author Alexey Tregubov
 * 
 */
abstract class Constraint implements IConstraint {
	private int id; // this id is used only in hash code function.
	private String name; // used only in toString() method.

	protected Constraint(int id, String name) {
		this.id = id;
		this.name = name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * controllers.solver.IConstraint#check(controllers.solver.StudyPlanMatrix)
	 */
	@Override
	abstract public CheckResult check(StudyPlanMatrix matrix);

	@Override
	public int hashCode() {
		return id;
	}

	@Override
	public boolean equals(Object o) {
		if ((o != null) && (o instanceof Constraint)) {
			Constraint anotherConstr = (Constraint) o;
			return (this.id == anotherConstr.id);
		}
		return false;
	}

	@Override
	public String toString() {
		return this.name;
	}
}
