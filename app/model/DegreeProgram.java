package model;

import java.util.ArrayList;
import java.util.List;
import model.entities.EDegreeProgram;
import model.entities.ERequirement;
import com.avaje.ebean.Ebean;

/**
 * @author Mihir Daptardar
 */
public class DegreeProgram {
	private EDegreeProgram entity;

	private DegreeProgram() {
		super();
	}

	public Integer getId() {
		return entity.getId();
	}

	public String getName() {
		return entity.getTitle();
	}

	public static List<DegreeProgram> getAll() {
		List<EDegreeProgram> list = Ebean.find(EDegreeProgram.class).findList();
		List<DegreeProgram> degreeList = new ArrayList<DegreeProgram>(list.size());
		for (EDegreeProgram edegreeProgram : list) {
			DegreeProgram degreeProgram = new DegreeProgram();
			degreeProgram.entity = edegreeProgram;
			degreeList.add(degreeProgram);
		}
		return degreeList;
	}

	public static DegreeProgram create(String title,
			List<? extends Requirement> requirements) {
		DegreeProgram degreeProgram = new DegreeProgram();
		EDegreeProgram newDegreeProgram = new EDegreeProgram();
		newDegreeProgram.setTitle(title);
		List<ERequirement> requirementList = new ArrayList<ERequirement>(
				requirements.size());

		for (Requirement requirement : requirements)
			requirementList.add(Requirement.unwrap(requirement));

		newDegreeProgram.setRequirementList(requirementList);
		newDegreeProgram.save();
		degreeProgram.entity = newDegreeProgram;
		return degreeProgram;
	}

	public void update(String title, List<? extends Requirement> requirements) {
		List<ERequirement> requirementList = new ArrayList<ERequirement>(
				requirements.size());

		for (Requirement requirement : requirements)
			requirementList.add(Requirement.unwrap(requirement));

		this.entity.setRequirementList(requirementList);
		this.entity.setTitle(title);
		this.entity.update();
		return;
	}

	public void removeAllRequirements() {
		List<ERequirement> eReqList = new ArrayList<ERequirement>();

		for (ERequirement eRequirement : this.entity.getRequirementList()) {
			eReqList.add((eRequirement));
		}
		for (ERequirement ereq : eReqList) {
			this.entity.getRequirementList().remove(ereq);
		}
		this.entity.update();
		return;
	}

	public List<Requirement> getRequirements() {
		List<Requirement> requirementList = new ArrayList<Requirement>(
				this.entity.getRequirementList().size());

		for (ERequirement eRequirement : this.entity.getRequirementList())
			requirementList.add(Requirement.wrap(eRequirement));

		return requirementList;
	}

	public static void delete(Integer dp_id) {
		EDegreeProgram eDegreeProgram = Ebean.find(EDegreeProgram.class, dp_id);
		if (eDegreeProgram != null)
			eDegreeProgram.delete();
		return;

	}

	public static DegreeProgram getById(Integer id) {
		EDegreeProgram eDegreeProgram = Ebean.find(EDegreeProgram.class, id);
		return wrap(eDegreeProgram);
	}

	static DegreeProgram wrap(EDegreeProgram eDegreeProgram) {
		DegreeProgram degreeProgram = new DegreeProgram();
		degreeProgram.entity = eDegreeProgram;
		return eDegreeProgram == null ? null : degreeProgram;
	}

	@Override
	public int hashCode() {
		return entity.getId();
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof DegreeProgram) {
			DegreeProgram anotherDegreeProgram = (DegreeProgram) o;
			if (this.getId().equals(anotherDegreeProgram.getId()))
				return true;
			else
				return false;
		}
		return false;
	}

	@Override
	public String toString() {
		String result = this.entity.getTitle();
		return result;
	}

}
