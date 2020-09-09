package com.github.perscholas.service;
import com.github.perscholas.DatabaseConnection;
import com.github.perscholas.dao.StudentDao;
import com.github.perscholas.model.CourseInterface;
import com.github.perscholas.model.Student;
import com.github.perscholas.model.StudentInterface;
import org.springframework.stereotype.Service;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// TODO - Implement respective DAO interface
@Service
public class StudentService implements StudentDao {
    private DatabaseConnection dbc;

    public StudentService(DatabaseConnection dbc) {
        this.dbc = dbc;
    }

    public StudentService() {
        this(DatabaseConnection.school_sba);
    }

    @Override
    public List<StudentInterface> getAllStudents() {
        ResultSet resultSet = dbc.executeQuery("SELECT * FROM students");
        try {
            // TODO - Parse `List<StudentInterface>` from `resultSet`
            List<StudentInterface> students = new ArrayList<>();
            while (resultSet.next()) {
                Student student = new Student();
                student.setEmail(resultSet.getString("email"));
                student.setName(resultSet.getString("name"));
                student.setPassword(resultSet.getString("password"));
                students.add(student);
            }
            return students;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    @Override
    public List<StudentInterface> getStudentByEmail(String studentEmail) {
        ResultSet resultSet = dbc.executeQuery("SELECT email FROM students");
        List<StudentInterface> students = new ArrayList<>();
        while (true) {
            try {
                if (!resultSet.next()) break;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            Student student = new Student();
            try {
                student.setEmail(resultSet.getString("email"));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                student.setName(resultSet.getString("name"));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                student.setPassword(resultSet.getString("password"));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            students.add(student);
        }
        return students;
    }



    @Override
    public Boolean validateStudent(String studentEmail, String password) {
        List<StudentInterface> student = getStudentByEmail(studentEmail);
        if (student == null)
            return false;
        return password.equals(password);
    }

    @Override
    public void registerStudentToCourse(String studentEmail, int courseId) {

    }

    @Override
    public List<CourseInterface> getStudentCourses(String studentEmail) {
        return null;
    }

}




