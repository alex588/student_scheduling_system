package test.model;

import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;
import model.ComplexCourseGroup;
import model.ComplexRequirement;
import model.CourseGroup;
import model.Junction;
import model.SimpleCourseGroup;
import model.SimpleRequirement;

import org.junit.Test;

public class TestCourseGroup {

	@Test
	public void create() {
		running(fakeApplication(), new Runnable() {
			public void run() {

				// Test to create a course
				SimpleCourseGroup scg1 = SimpleCourseGroup.create("scg1");
				SimpleCourseGroup scg2 = SimpleCourseGroup.create("scg2");
				ComplexCourseGroup ccg = ComplexCourseGroup.create("ccg",Junction.OR);
				
				ccg.addChildNode(scg1, true);
				ccg.addChildNode(scg2, true);
				
				// assertThat(prefix.equals(prefix)).isTrue();
				//c.setPrereq(prereq );
			}
		});
	}
}
