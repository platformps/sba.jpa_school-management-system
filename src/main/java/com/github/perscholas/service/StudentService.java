package com.github.perscholas.service;

import com.github.perscholas.DatabaseConnection;
import com.github.perscholas.dao.StudentDao;
import com.github.perscholas.model.CourseInterface;
import com.github.perscholas.model.Student;
import com.github.perscholas.model.StudentInterface;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// TODO - Implement respective DAO interface
public class StudentService implements StudentDao {
    private final DatabaseConnection dc;

    public StudentService(DatabaseConnection dc) {
        this.dc = dc;
    }

    public StudentService() {
        this(DatabaseConnection.MANAGEMENT_SYSTEM);
    }

    @Override
    //This method will return all students available in Students table
    public List<StudentInterface> getAllStudents() {
        ResultSet resultSet = dc.executeQuery("SELECT * FROM student");
        try {
            List<StudentInterface> students = new ArrayList<>();
            while(resultSet.next()) {
                Student student = new Student();
                student.setEmail(resultSet.getString("email"));
                student.setName(resultSet.getString("name"));
                student.setPassword(resultSet.getString("password"));
                students.add(student);
            }
            return students;
        } catch(Exception e) {
            throw new Error(e);
        }
    }

    @Override
    //This method will return all students available in Students table using email id
    public StudentInterface getStudentByEmail(String studentEmail) {

        return getAllStudents().stream()
                .filter(student -> student.getEmail().equals(studentEmail))
                .findFirst()
                .get();
    }

    @Override
    //This method will validate student using email and password
    public Boolean validateStudent(String studentEmail, String password) {

        return getAllStudents().stream()
                .filter(student -> student.getEmail().equals(studentEmail) && student.getPassword().equals(password))
                .count() == 1 ? true : false;
    }

    @Override
    //This method will register the course to Student
    public void registerStudentToCourse(String studentEmail, int courseId) {

        List<CourseInterface> studentCourses = getStudentCourses(studentEmail);
        Optional<CourseInterface> course = studentCourses.stream()
                .filter(crs -> crs.getId() == courseId)
                .findFirst();
        if(course.isPresent()) {
            course.ifPresent(crs -> System.out.println("Already registered to this Course!!"));
            return;
        }
        String query = "INSERT INTO Student_Course values (?, ?)";
        try {
            PreparedStatement preparedStatement = dc.getDatabaseConnection().prepareStatement(query);
            preparedStatement.setString(1, studentEmail);
            preparedStatement.setInt(2, courseId);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    //This method will return all courses mapped to Student
    public List<CourseInterface> getStudentCourses(String studentEmail) {

        List<CourseInterface> studentCourses = new ArrayList<>();
        String query = "SELECT * FROM Student_Course WHERE Student_email=?";
        try {
            PreparedStatement preparedStatement = dc.getDatabaseConnection().prepareStatement(query);
            preparedStatement.setString(1, studentEmail);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                int courseId = resultSet.getInt("Course_id");
                studentCourses.add(new CourseService().getAllCourses()
                        .stream()
                        .filter(course -> course.getId() == courseId)
                        .findFirst()
                        .get());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return studentCourses;
    }
}
