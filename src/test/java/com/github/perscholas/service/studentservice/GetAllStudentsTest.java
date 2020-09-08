package com.github.perscholas.service.studentservice;

import com.github.perscholas.JdbcConfigurator;
import com.github.perscholas.dao.StudentDao;
import com.github.perscholas.model.StudentInterface;
import com.github.perscholas.service.StudentService;
import com.github.perscholas.utils.DirectoryReference;
import com.github.perscholas.DatabaseConnection;
import com.github.perscholas.model.Student;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import java.io.File;
import java.util.List;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * @author leonhunter
 * @created 02/12/2020 - 8:22 PM
 */
public class GetAllStudentsTest {
    @Before
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
        JdbcConfigurator.initialize();
        StudentDao service = (StudentDao) new StudentService();

        // when
        List<StudentInterface> expectedstudentList = service.getAllStudents();

        // then
        ResultSet resultSet = DatabaseConnection.MANAGEMENT_SYSTEM.executeQuery("SELECT * FROM student");
        List<StudentInterface> actualStudents = new ArrayList<>();
        try {
            while(resultSet.next()) {
                Student student = new Student();
                student.setEmail(resultSet.getString("email"));
                student.setName(resultSet.getString("name"));
                student.setPassword(resultSet.getString("password"));
                actualStudents.add(student);
            }
        } catch(Exception ex) {
            throw new Error(ex);
        }
        Assert.assertArrayEquals(expectedstudentList.toArray(), actualStudents.toArray());
    }
}
