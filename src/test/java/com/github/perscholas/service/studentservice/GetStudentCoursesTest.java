package com.github.perscholas.service.studentservice;

import com.github.perscholas.JdbcConfigurator;
import com.github.perscholas.dao.StudentDao;
import com.github.perscholas.model.CourseInterface;
import com.github.perscholas.model.Student;
import com.github.perscholas.model.StudentInterface;
import com.github.perscholas.service.StudentService;
import com.github.perscholas.utils.DirectoryReference;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.stream.Collectors;

/**
 * @author leonhunter
 * @created 02/12/2020 - 8:25 PM
 */
public class GetStudentCoursesTest {
    @Before // TODO (OPTIONAL) - Use files to execute SQL commands
    public void setup() {
        DirectoryReference directoryReference = DirectoryReference.RESOURCE_DIRECTORY;
        File coursesSchemaFile = directoryReference.getFileFromDirectory("courses.create-table.sql");
        File studentsSchemaFile = directoryReference.getFileFromDirectory("students.create-table.sql");
        File coursesPopulatorFile = directoryReference.getFileFromDirectory("courses.populate-table.sql");
        File studentsPopulatorFile = directoryReference.getFileFromDirectory("students.populate-table.sql");
        File[] filesToExecute = new File[]{
                coursesSchemaFile,
                studentsSchemaFile,
                coursesPopulatorFile,
                studentsPopulatorFile
        };
    }

    // given
    @Test
    public void test() {
        //JdbcConfigurator.initialize();
        StudentDao studentDao = new StudentService();
        String expectedCourseName = "Anatomy";
        String studentEmail = "aiannitti7@is.gd";
        int courseId = 3;

        // when
        studentDao.registerStudentToCourse(studentEmail, courseId);
        String actualCourseName = studentDao.getStudentCourses(studentEmail)
                .stream()
                .map(CourseInterface::getName)
                .collect(Collectors.joining());

        // then
        Assert.assertEquals(expectedCourseName, actualCourseName);
    }
}
