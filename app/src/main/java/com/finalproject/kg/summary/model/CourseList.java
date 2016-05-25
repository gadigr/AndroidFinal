package com.finalproject.kg.summary.model;

/**
 * Created by Kobi on 25/05/2016.
 */
public class CourseList
{
        private String CourseName;
        private boolean bSelect;

        public CourseList()
        {

        }

        public CourseList(String courseName,boolean bSelect)
        {
            this.CourseName = courseName;
            this.bSelect = bSelect;
        }

    public boolean getSelected()
    {
        return this.bSelect;
    }

        public String getCourseName()
        {
            return this.CourseName;
        }

        public void setCourseName(String courseName)
        {
            this.CourseName = courseName;
        }

    public void setSelected(boolean bSelect)
    {
        this.bSelect = bSelect;
    }
}
