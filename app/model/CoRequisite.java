package model;

import model.entities.EPrereqCoreqFormula;

/**
 * @author Mihir Daptardar
 */
public class CoRequisite extends Requisite {

	private CoRequisite() {
		super();
	}

	private CoRequisite(Requisite parent, Junction junction, Boolean isCourse,
			Course course, Boolean isPositive) {
		super(parent, junction, isCourse, course, isPositive);
	}

	public static CoRequisite createNode(Boolean isPositive,
			CoRequisite parent, Junction junction) {
		if (junction == null)
			throw new IllegalArgumentException(
					"Junction is null. Junction cannot be null in the intermediate node.");
		CoRequisite newNode = new CoRequisite(parent, junction, false, null,
				isPositive);
		return newNode;
	}

	public static CoRequisite createLeaf(Boolean isPositive,
			CoRequisite parent, Course course) {
		CoRequisite newLeaf = new CoRequisite(parent, null, true, course,
				isPositive);
		return newLeaf;
	}

	static EPrereqCoreqFormula unwrap(CoRequisite prereq) {
		return prereq == null ? null : prereq.entity;
	}

	@Override
	protected Requisite innerWrap(EPrereqCoreqFormula entity) {
		CoRequisite coreq = new CoRequisite();
		coreq.entity = entity;
		return coreq;
	}

	static CoRequisite wrap(EPrereqCoreqFormula entity) {
		CoRequisite coreq = new CoRequisite();
		coreq.entity = entity;
		return coreq;
	}

}
