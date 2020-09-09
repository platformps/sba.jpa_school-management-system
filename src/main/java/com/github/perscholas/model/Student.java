package com.github.perscholas.model;

import javax.persistence.*;
import java.util.List;

// TODO - Annotate and Implement respective interface and define behaviors
@Entity
@Table(name = "Student")
public class Student implements StudentInterface{

    @Id
    @Column(name = "email")
    private String email;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "password")
    private String password;

    @ManyToMany
    private List<Course> registeredCourses;

    //Null constructor
    public void Student(){

    }

    //Non-null constructor
    public void Student(String email, String name, String password){
        this.email = email;
        this.name = name;
        this.password = password;
    }

    @Override
    public String getEmail() {
        return this.email;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    public List<Course> getRegisteredCourses() {
        return registeredCourses;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    public void setRegisteredCourses(List<Course> registeredCourses) {
        this.registeredCourses = registeredCourses;
    }
}
