package com.github.perscholas.service;

import com.github.perscholas.DatabaseConnection;
import com.github.perscholas.dao.CourseDao;
import com.github.perscholas.model.CourseInterface;


import java.util.List;

// TODO - Implement respective DAO interface

public class CourseService implements CourseDao {
    private DatabaseConnection dbc;

    public DatabaseConnection getDbc() {
        return dbc;
    }

    @Override
    public List<CourseInterface> getAllCourses() {
        return null;
    }
}
