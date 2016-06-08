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

    public SummaryLike(String UserId,boolean Like)
    {
        this.UserId = UserId;
        this.Like = Like;
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
