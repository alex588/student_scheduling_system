package test.model;

import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;
import model.CoRequisite;
import model.ComplexRequirement;
import model.Course;
import model.CourseGroup;
import model.Junction;
import model.Prefix;
import model.Requirement;
import model.Requisite;
import model.SimpleRequirement;

import org.junit.Test;

public class TestRequirement {
	@Test
	public void create() {
		running(fakeApplication(), new Runnable() {
			public void run() {

				// Test to create a course

				ComplexRequirement req1 = ComplexRequirement.create("Creq1", true, Junction.AND);
				SimpleRequirement req2 = SimpleRequirement.create("Sreq1", 2, CourseGroup.getAll().get(0));
				SimpleRequirement req3 = SimpleRequirement.create("Sreq2", 4, CourseGroup.getAll().get(1));
				
				req1.addChildNode(req2, true);
				req1.addChildNode(req3, false);
				
				
				// assertThat(prefix.equals(prefix)).isTrue();
				//c.setPrereq(prereq );
			}
		});
	}
}
