package com.finalproject.kg.summary.model;

import java.util.Calendar;

/**
 * Created by Kobi on 20/05/2016.
 */
public class SummaryComment {
    private boolean Show;
    private String UserWriterId;
    private String Comment;
    private Calendar DateTime;

    public SummaryComment()
    {
    }

    public SummaryComment(boolean Show,String UserWriterId,String Comment,Calendar DateTime)
    {
        this.Show = Show;
        this.UserWriterId = UserWriterId;
        this.Comment = Comment;
        this.DateTime = DateTime;
    }


    public Calendar getDateTime() {
        return DateTime;
    }

    public void setDateTime(Calendar DateTime)
    {
        this.DateTime = DateTime;
    }

    public void setShow(boolean Show)
    {
        this.Show = Show;
    }

    public void setUserWriterId(String UserWriterId)
    {
        this.UserWriterId = UserWriterId;
    }

    public void setComment(String Comment)
    {
        this.Comment = Comment;
    }

    public boolean getShow()
    {
        return Show;
    }

    public String getUserWriterId()
    {
        return UserWriterId;
    }

    public String getComment()
    {
        return Comment;
    }
}
