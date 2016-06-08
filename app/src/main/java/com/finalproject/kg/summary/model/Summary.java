package com.finalproject.kg.summary.model;




import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Kobi on 16/05/2016.
 */
public class Summary {
    private String Id;
    private String Name;
    private String StudentId;
    private String SummaryImage;
    private Calendar DateTime;
    private String Course;
    private List<SummaryLike> lstLike;
    private List<SummaryComment> lstComment;
    private Date LastUpdate;

    public String getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public String getStudentId() {
        return StudentId;
    }

    public String getSummaryImage() {
        return SummaryImage;
    }

    public Date getLastUpdate() {
        return LastUpdate;
    }

    public Calendar getDateTime() {
        return DateTime;
    }

    public String getCourse() {
        return Course;
    }

    public List<SummaryLike> getLstLike() {
        return lstLike;
    }

    public List<SummaryComment> getLstComment() {
        return lstComment;
    }

    public void setName(String Name)
    {
        this.Name = Name;
    }


    public void setId(String Id)
    {
        this.Id = Id;
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

    public void setCourse(String Course)
    {
        this.Course = Course;
    }

    public void setLastUpdate(Date LastUpdate)
    {
        this.LastUpdate = LastUpdate;
    }

    public void setLstLike(List<SummaryLike> lstLike)
    {
        this.lstLike = lstLike;
    }

    public void setLstComment(List<SummaryComment> lstComment)
    {
        this.lstComment = lstComment;
    }

    public Summary()
    {
        Calendar cc = Calendar.getInstance();
        //LastUpdate = Date(Calendar.YEAR,Calendar.MONTH,Calendar.DAY_OF_MONTH,Calendar.HOUR,Calendar.MINUTE,Calendar.SECOND);
        LastUpdate = new Date();
    }

    public Summary(String Id,String Name, String StudentId, String SummaryImage, Calendar DateTime,String Course,List<SummaryLike> lstLike, List<SummaryComment> lstComment, Date LastUpdate)
    {
        this.Id = Id;
        this.Name = Name;
        this.StudentId = StudentId;
        this.SummaryImage = SummaryImage;
        this.DateTime = DateTime;
        this.Course = Course;
        this.lstLike = lstLike;
        this.lstComment = lstComment;
        this.LastUpdate = LastUpdate;
    }

}
