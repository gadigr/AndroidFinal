package com.finalproject.kg.summary.model;

import java.util.List;

/**
 * Created by Kobi on 07/05/2016.
 */
public class Student {
    private String id;
    private String name;
    private String emailaddress;
    private String imageName;
    private List<Course> lstCourse;
    public String password;

    public Student(){

    }

    public Student(String id, String name, String emailaddress, String imageName, List<Course> lstCourse) {
        this.id = id;
        this.name = name;
        this.emailaddress = emailaddress;
        this.imageName = imageName;
        this.lstCourse = lstCourse;
    }

    public List<Course> getLstCourse()
    {
        return this.lstCourse;
    }

    public void setLstCourse(List<Course> lstCourse)
    {
        this.lstCourse = lstCourse;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmailaddress() {
        return emailaddress;
    }

    public String getImageName() {
        return imageName;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setId(String id) {
        this.id = id;
    }
    public void setEmailaddress(String emailaddress) {
        this.emailaddress = emailaddress;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
