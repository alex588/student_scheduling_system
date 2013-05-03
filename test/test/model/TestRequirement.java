package test.model;

import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;

import java.util.List;
import model.ComplexRequirement;
import model.Course;
import model.CourseGroup;
import model.Junction;
import model.SimpleRequirement;

import org.junit.Assert;
import org.junit.Test;

/**
 * 
 * @author Mihir Daptardar
 * 
 */

public class TestRequirement {
	@Test
	public void create() {
		running(fakeApplication(), new Runnable() {
			public void run() {

				// Test to create a course

				ComplexRequirement creq1 = ComplexRequirement.create("Creq1",
						null, true, Junction.OR);
				ComplexRequirement creq2 = ComplexRequirement.create("creq2",
						null, true, Junction.AND);
				SimpleRequirement sreq1 = SimpleRequirement.create("Sreq1",
						null, 2, CourseGroup.getAll().get(0));
				SimpleRequirement sreq2 = SimpleRequirement.create("Sreq2",
						null, 4, CourseGroup.getAll().get(1));
				SimpleRequirement sreq3 = SimpleRequirement.create("Sreq3",
						null, 2, CourseGroup.getAll().get(2));

				creq1.addChildNode(creq2, true);
				creq1.addChildNode(sreq3, false);

				creq2.addChildNode(sreq1, true);
				creq2.addChildNode(sreq2, true);

				List<Course> clist = creq1.getCourses();
				Assert.assertNotNull(clist);
				// this returns the list of courses
			}
		});
	}
}
