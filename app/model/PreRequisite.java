package model;

import java.util.ArrayList;
import java.util.List;

import com.avaje.ebean.Ebean;
import com.google.common.collect.Lists;

import model.entities.EPrereqCoreqFormula;

public class PreRequisite extends Requisite {
	protected PreRequisite(){
		super();
	}

	protected PreRequisite(PreRequisite parent, Junction junction,
			Boolean pcf_is_course, Course course,
			Boolean pcf_is_positive) {
		super(parent, junction, pcf_is_course, course, pcf_is_positive);
	}

	public List<PreRequisite> getChildren(){
		entity.refresh();
		List<PreRequisite> children = new ArrayList<>(entity.getChildren().size());
		for (EPrereqCoreqFormula child : entity.getChildren())
			children.add(wrap(child));
		return children;
	}
	
	public static PreRequisite createNode(Boolean isPositive, PreRequisite parent, Junction junction){
		PreRequisite newNode = new PreRequisite(parent, junction, false, null, isPositive);
		return newNode;
	}

	public static PreRequisite createLeaf(Boolean isPositive, PreRequisite parent, Course course){
		PreRequisite newLeaf = new PreRequisite(parent, null, true, course, isPositive);
		return newLeaf; 
	}

	static EPrereqCoreqFormula unwrap(PreRequisite prereq){
		return prereq == null ? null : prereq.entity;
	}
	
	static PreRequisite wrap(EPrereqCoreqFormula entity){
		PreRequisite prereq = new PreRequisite();
		prereq.entity = entity;
		return prereq;
	}
}
