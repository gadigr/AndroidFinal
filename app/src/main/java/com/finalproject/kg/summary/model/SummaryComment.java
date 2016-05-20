package com.finalproject.kg.summary.model;

/**
 * Created by Kobi on 20/05/2016.
 */
public class SummaryComment {
    private String Id;
    private String UserWriterId;
    private String Comment;

    public SummaryComment()
    {
    }

    public void setId(String Id)
    {
        this.Id = Id;
    }

    public void setUserWriterId(String UserWriterId)
    {
        this.UserWriterId = UserWriterId;
    }

    public void setComment(String Comment)
    {
        this.Comment = Comment;
    }

    public String getId()
    {
        return Id;
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
