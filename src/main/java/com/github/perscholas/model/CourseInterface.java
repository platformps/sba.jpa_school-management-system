package com.github.perscholas.model;

public interface CourseInterface extends EntityInterface<Integer> {
    Integer getId();
    String getName();
    String getInstructor();

    void setId(Integer id);
    void setName(String name);
    void setInstructor(String instructor);
}
