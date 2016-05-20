package com.finalproject.kg.summary.model;

/**
 * Created by Kobi on 20/05/2016.
 */
public class SummaryLike {
    private String UserId;
    private boolean Like;

    public SummaryLike()
    {
    }

    public void setUserId(String UserId)
    {
        this.UserId = UserId;
    }

    public void setLike(boolean Like)
    {
        this.Like = Like;
    }

    public String getUserId()
    {
        return(this.UserId);
    }

    public boolean getLike()
    {
        return(this.Like);
    }
}
