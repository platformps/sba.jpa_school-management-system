package com.github.perscholas.service;

import com.github.perscholas.DatabaseConnection;
import com.github.perscholas.dao.CourseDao;
import com.github.perscholas.model.Course;
import com.github.perscholas.model.CourseInterface;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseService implements CourseDao  {
    private DatabaseConnection dbc;

    public CourseService(DatabaseConnection dbc) {
        this.dbc = dbc;
    }

    public CourseService() {
        this(DatabaseConnection.MANAGEMENT_SYSTEM);
    }

    @Override
    public List<CourseInterface> getAllCourses() {
        List<CourseInterface> courses = new ArrayList<>();
        try {
            String query = "SELECT * FROM course";
            ResultSet resultSet = dbc.executeQuery(query);

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
