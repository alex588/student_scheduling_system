package model;

import java.util.ArrayList;
import java.util.List;
import model.entities.ECourse;
import model.entities.ECourseAvailability;
import com.avaje.ebean.Ebean;

/**
 * @author Alexey Tregubov, Mihir Daptardar.
 */
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

	public static Course getByPrefixAndNumber(Prefix prefix, Integer number) {
		ECourse ecourse = Ebean.find(ECourse.class).where()
				.eq("course_prefix_id_fk", prefix.getId())
				.eq("course_number", number).findList().get(0);
		return ecourse == null ? null : wrap(ecourse);
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

	public static Course create(Prefix coursePrefix, Integer courseNumber,
			String courseTitle, Integer courseCredits, Integer courseLevel,
			PreRequisite prereq, CoRequisite coreq) {
		Course course = new Course();
		ECourse newCourse = new ECourse();
		newCourse.setCoursePrefix(Prefix.unwrap(coursePrefix));
		newCourse.setCourseNumber(courseNumber);
		newCourse.setTitle(courseTitle);
		newCourse.setCourseCredits(courseCredits);
		newCourse.setCourseLevel(courseLevel);

		newCourse.setPrereq(PreRequisite.unwrap(prereq));
		newCourse.setCoreq(CoRequisite.unwrap(coreq));

		Ebean.save(newCourse);
		course.entity = newCourse;
		return course;
	}

	public void update(String title, Integer courseCredits) {

		this.entity.setTitle(title);
		this.entity.setCourseCredits(courseCredits);
		this.entity.update();
	}

	static public void delete(Integer courseId) {
		ECourse ecourse = Ebean.find(ECourse.class, courseId);
		if (ecourse != null)
			ecourse.delete();
		return;
	}

	public void setPrereq(PreRequisite prereq) {
		entity.setPrereq(PreRequisite.unwrap(prereq));
		entity.update();
	}

	public void setCoreq(CoRequisite coreq) {
		entity.setCoreq(CoRequisite.unwrap(coreq));
		entity.update();
	}

	public Integer getId() {
		return entity.getId();
	}

	public String getTitle() {
		return entity.getCourseTitle();
	}

	public Integer getCredits() {
		return entity.getCourseCredits();
	}

	public Integer getCourseNo() {
		return entity.getCourseNumber();
	}

	public Location getLocation(Semester sem, Integer year) {
		if (sem == null)
			return Location.BOTH;
		Term inputTerm = Term.create(sem, year);
		Location generalLocation = null;
		Location specificLocation = null;
		for (ECourseAvailability record : this.entity.getAvailableCourse()) {
			Term availableTerm = Term.create(
					Semester.wrap(record.getSemester()), year == null ? null
							: record.getYear());

			if (inputTerm.getSemester().equals(availableTerm.getSemester())) {
				if (availableTerm.getYear() != null
						&& availableTerm.getYear().equals(inputTerm.getYear())) {
					specificLocation = Location.getLocationForString(record
							.getLocation());
				}
				if (availableTerm.getYear() == null) {
					generalLocation = Location.getLocationForString(record
							.getLocation());
				}
			}
		}
		if (specificLocation != null)
			return specificLocation;
		else
			return generalLocation;
	}

	public Prefix getPrefix() {
		return Prefix.wrap(this.entity.getCoursePrefix());
	}

	@Override
	public String toString() {
		String result = this.getPrefix().getName()
				+ this.entity.getCourseNumber().toString();
		return result;
	}

	/**
	 * checks general course availability.
	 * 
	 * @param term
	 *            term - semester and year
	 * @param year
	 * @return
	 */
	public Boolean isAvailable(Term term) {
		return isAvailable(term.getSemester(), term.getYear());
	}

	/**
	 * This method performs 'soft' check: if course is not available in
	 * specified term, then check general availability.
	 * 
	 * @param term
	 * @param location
	 * @return
	 */
	public Boolean isAvailable(Term term, Location location) {
		if (location == null)
			throw new IllegalArgumentException("Location is null.");

		Location availableLocation = getLocation(term.getSemester(),
				term.getYear());

		if (availableLocation == null) {
			availableLocation = getLocation(term.getSemester(), null);
			if (availableLocation == null)
				return false;
		}
		if ((location == Location.BOTH) || (availableLocation == Location.BOTH))
			return true;
		if ((availableLocation == Location.ONCAMPUS)
				|| (availableLocation == Location.ONLINE)) {
			if (availableLocation == location)
				return true;
			else
				return false;
		}
		throw new IllegalStateException("This should never be reached.");
	}

	/**
	 * checks general course availability.
	 * 
	 * @param sem
	 * @param year
	 * @return
	 */
	public Boolean isAvailable(Semester sem) {
		return isAvailable(sem, null);
	}

	/**
	 * availability. this method checks if a particular course is available for
	 * a FIXED year and FIXED semester.. Example: Graphics is available in the
	 * fall for all years! so if you check availability for graphics course in
	 * fall 2009, it will return FALSE as this method only checks for FIXED
	 * year. However u can check if its available for all falls by using year ==
	 * null and it will return TRUE
	 * 
	 * @param sem
	 * @param year
	 * @return true if available and false if not available.
	 */
	public Boolean isAvailable(Semester sem, Integer year) {

		List<ECourseAvailability> list = Ebean.find(ECourseAvailability.class)
				.where().eq("semester", Semester.unwrap(sem)).eq("year", year)
				.eq("course", entity).findList();
		if (list.isEmpty()) {
			return false;
		}
		return true;
	}

	//

	/**
	 * this method sets courseavailabilty for a FIXED semester na FIXED year...
	 * also if the year is null implies that the course is available for all the
	 * FIXED semesters Also, you can change the availabliy for a particular year
	 * even if its availalbe for all years (year = null) Any doubts ask Me or
	 * Alex
	 * 
	 * Example: Graphics is available in the fall for all years ONCAMPUS but for
	 * year 2014 you want graphics to be available ONLINE, you can use
	 * setCourseAvailability(Semester.FALl, 2014 ,true , Location.ONLINE) to
	 * change graphics courseavailability.
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
			newEntry.setSemester(Semester.unwrap(sem));
			newEntry.save();
			this.entity.refresh();
		}

		else if (isAvailable(sem, year) && !isAvailable) {
			List<ECourseAvailability> list = Ebean
					.find(ECourseAvailability.class).where()
					.eq("semester", Semester.unwrap(sem)).eq("course", entity)
					.eq("year", year).findList();

			if (!list.isEmpty()) {
				list.get(0).delete(); // unique entry!
			}
			this.entity.refresh();
		}

		else {
			ECourseAvailability entry = null;
			List<ECourseAvailability> list = Ebean
					.find(ECourseAvailability.class).where()
					.eq("semester", Semester.unwrap(sem)).eq("year", year)
					.eq("course", entity).findList();

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
			entry.update();
			this.entity.refresh();
		}
		return;
	}

	public PreRequisite getPreRequisite() {
		return entity.getPrereq() == null ? null : PreRequisite.wrap(entity
				.getPrereq());
	}

	public CoRequisite getCoRequisite() {
		return entity.getCoreq() == null ? null : CoRequisite.wrap(entity
				.getCoreq());
	}

	@Override
	public int hashCode() {
		return entity.getId();
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Course) {
			Course anotherCourse = (Course) o;
			if (this.getId().equals(anotherCourse.getId()))
				return true;
			else
				return false;
		}
		return false;
	}

}