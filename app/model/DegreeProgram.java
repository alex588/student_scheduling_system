package model;

import java.util.ArrayList;
import java.util.List;

import model.entities.EDegreeProgram;
import model.entities.ERequirement;

import com.avaje.ebean.Ebean;

public class DegreeProgram {
	private EDegreeProgram entity;

	private DegreeProgram() {
		super();

	}

	public Integer getId() {
		return entity.dp_id;
	}

	public String getName() {
		return entity.dp_title;

	}

	public static List<DegreeProgram> getAll() {
		List<EDegreeProgram> list = Ebean.find(EDegreeProgram.class).findList();
		List<DegreeProgram> degreeList = new ArrayList<>(list.size());

		for (EDegreeProgram edegreeProgram : list) {
			DegreeProgram degreeProgram = new DegreeProgram();
			degreeProgram.entity = edegreeProgram;
			degreeList.add(degreeProgram);

		}
		return degreeList;

	}

	static DegreeProgram create(String dp_title,
			List<? extends Requirement> requirements) {
		DegreeProgram degreeProgram = new DegreeProgram();
		EDegreeProgram newDegreeProgram = new EDegreeProgram();
		newDegreeProgram.setDp_title(dp_title);
		List<ERequirement> requirementList = new ArrayList<ERequirement>(
				requirements.size());
		for (Requirement requirement : requirements) {
			requirementList.add(Requirement.unwrap(requirement));
		}
		newDegreeProgram.setRequirementList(requirementList);
		newDegreeProgram.save();
		degreeProgram.entity = newDegreeProgram;
		return degreeProgram;
	}

	public void update(String dp_title, List<? extends Requirement> requirements) {
		List<ERequirement> requirementList = new ArrayList<ERequirement>(
				requirements.size());
		for (Requirement requirement : requirements) {
			requirementList.add(Requirement.unwrap(requirement));
		}
		this.entity.setRequirementList(requirementList);
		this.entity.setDp_title(dp_title);
		this.entity.update();

	}
	
	public List<? extends Requirement> getRequirements(){
		List<Requirement> requirementList = new ArrayList<Requirement>(
				this.entity.getRequirementList().size());
		for (ERequirement eRequirement : this.entity.getRequirementList()) {
			requirementList.add(Requirement.wrap(eRequirement));
		}
		return requirementList;
	}

	public static void delete(Integer dp_id) {
		EDegreeProgram eDegreeProgram = Ebean.find(EDegreeProgram.class, dp_id);
		eDegreeProgram.delete();

	}

}
