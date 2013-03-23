package model;

import java.util.ArrayList;
import java.util.List;

import model.entities.ECourse;
import model.entities.ECourseAvailability;
import model.entities.EPrereqCoreqFormula;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.ExpressionList;

public class Course {
	private ECourse entity;

	private Course() {
		super();
	}

	public enum Location {

		ONCAMPUS {
			@Override
			public String toString() {
				return "ONCAMPUS";
			}
		},
		ONLINE {
			@Override
			public String toString() {
				return "ONLINE";
			}
		},
		BOTH {
			@Override
			public String toString() {
				return "BOTH";
			}

		};

		public static Location getLocationForString(String str) {
			if (str.equalsIgnoreCase(ONCAMPUS.toString()))
				return ONCAMPUS;
			else if (str.equalsIgnoreCase(ONLINE.toString()))
				return ONLINE;
			else
				return BOTH;
		}
	}

	public static List<Course> getAll() {
		List<ECourse> list = Ebean.find(ECourse.class).findList();
		List<Course> courseList = new ArrayList<>(list.size());

		for (ECourse ecourse : list) {
			Course course = new Course();
			course.entity = ecourse;
			courseList.add(course);

		}
		return courseList;

	}

	public static Course getById(Integer id) {
		ECourse course = Ebean.find(ECourse.class, id);
		return wrap(course);

	}

	static Course wrap(ECourse ecourse) {
		Course course = new Course();
		course.entity = ecourse;
		return ecourse == null ? null : course;
	}

	static ECourse unwrap(Course course) {
		return course == null ? null : course.entity;
	}

	public static Course create(Prefix course_prefix, Integer course_number,
			String course_title, Integer course_credits, Integer course_level,
			PreRequisite prereq, CoRequisite coreq) {
		Course course = new Course();
		ECourse newCourse = new ECourse();
		newCourse.setCourse_prefix(Prefix.unwrap(course_prefix));
		newCourse.setCourse_number(course_number);
		newCourse.setCourse_title(course_title);
		newCourse.setCourse_credits(course_credits);
		newCourse.setCourse_level(course_level);

		newCourse.setPrereq(PreRequisite.unwrap(prereq));
		newCourse.setCoreq(CoRequisite.unwrap(coreq));

		Ebean.save(newCourse);
		course.entity = newCourse;
		return course;
	}

	// has to create a new entity/record/tuple in database and returns its
	// object

	public void update(String course_title, Integer course_credits) {

		this.entity.setCourse_title(course_title);
		this.entity.setCourse_credits(course_credits);
		this.entity.update();
	}

	static public void delete(Integer course_id) {
		ECourse ecourse = Ebean.find(ECourse.class, course_id);
		ecourse.delete();
	}

	public void setPrereq(PreRequisite prereq) {
		entity.setPrereq(PreRequisite.unwrap(prereq));
		// save in database
		Ebean.save(entity);
	}

	public void setCoreq(CoRequisite coreq) {
		entity.setCoreq(CoRequisite.unwrap(coreq));
		// save in database
		Ebean.save(entity);
	}

	public Integer getId() {
		return entity.getCourse_id();
	}

	public String getTitle() {
		return entity.getCourse_title();
	}

	public Integer getCredits() {
		return entity.getCourse_credits();
	}

	public Integer getPrefixId() {
		return entity.getCourse_prefix().getPrefixId();
	}

	public Integer getCourseNo() {
		return entity.getCourse_number();
	}

	public Location getLocation() {
		if (entity.getAvailableCourse().isEmpty()) {
			return null;
		} else {
			ECourseAvailability courseAvailability = entity
					.getAvailableCourse().get(0); // First element
			String location = courseAvailability.getLocation();
			return Location.getLocationForString(location);
		}
	}

	/**
	 * 
	 * @param prefix
	 *            - pass the prefix object
	 * @param course
	 *            - pass the course object
	 * @return - return a concatentation of the string and course eg. (CS451)
	 */

	public static String getPrefixCourseNo(Prefix prefix, Course course) {
		return prefix.getName().toString() + course.getCourseNo().toString();
	}

	public Boolean isAvailable(Semester sem, Integer year) {
		Term inputTerm = Term.create(sem, year);
		for (ECourseAvailability record : this.entity.getAvailableCourse()) {
			Term availableTerm = Term.create(
					Semester.wrap(record.getSemester()), record.getYear());

			if (inputTerm.equals(availableTerm))
				return true;
		}
		return false;
	}

	// FIXME: left out location of the course

	/**
	 * 
	 * @param sem
	 * @param year
	 * @param isAvailable
	 * @param location
	 *            - Its a emum. It can only be three values: ONCAMPUS, ONLINE,
	 *            BOTH (case sensitive)
	 */

	public void setCourseAvailability(Semester sem, Integer year,
			Boolean isAvailable, Location location) {

		if (!isAvailable(sem, year) && !isAvailable) {
			return;
		}

		else if (!isAvailable(sem, year) && isAvailable) {
			ECourseAvailability newEntry = new ECourseAvailability();
			newEntry.setCourse(entity);
			newEntry.setYear(year);
			newEntry.setLocation(location.toString());
			newEntry.setSemester(Semester.upwrap(sem));
			newEntry.save();
		}

		else if (isAvailable(sem, year) && !isAvailable) {
			List<ECourseAvailability> list = Ebean
					.find(ECourseAvailability.class).where()
					.eq("semester", sem).eq("year", year).eq("course", entity)
					.findList();

			if (!list.isEmpty()) {
				list.get(0).delete(); // unique entry!
			}
		}

		else {
			ECourseAvailability entry = null;
			List<ECourseAvailability> list = Ebean
					.find(ECourseAvailability.class).where()
					.eq("semester", sem).eq("year", year).eq("course", entity)
					.findList();

			if (!list.isEmpty()) {
				entry = list.get(0); // unique entry!
			}
			entry.setCourse(entity);

			if (entry.getLocation().equals(location.toString())) {
				return;
			}

			else if (entry.getLocation().equalsIgnoreCase(
					Location.BOTH.toString())
					&& !location.toString().equalsIgnoreCase(
							Location.BOTH.toString())) {
				entry.setLocation(location.toString());
			}

			else if (!entry.getLocation().equalsIgnoreCase(
					Location.BOTH.toString())
					&& location.toString().equalsIgnoreCase(
							Location.BOTH.toString())) {
				entry.setLocation(Location.BOTH.toString());
			}

			else if (entry.getLocation().equalsIgnoreCase(
					Location.ONCAMPUS.toString())
					&& location.toString().equalsIgnoreCase(
							Location.ONLINE.toString())) {
				entry.setLocation(Location.BOTH.toString());
			}

			else if (entry.getLocation().equalsIgnoreCase(
					Location.ONLINE.toString())
					&& location.toString().equalsIgnoreCase(
							Location.ONCAMPUS.toString())) {
				entry.setLocation(Location.BOTH.toString());
			}

		}

	}

}