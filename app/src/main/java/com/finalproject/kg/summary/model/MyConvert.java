package com.finalproject.kg.summary.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by hayya on 08/06/2016.
 */
public class MyConvert {

    private final static MyConvert instance = new MyConvert();

    public static MyConvert instance() {
        return instance;
    }

    public String ConvertLikeListToString(List<SummaryLike> lstLike)
    {
        String str = "";

        for (SummaryLike currSl : lstLike) {
            str = str + currSl.getUserId() + "#" + currSl.getLike() + "%";
        }

        return str;
    }

    public List<SummaryLike> ConvertStringToLikeList(String strLike)
    {
        List<SummaryLike> lstLike = new LinkedList<SummaryLike>();
        String[] arr = strLike.split("%");
        for (String currLike : arr)
        {
            String[] arr2 = currLike.split("#");
            SummaryLike sl = new SummaryLike(arr2[0],Boolean.valueOf(arr2[1]));
            lstLike.add(sl);
        }

        return lstLike;
    }

    public String ConvertCommentListToString(List<SummaryComment> LstComment)
    {
        String str = "";

        for (SummaryComment currSc : LstComment) {
            str = str + currSc.getShow() + "#" + currSc.getUserWriterId() + "#" + currSc.getComment() + "#" + currSc.getDateTime() + "%";
        }

        return str;
    }

    public List<SummaryComment> ConvertStringToCommentList(String strComment)
    {
        List<SummaryComment> LstComment = new LinkedList<SummaryComment>();
        String[] arr = strComment.split("%");
        for (String currComment : arr)
        {
            String[] arr2 = currComment.split("#");
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat  sdf = new SimpleDateFormat("HH:mm dd/MM/yyyy");
            try {
                cal.setTime(sdf.parse(arr2[3]));
            } catch (ParseException e) {
                e.printStackTrace();
            }


            SummaryComment sc = new SummaryComment(Boolean.valueOf(arr2[0]),arr2[1],arr2[2],cal);
            LstComment.add(sc);
        }

        return LstComment;
    }
}
