package com.github.perscholas.service;

import com.github.perscholas.DatabaseConnection;
import com.github.perscholas.dao.StudentDao;
import com.github.perscholas.model.CourseInterface;
import com.github.perscholas.model.Student;
import com.github.perscholas.model.StudentInterface;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// TODO - Implement respective DAO interface
public class StudentService implements StudentDao {
    private final DatabaseConnection dbc;

    public StudentService(DatabaseConnection dbc) {
        this.dbc = dbc;
    }

    public StudentService() {
        this(DatabaseConnection.UAT);
    }

    @Override
    public List<StudentInterface> getAllStudents() {
        ResultSet resultSet = dbc.executeQuery("SELECT * FROM Student");
        List<StudentInterface> students = new ArrayList<>();
        try {
            while(resultSet.next()){
                Student student = new Student();
                student.setEmail(resultSet.getString("email"));
                student.setName(resultSet.getString("name"));
                student.setPassword(resultSet.getString("password"));
                students.add(student);
            }
            return students; // TODO - Parse `List<StudentInterface>` from `resultSet`
        } catch(Exception e) {
            throw new Error(e);
        }
    }

    @Override
    public StudentInterface getStudentByEmail(String studentEmail) {
        ResultSet resultSet = dbc.executeQuery("SELECT * FROM Student WHERE email = " + studentEmail);
        Student student = new Student();
        try{
            while(resultSet.next()){
                student.setEmail(resultSet.getString("email"));
                student.setName(resultSet.getString("name"));
                student.setPassword(resultSet.getString("password"));
            }
            return student;
        } catch (Exception e){
            throw new Error(e);
        }
    }

    @Override
    public Boolean validateStudent(String studentEmail, String password) {
        return getStudentByEmail(studentEmail).getPassword().equals(password);
    }

    @Override
    public void registerStudentToCourse(String studentEmail, int courseId) {

    }

    @Override
    public List<CourseInterface> getStudentCourses(String studentEmail) {
        return null;
    }
}
