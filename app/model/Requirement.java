package model;

import java.util.ArrayList;
import java.util.List;

import model.entities.ERequirement;

import com.avaje.ebean.Ebean;

public abstract class Requirement {
	protected ERequirement entity;

	protected Requirement(){
		
	}

	public static List<Requirement> getAll() {
		List<ERequirement> list = Ebean.find(ERequirement.class).findList();
		List<Requirement> reqList = new ArrayList<>(list.size());

		for (ERequirement eRequirement : list) {
			Requirement requirement;
			if (eRequirement.getReq_is_simple())
				requirement = new SimpleRequirement();
			else
				requirement = new ComplexRequirement();
			requirement.entity = eRequirement;
			reqList.add(requirement);
		}
		return reqList;
	}

	public static ERequirement unwrap(Requirement requirement) {
		return requirement == null ? null : requirement.entity;
	}
	
	public static Requirement wrap(ERequirement eRequirement){
		Requirement requirement;
		if (eRequirement.getReq_is_simple())
			requirement = new SimpleRequirement();
		else
			requirement = new ComplexRequirement();
		requirement.entity = eRequirement;
		return eRequirement == null ? null : requirement;
		
	} 

	public static void delete(Integer req_id) {
		Ebean.delete(req_id);
	}

	public Integer getId() {
		return entity.req_id;
	}

	public String getTitle() {
		return entity.getReq_title();
	}

	public abstract List<Course> getCourses();
}