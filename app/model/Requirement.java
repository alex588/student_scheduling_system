package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import model.entities.ERequirement;
import com.avaje.ebean.Ebean;
import controllers.util.Converter;

/**
 * @author Mihir Daptardar
 * 
 */
public abstract class Requirement {
	protected ERequirement entity;

	protected Requirement() {

	}

	public static List<Requirement> getAll() {
		List<ERequirement> list = Ebean.find(ERequirement.class).findList();
		List<Requirement> reqList = new ArrayList<Requirement>(list.size());

		for (ERequirement eRequirement : list) {
			Requirement requirement;
			if (eRequirement.isSimple())
				requirement = new SimpleRequirement();
			else
				requirement = new ComplexRequirement();
			requirement.entity = eRequirement;
			reqList.add(requirement);
		}
		return reqList;
	}

	public static List<Requirement> getAllVisible() {
		List<ERequirement> list = Ebean.find(ERequirement.class).where()
				.eq("req_is_visible", true).findList();
		List<Requirement> reqList = new ArrayList<Requirement>();
		for (ERequirement eRequirement : list) {
			reqList.add(wrap(eRequirement));
		}
		return reqList;
	}

	public static ERequirement unwrap(Requirement requirement) {
		return requirement == null ? null : requirement.entity;
	}

	public static Requirement wrap(ERequirement eRequirement) {
		Requirement requirement;
		if (eRequirement.isSimple())
			requirement = new SimpleRequirement();
		else
			requirement = new ComplexRequirement();
		requirement.entity = eRequirement;
		return eRequirement == null ? null : requirement;

	}

	public static void delete(Requirement req) {
		if (req.entity != null)
			req.entity.delete();
	}

	public Integer getId() {
		return entity.getId();
	}

	public String getTitle() {
		return entity.getTitle();
	}

	public Boolean isSimple() {
		return entity.isSimple();
	}

	public static Requirement getById(Integer id) {
		ERequirement eRequirement = Ebean.find(ERequirement.class, id);
		return wrap(eRequirement);
	}

	public String getAbbreviation() {
		return this.entity.getAbbreviation() == null ? null : this.entity
				.getAbbreviation();
	}

	/**
	 * If requirement is visible then it is available in UI. In complex
	 * requirements where intermediate node is created automatically, we must
	 * create invisible nodes.
	 * 
	 * @return
	 */
	public Boolean isVisible() {
		return this.entity.isVisible();
	}

	public static Integer findSimilarAbbrNo(String abbr) {
		List<ERequirement> eRequirementList = Ebean.find(ERequirement.class)
				.where().like("req_abbr", abbr).findList();
		if (eRequirementList.size() == 0)
			return eRequirementList.size();
		else {
			List<String> listOfAbbr = new ArrayList<String>();
			for (ERequirement e : eRequirementList)
				listOfAbbr.add(e.getAbbreviation());
			Collections.sort(listOfAbbr);
			String lastAbbr = listOfAbbr.get(listOfAbbr.size() - 1);
			Integer length = lastAbbr.length();
			String lastNo = lastAbbr.substring(length - 1, length);
			if (Converter.isInteger(lastNo))
				return Integer.parseInt(lastNo);
			else
				return 0;
		}
	}

	public static Requirement getByAbbreviation(String abbreviation) {
		if (abbreviation == null)
			throw new IllegalArgumentException("abbreviation cannot be null!!");
		List<ERequirement> eRequirementList = Ebean.find(ERequirement.class)
				.where().eq("req_abbr", abbreviation).findList();
		if (eRequirementList.isEmpty())
			return null;
		else
			return Requirement.wrap(eRequirementList.get(0)); // abbreviation is
																// always unique
	}

	@Override
	public int hashCode() {
		return entity.getId();
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Requirement) {
			Requirement anotherRequirement = (Requirement) o;
			if (this.getId().equals(anotherRequirement.getId()))
				return true;
			else
				return false;
		}
		return false;
	}

	@Override
	public String toString() {
		String result = this.entity.getAbbreviation() == null ? this.entity
				.getTitle() : this.entity.getAbbreviation();
		return result;
	}

	/**
	 * In simple requirement this method is equivalent to
	 * getCourseGroup().getAllCourses(). In complex requirement it recursively
	 * collects courses from the subgroups.
	 * 
	 * @return
	 */
	public abstract List<Course> getCourses();

	/**
	 * 
	 * @return plain list of all requirements that constitute formula of this
	 *         complex requirement.
	 */
	public abstract List<Requirement> getAllSubrequirements();

}
