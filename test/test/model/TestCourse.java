package test.model;

import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;
import model.*;
import model.Course.Location;

/**
 * 
 * @author Mihir Daptardar
 * 
 */

public class TestCourse {

	@Test
	public void create() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				// Test to create a course
				Course cmain = Course.create(Prefix.getById(1), 303,
						"TestCourse", 3, 300, null, null);
				Assert.assertNotNull(cmain);
				Course cprereq1 = Course.create(Prefix.getById(1), 302,
						"TestCo1", 3, 300, null, null);
				Assert.assertNotNull(cprereq1);
				Course cprereq2 = Course.create(Prefix.getById(1), 299,
						"TestCo2", 3, 200, null, null);
				Assert.assertNotNull(cprereq2);
				CoRequisite proot = CoRequisite.createNode(true, null,
						Junction.OR);
				Assert.assertNotNull(proot);
				cmain.setCoreq(proot);
				CoRequisite.createLeaf(true, proot, cprereq1);
				CoRequisite.createLeaf(true, proot, cprereq2);
			}
		});
	}

	@Test
	public void testComplexRrereqFormula() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				// Example:
				// courseWithPrereq has the following prerequisite: ((courseA or
				// courseB) and courseC)

				Course courseA = Course.create(Prefix.getById(1), 302,
						"courseA", 3, 300, null, null);
				Assert.assertNotNull(courseA);
				Course courseB = Course.create(Prefix.getById(1), 299,
						"courseB", 3, 200, null, null);
				Assert.assertNotNull(courseB);
				Course courseC = Course.create(Prefix.getById(1), 280,
						"courseC", 3, 200, null, null);
				Assert.assertNotNull(courseC);

				PreRequisite rootOfTheFormula = PreRequisite.createNode(true,
						null, Junction.AND);
				PreRequisite rootOfAorBsubFormula = PreRequisite.createNode(
						true, rootOfTheFormula, Junction.OR);
				PreRequisite.createLeaf(true, rootOfTheFormula, courseC);
				PreRequisite.createLeaf(true, rootOfAorBsubFormula, courseA);
				PreRequisite.createLeaf(true, rootOfAorBsubFormula, courseB);

				Course courseWithPrereq = Course.create(Prefix.getById(1), 280,
						"courseC", 3, 200, rootOfTheFormula, null);
				Assert.assertNotNull(courseWithPrereq);
			}
		});
	}

	public void update() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				// Test to update

				List<Course> courseList = Course.getAll();
				Course c = courseList.get(2);
				c.update("newTest", 8);
			}
		});

	}

	public void delete() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				// Test to Delete
				Course.delete(20);
			}
		});

	}

	@Test
	public void getCourse() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				Course c = Course.getByPrefixAndNumber(Prefix.getById(3), 181);
				c.getTitle();
			}

		});

	}

	@Test
	public void checkisAvailable() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				Course courseb = Course.create(Prefix.getById(1), 309, "AI", 3,
						300, null, null);
				courseb.setCourseAvailability(Semester.FALL, null, true,
						Location.ONCAMPUS);
				courseb = Course.getById(20);
				courseb.setCourseAvailability(Semester.FALL, 2009, true,
						Location.ONLINE);
			}
		});
	}
}