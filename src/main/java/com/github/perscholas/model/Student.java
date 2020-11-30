package com.github.perscholas.model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

// TODO - Annotate and Implement respective interface and define behaviors
@Entity
@Table(name="Student")
public class Student implements StudentInterface {
    @Id
    @Column(name="email")
    private String email;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "password")
    private String password;

    ///ATTEMPT AT JPA TABLE JOIN
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "STUDENT_COURSE",
            joinColumns = { @JoinColumn(name = "student_email", referencedColumnName = "email") },
            inverseJoinColumns = { @JoinColumn(name = "course_id", referencedColumnName = "userid") })
    private List<Course> courseLists;
    //////////////////

    public Student () {

    }

    public Student (String email, String name, String password) {
    this.email = email;
    this.name = name;
    this.password = password;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPassword() {
        return password;
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

    @Override
    public String toString() {
        return getEmail() + ", " + getName() + ", " + getPassword();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student student = (Student) o;
        return Objects.equals(email, student.email) &&
                Objects.equals(name, student.name) &&
                Objects.equals(password, student.password) &&
                Objects.equals(courseLists, student.courseLists);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, name, password, courseLists);
    }
}
