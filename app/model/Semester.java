package model;

import java.util.ArrayList;
import java.util.List;

import model.entities.ESemester;

import com.avaje.ebean.Ebean;

public class Semester {
	
	private ESemester entity;
	public static Semester FALL, SPRING, SUMMER1, SUMMER2, WINTER;
	
	static {
			for (Semester sem : getAll()){
				if (sem.getName().equalsIgnoreCase("fall")){
					FALL = sem;
				}
				else if(sem.getName().equalsIgnoreCase("spring")){
					SPRING = sem;
				}
				else if(sem.getName().equalsIgnoreCase("Summer I")){
					SUMMER1 = sem;
				}
				else if(sem.getName().equalsIgnoreCase("Summer II")){
					SUMMER2 = sem;
				}
				else if(sem.getName().equalsIgnoreCase("winter")){
					WINTER = sem;
				}
			}
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

	static Semester create(Integer semester_id, String semester_name) {
		Semester semester = new Semester();
		ESemester newSemester = new ESemester();
		newSemester.setSemester_id(semester_id);
		newSemester.setSemester_name(semester_name);
		Ebean.save(newSemester);

		newSemester = Ebean.find(ESemester.class, newSemester.getSemester_id());

		semester.entity = newSemester;
		return semester;
	}

	// has to create a new entity/record/tuple in database and returns its
	// object

	public static ESemester upwrap(Semester semester) {
		return semester == null ? null : semester.entity;
	}

	public static Semester wrap (ESemester eSemester){
		Semester semester = new Semester();
		semester.entity = eSemester;
		return eSemester == null ? null : semester;
	}
	
	static public void delete(Integer semester_id) {
		ESemester eSemester = Ebean.find(ESemester.class, semester_id);
		eSemester.delete();

	}

	public Integer getId() {
		return entity.getSemester_id();
	}

	public String getName() {
		return entity.getSemester_name();
	}

	@Override
	public int hashCode() {
		return entity.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Semester) {
			Semester anotherSem = (Semester) o;
			return (this.getId() == anotherSem.getId());
		} else
			return false;
	}

}
