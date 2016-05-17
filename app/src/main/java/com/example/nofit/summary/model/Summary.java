package com.example.nofit.summary.model;




import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Kobi on 16/05/2016.
 */
public class Summary {
    String Id;
    String Name;
    String StudentId;
    String SummaryImage;
    Date DateTime;
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss aaa");

    public String getId() {
        return Id;
    }

    public Summary()
    {

        /*
        Id="1";
        Name = "Test";
        StudentId="null";
        SummaryImage="1.png";
        try{
            DateTime = dateFormat.parse("2016-01-01 00:00:00 pm");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }*/
    }

}
