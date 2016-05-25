package com.finalproject.kg.summary.model;

/**
 * Created by נירית וקובי on 25/05/2016.
 */
public class Course {
    private String CourseName;

    public Course()
    {

    }

    public Course(String courseName)
    {
        this.CourseName = courseName;
    }

    public String getCourseName()
    {
        return this.CourseName;
    }

    public void setCourseName(String courseName)
    {
        this.CourseName = courseName;
    }
}
