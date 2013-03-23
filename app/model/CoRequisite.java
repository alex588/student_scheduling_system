package model;

import java.util.List;

import model.entities.EPrereqCoreqFormula;

public class CoRequisite extends Requisite {

	protected CoRequisite(Requisite parent, Junction junction,
			Boolean pcf_is_course, Course course, Boolean pcf_is_positive) {
		super(parent, junction, pcf_is_course, course, pcf_is_positive);
		// TODO Auto-generated constructor stub
	}

	public List<PreRequisite> getChildren(){
		return null;
	}
	
	public static CoRequisite createNode(Boolean isPositive, CoRequisite parent, Junction junction){
		CoRequisite newNode = new CoRequisite(parent, junction, false, null, isPositive);
		return newNode;
	}

	public static CoRequisite createLeaf(Boolean isPositive, CoRequisite parent, Course course){
		CoRequisite newLeaf = new CoRequisite(parent, null, true, course, isPositive);
		return newLeaf; 
	}

	static EPrereqCoreqFormula unwrap(CoRequisite prereq){
		return prereq == null ? null : prereq.entity;
	}
	
}
