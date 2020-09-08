package com.github.perscholas.service.studentservice;

import com.github.perscholas.model.Course;
import com.github.perscholas.model.CourseInterface;
import com.github.perscholas.model.Student;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
/**
 * @author leonhunter
 * @created 02/12/2020 - 8:47 PM
 */


public class TestConstructor {

        @Test
        public void courseConstructorTest() {
                Course course = new Course();
                Assert.assertTrue(course instanceof CourseInterface);
        }

        @Test
        public void studentConstructorTest() {
                Student student = new Student();
                Assert.assertTrue(student instanceof Student);
                Assert.assertNotNull(student.getStudentCourses());
        }
}
