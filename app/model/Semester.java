package model;

import java.util.ArrayList;
import java.util.List;
import model.entities.ESemester;
import com.avaje.ebean.Ebean;

/**
 * @author Mihir Daptardar
 */
public class Semester {

	private ESemester entity;
	public static Semester FALL, SPRING, SUMMER1, SUMMER2, WINTER;

	static {
		for (Semester sem : getAll()) {
			if (sem.getName().equalsIgnoreCase("fall")) {
				FALL = sem;
			} else if (sem.getName().equalsIgnoreCase("spring")) {
				SPRING = sem;
			} else if (sem.getName().equalsIgnoreCase("Summer I")) {
				SUMMER1 = sem;
			} else if (sem.getName().equalsIgnoreCase("Summer II")) {
				SUMMER2 = sem;
			} else if (sem.getName().equalsIgnoreCase("winter")) {
				WINTER = sem;
			}
		}
	}

	public static Integer getNumberOfSemesters() {
		Integer num = getAll().size();
		return num;
	}

	public static Semester findByName(String sSemester) {
		if (sSemester == null || sSemester == "")
			return null;
		else if (sSemester.equalsIgnoreCase("fall"))
			return FALL;
		else if (sSemester.equalsIgnoreCase("spring"))
			return SPRING;
		else if (sSemester.equalsIgnoreCase("summer I"))
			return SUMMER1;
		else if (sSemester.equalsIgnoreCase("summer II"))
			return SUMMER2;
		else
			return WINTER;
	}

	public static List<Semester> getAll() {
		List<ESemester> list = Ebean.find(ESemester.class).findList();
		List<Semester> semList = new ArrayList<>(list.size());
		for (ESemester esemester : list) {
			Semester semester = new Semester();
			semester.entity = esemester;
			semList.add(semester);
		}
		return semList;
	}

	public static ESemester unwrap(Semester semester) {
		return semester == null ? null : semester.entity;
	}

	public static Semester wrap(ESemester eSemester) {
		Semester semester = new Semester();
		semester.entity = eSemester;
		return eSemester == null ? null : semester;
	}

	static public void delete(Integer semester_id) {
		ESemester eSemester = Ebean.find(ESemester.class, semester_id);
		eSemester.delete();
	}

	public Integer getId() {
		return entity.getId();
	}

	public String getName() {
		return entity.getName();
	}

	@Override
	public int hashCode() {
		return entity.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		else if (o instanceof Semester) {
			Semester anotherSem = (Semester) o;
			return ((anotherSem != null) && this.getId() == anotherSem.getId());
		} else
			return false;
	}

	@Override
	public String toString() {
		return this.getName();
	}

	public String toShortString() {
		return this.getName().substring(0, 1);
	}
}
