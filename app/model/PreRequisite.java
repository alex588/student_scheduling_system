package model;

import model.entities.EPrereqCoreqFormula;

/**
 * @author Mihir Daptardar
 */
public class PreRequisite extends Requisite {
	protected PreRequisite() {
		super();
	}

	protected PreRequisite(PreRequisite parent, Junction junction,
			Boolean pcf_is_course, Course course, Boolean pcf_is_positive) {
		super(parent, junction, pcf_is_course, course, pcf_is_positive);
	}

	public static PreRequisite createNode(Boolean isPositive,
			PreRequisite parent, Junction junction) {
		if (junction == null)
			throw new IllegalArgumentException(
					"Junction is null. Junction cannot be null in the intermediate node.");
		PreRequisite newNode = new PreRequisite(parent, junction, false, null,
				isPositive);
		return newNode;
	}

	public static PreRequisite createLeaf(Boolean isPositive,
			PreRequisite parent, Course course) {
		PreRequisite newLeaf = new PreRequisite(parent, null, true, course,
				isPositive);
		return newLeaf;
	}

	static EPrereqCoreqFormula unwrap(PreRequisite prereq) {
		return prereq == null ? null : prereq.entity;
	}

	@Override
	protected PreRequisite innerWrap(EPrereqCoreqFormula entity) {
		PreRequisite prereq = new PreRequisite();
		prereq.entity = entity;
		return prereq;
	}

	static PreRequisite wrap(EPrereqCoreqFormula entity) {
		PreRequisite prereq = new PreRequisite();
		prereq.entity = entity;
		return prereq;
	}
}
