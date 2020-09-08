package com.github.perscholas.service;

import com.github.perscholas.DatabaseConnection;
import com.github.perscholas.dao.CourseDao;
import com.github.perscholas.model.Course;
import com.github.perscholas.model.CourseInterface;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// TODO - Implement respective DAO interface
public class CourseService implements CourseDao {
    private DatabaseConnection dc;

    public CourseService(DatabaseConnection dc) {
        this.dc = dc;
    }

    public CourseService() {
        this(DatabaseConnection.MANAGEMENT_SYSTEM);
    }

    @Override
    //This method will return all the courses available in course table
    public List<CourseInterface> getAllCourses() {
        List<CourseInterface> courses = new ArrayList<>();
        try {
            String query = "SELECT * FROM course";
            ResultSet resultSet = dc.executeQuery(query);

            while(resultSet.next()) {
                Course course = new Course();
                course.setId(resultSet.getInt("id"));
                course.setName(resultSet.getString("name"));
                course.setInstructor(resultSet.getString("instructor"));
                courses.add(course);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return courses;
    }
}
