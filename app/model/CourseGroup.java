package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import model.entities.ECourseGroup;
import com.avaje.ebean.Ebean;
import controllers.util.Converter;

/**
 * @author Alexey Tregubov
 */
public abstract class CourseGroup {
	protected ECourseGroup entity;

	protected CourseGroup() {
		super();
	}

	public static List<CourseGroup> getAll() {
		List<ECourseGroup> list = Ebean.find(ECourseGroup.class).findList();
		List<CourseGroup> courseGroupList = new ArrayList<>(list.size());

		for (ECourseGroup ecoursegroup : list) {
			CourseGroup coursegroup = CourseGroupFactory.wrap(ecoursegroup);
			courseGroupList.add(coursegroup);
		}
		return courseGroupList;

	}

	static public void delete(CourseGroup courseGroup) {
		if (courseGroup.entity != null)
			courseGroup.entity.delete();
	}

	public String getAbbreviation() {
		return this.entity.getAbbreviation() == null ? null : this.entity
				.getAbbreviation();
	}

	public static CourseGroup getByAbbreviation(String abbreviation) {
		if (abbreviation == null)
			throw new IllegalArgumentException("abbreviation cannot be null!!");
		List<ECourseGroup> eCourseGroupsList = Ebean.find(ECourseGroup.class)
				.where().eq("cg_abbr", abbreviation).findList();
		if (eCourseGroupsList.isEmpty())
			return null;
		else
			return CourseGroupFactory.wrap(eCourseGroupsList.get(0)); // unique
	}

	public static Integer findSimilarAbbrNo(String abbr) {
		List<ECourseGroup> eCourseGroupsList = Ebean.find(ECourseGroup.class)
				.where().like("cg_abbr", abbr + "%").findList();
		if (eCourseGroupsList.size() == 0)
			return eCourseGroupsList.size();

		else {
			List<String> listOfAbbr = new ArrayList<>();
			for (ECourseGroup e : eCourseGroupsList)
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

	public void update(String title, Boolean isSimple, Junction junction,
			String abbreviation, Boolean isVisible) {

		this.entity.setTitle(title);
		this.entity.setJnction(junction == null ? null : junction
				.toString());
		this.entity.setIsSimple(isSimple);
		this.entity.setAbbreviation(abbreviation);
		this.entity.update();
		this.entity.setIsVisible(isVisible);
	}

	public boolean isSimple() {
		return entity.isSimple();
	}

	public Integer getId() {
		return entity.getId();
	}

	public String getTitle() {
		return entity.getTitle();
	}

	public Junction getJunction() {
		return entity.getJunction() != null ? Junction
				.valueOf(entity.getJunction()) : null;
	}

	/**
	 * If course group is visible then it is available in UI. In complex course
	 * groups where intermediate node is created automatically, we must create
	 * invisible nodes.
	 * 
	 * @return
	 */
	public Boolean isVisible() {
		return entity.isVisible();
	}

	public static CourseGroup getById(Integer id) {
		ECourseGroup courseGroup = Ebean.find(ECourseGroup.class, id);
		return CourseGroupFactory.wrap(courseGroup);
	}

	public static List<CourseGroup> getAllVisible() {
		List<ECourseGroup> list = Ebean.find(ECourseGroup.class).where()
				.eq("cg_is_visible", true).findList();
		List<CourseGroup> cgList = new ArrayList<>();
		for (ECourseGroup eCourseGroup : list) {
			cgList.add(CourseGroupFactory.wrap(eCourseGroup));
		}
		return cgList;
	}

	@Override
	public int hashCode() {
		return entity.getId();
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof CourseGroup) {
			CourseGroup anotherCoursegroup = (CourseGroup) o;
			if (this.getId().equals(anotherCoursegroup.getId()))
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
	 * Performs set operations such as union(OR), intersection (AND),
	 * subtraction (AND not) if needed, and calculates the result set courses
	 * that constitute the group.
	 * 
	 * @return list of all courses in the group.
	 */
	public abstract List<Course> getAllCourses();

}
