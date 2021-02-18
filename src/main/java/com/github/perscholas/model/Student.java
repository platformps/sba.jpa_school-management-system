package com.github.perscholas.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// TODO - Annotate and Implement respective interface and define behaviors
@Entity
public class Student implements StudentInterface {

    private String email;
    private String name;
    private String password;

    private List<CourseInterface> studentCourses;

    public Student() {
        studentCourses = new ArrayList<>();
    }

    public Student(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    public List<CourseInterface> getStudentCourses() {
        return studentCourses;
    }

    public void setStudentCourses(List<CourseInterface> studentCourses) {
        this.studentCourses = studentCourses;
    }

    @Override
    public String toString() {
        return "Student{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", studentCourses=" + studentCourses +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(email, student.email) &&
                Objects.equals(name, student.name) &&
                Objects.equals(password, student.password);
    }
}

