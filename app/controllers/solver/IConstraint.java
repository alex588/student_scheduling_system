package controllers.solver;

/**
 * 
 * @author Alexey Tregubov
 * 
 *         this is general interface for all constraints.
 */
interface IConstraint {
	/**
	 * 
	 * @param matrix
	 *            reflects current state of the calculation.
	 * @return
	 */
	abstract public CheckResult check(StudyPlanMatrix matrix);

}