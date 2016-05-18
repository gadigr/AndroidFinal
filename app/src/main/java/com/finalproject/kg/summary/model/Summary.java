package com.finalproject.kg.summary.model;




import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Kobi on 16/05/2016.
 */
public class Summary {
    private String Name;
    private String StudentId;
    private String SummaryImage;
    private Calendar DateTime;

    public String getName() {
        return Name;
    }

    public String getStudentId() {
        return StudentId;
    }

    public String getSummaryImage() {
        return SummaryImage;
    }

    public Calendar getDateTime() {
        return DateTime;
    }

    public void setName(String Name)
    {
        this.Name = Name;
    }

    public void setStudentId(String StudentId)
    {
        this.StudentId = StudentId;
    }

    public void setSummaryImage(String SummaryImage)
    {
        this.SummaryImage = SummaryImage;
    }

    public void setDateTime(Calendar DateTime)
    {
        this.DateTime = DateTime;
    }

    public Summary()
    {
    }

    public Summary(String Name, String StudentId, String SummaryImage, Calendar DateTime)
    {
        this.Name = Name;
        this.StudentId = StudentId;
        this.SummaryImage = SummaryImage;
        this.DateTime = DateTime;
    }

}
