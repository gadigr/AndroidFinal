package com.finalproject.kg.summary.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by hayya on 08/06/2016.
 */
public class SummarySql {

    private static final String SUMMARY_TABLE = "summaries";
    private static final String SUMMARY_ID = "suid";
    private static final String SUMMARY_NAME = "name";
    private static final String SUMMARY_STUDENT_ID = "student_id";
    private static final String SUMMARY_IMAGE = "image";
    private static final String SUMMARY_DATETIME = "datetime";
    private static final String SUMMARY_COURSE = "course";
    private static final String SUMMARY_LIKE = "lstLike";
    private static final String SUMMARY_COMMENT = "lstComment";
    private static final String SUMMARY_LAST_UPDATE = "lateUpdate";


    public static void add(SQLiteDatabase db, Summary su) {
       ContentValues values = new ContentValues();
        values.put(SUMMARY_ID, su.getId());
        values.put(SUMMARY_NAME, su.getName());
        values.put(SUMMARY_STUDENT_ID, su.getStudentId());
        values.put(SUMMARY_IMAGE, su.getSummaryImage());

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd/MM/yyyy");

        values.put(SUMMARY_DATETIME, sdf.format(su.getDateTime().getTime()));
        values.put(SUMMARY_COURSE, su.getCourse());
        values.put(SUMMARY_LIKE, MyConvert.instance().ConvertLikeListToString(su.getLstLike()));
        values.put(SUMMARY_COMMENT, MyConvert.instance().ConvertCommentListToString(su.getLstComment()));
        values.put(SUMMARY_LAST_UPDATE, su.getLastUpdate());

        //String ss = su.getLstLike().toString();

        db.insertWithOnConflict(SUMMARY_TABLE, SUMMARY_ID, values,SQLiteDatabase.CONFLICT_REPLACE);
    }

    public static List<Summary> getAllSummaries(SQLiteDatabase db) {
        Cursor cursor = db.query(SUMMARY_TABLE, null, null, null, null, null, null);

        List<Summary> list = new LinkedList<Summary>();
        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(SUMMARY_ID);
            int nameIndex = cursor.getColumnIndex(SUMMARY_NAME);
            int studentIdIndex = cursor.getColumnIndex(SUMMARY_STUDENT_ID);
            int imageIndex = cursor.getColumnIndex(SUMMARY_IMAGE);
            int datetimeIndex = cursor.getColumnIndex(SUMMARY_DATETIME);
            int courseIndex = cursor.getColumnIndex(SUMMARY_COURSE);
            int likeIndex = cursor.getColumnIndex(SUMMARY_LIKE);
            int commentIndex = cursor.getColumnIndex(SUMMARY_COMMENT);
            int lastUpdateIndex = cursor.getColumnIndex(SUMMARY_LAST_UPDATE);
            do {
                String id = cursor.getString(idIndex);
                String name = cursor.getString(nameIndex);
                String studentId = cursor.getString(studentIdIndex);
                String image = cursor.getString(imageIndex);
                String datetime = cursor.getString(datetimeIndex);
                String course = cursor.getString(courseIndex);
                String like = cursor.getString(likeIndex);
                String comment = cursor.getString(commentIndex);
                String lastUpdate = cursor.getString(lastUpdateIndex);
                Calendar cal = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd/MM/yyyy");

                try {
                    cal.setTime(sdf.parse(datetime));
                } catch (ParseException e) {
                    e.printStackTrace();
                }



                //Date dd = new Date(lastUpdate);

                Summary su = new Summary(id, name, studentId, image, cal, course,MyConvert.instance().ConvertStringToLikeList(like),MyConvert.instance().ConvertStringToCommentList(comment), lastUpdate);
                list.add(su);
            } while (cursor.moveToNext());

        }

        return list;
    }

    public static void create(SQLiteDatabase db) {
        db.execSQL("create table " +
                SUMMARY_TABLE + " (" +
                SUMMARY_ID + " TEXT PRIMARY KEY," +
                SUMMARY_NAME + " TEXT," +
                SUMMARY_STUDENT_ID + " TEXT," +
                SUMMARY_IMAGE + " TEXT," +
                SUMMARY_DATETIME + " TEXT," +
                SUMMARY_COURSE + " TEXT," +
                SUMMARY_LIKE + " TEXT," +
                SUMMARY_COMMENT + " TEXT," +
                SUMMARY_LAST_UPDATE + " TEXT);"
        );
    }

    public static void drop(SQLiteDatabase db) {
        db.execSQL("drop table " + SUMMARY_TABLE);
    }

    public static String getLastUpdateDate(SQLiteDatabase db){
        return LastUpdateSql.getLastUpdate(db,SUMMARY_TABLE);
    }
    public static void setLastUpdateDate(SQLiteDatabase db, String date){
        LastUpdateSql.setLastUpdate(db,SUMMARY_TABLE, date);
    }
}
