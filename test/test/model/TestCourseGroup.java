package test.model;

import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;
import java.util.List;
import model.ComplexCourseGroup;
import model.Course;
import model.CourseGroup;
import model.Junction;
import model.Prefix;
import model.SimpleCourseGroup;
import model.entities.ECourseGroup;
import org.junit.Assert;
import org.junit.Test;
import com.avaje.ebean.Ebean;

/**
 * 
 * @author Mihir Daptardar
 * 
 */
public class TestCourseGroup {

	@Test
	public void create() {
		running(fakeApplication(), new Runnable() {
			@Override
			public void run() {
				ComplexCourseGroup ccg = ComplexCourseGroup.create("ccg1",
						null, true, Junction.AND);
				SimpleCourseGroup scg1 = SimpleCourseGroup.create("scg1", null);
				SimpleCourseGroup scg2 = SimpleCourseGroup.create("scg2", null);

				Course c1 = Course.create(Prefix.getById(2), 501, "c1", 3, 500,
						null, null);
				Course c2 = Course.create(Prefix.getById(2), 502, "c2", 3, 500,
						null, null);
				Course c3 = Course.create(Prefix.getById(2), 503, "c3", 3, 500,
						null, null);
				Course c4 = Course.create(Prefix.getById(2), 504, "c4", 3, 500,
						null, null);

				scg1.addCourse(c1);
				scg1.addCourse(c2);
				scg1.addCourse(c3);

				scg2.addCourse(c2);
				scg2.addCourse(c1);
				scg2.addCourse(c4);

				ccg.addChildNode(scg1, false);
				ccg.addChildNode(scg2, false);

				List<Course> clist = ccg.getAllCourses();
				Assert.assertNotNull(clist);

				// deletion
				Course.delete(c1.getId());
				Course.delete(c2.getId());
				Course.delete(c3.getId());
				Course.delete(c4.getId());

				CourseGroup.delete(scg1);
				CourseGroup.delete(scg2);
				CourseGroup.delete(ccg);
			}

		});
	}

	public void removeChildren() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				ECourseGroup cg = Ebean.find(ECourseGroup.class).where()
						.eq("cg_title", "ccg").findList().get(0);

				ComplexCourseGroup ccg = (ComplexCourseGroup) CourseGroup
						.getById(cg.getId());
				ccg.removeChildNode(ccg);
				Course.delete(20);
			}

		});
	}
}
