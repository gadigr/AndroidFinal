package com.example.nofit.summary.model;

/**
 * Created by Kobi on 07/05/2016.
 */
public class Student {
    String id;
    String name;
    String emailaddress;
    String imageName;
    public String password;

    public Student(){

    }

    public Student(String id, String name, String emailaddress, String imageName) {
        this.id = id;
        this.name = name;
        this.emailaddress = emailaddress;
        this.imageName = imageName;
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
