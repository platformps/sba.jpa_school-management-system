package com.github.perscholas.model;

import javax.persistence.*;
import java.util.List;

// TODO - Annotate and Implement respective interface and define behaviors
@Entity
@Table(name = "Course")
public class Course implements CourseInterface{

    @Id
    @Column(name = "id")
    private Integer id;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "instructor")
    private String instructor;

    @ManyToMany(mappedBy = "registeredCourses")
    private List<Student> registeredStudents;

    //Null constructor
    public void Course(){

    }

    //Non-null constructor
    public void Course(Integer id, String name, String instructor){
        this.id = id;
        this.name = name;
        this.instructor = instructor;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getInstructor() {
        return instructor;
    }

    public List<Student> getRegisteredStudents() {
        return registeredStudents;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public void setRegisteredStudents(List<Student> registeredStudents) {
        this.registeredStudents = registeredStudents;
    }
}
