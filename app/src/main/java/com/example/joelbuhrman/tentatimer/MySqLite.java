package com.example.joelbuhrman.tentatimer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by mliljenberg on 09/04/16.
 */
public class MySqLite extends SQLiteOpenHelper {
    private boolean putAllInProgress = false;
    private SQLiteDatabase putAllDatabase;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "times";
    private static final String CREATE_TIMES_TABLE = "create table Times(\n" +
            "course varchar(300) not null,\n" +
            "time float not null,\n" +
            "primary key(course)\n" +
            ");";

    private static final String TABLE_COURSETIME = "COURSETIMES";
    private static final String KEY_COURSES = "courses";
    private static final String VAR_TIMES = "times";
    private static final String[] TIMES_COLUMNS = {KEY_COURSES,VAR_TIMES };

    public MySqLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Skapar databasen
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TIMES_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Locations");
        this.onCreate(db);
    }

    /**
     * Locations_Locations
     **/


    public synchronized Boolean addTime(MyRegisteredTime time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_COURSES, time.getCourse());
        values.put(VAR_TIMES, time.getTime());
        Boolean inserted = db.insert(TABLE_COURSETIME, null, values) > 0;

        return inserted;
    }

    public synchronized MyRegisteredTime getLocation(String course) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(
                TABLE_COURSETIME,             //table
                TIMES_COLUMNS,            //column names
                KEY_COURSES + " = ?",    //selections
                new String[]{course}, //selections args
                null,                      //group by
                null,                      //having
                null,                      //order by
                null);                     //limit

        if (cursor != null) {
            cursor.moveToFirst();
        }
        MyRegisteredTime location = new MyRegisteredTime(cursor.getString(0), (long)Integer.parseInt(cursor.getString(1)));
        db.close();
        cursor.close();

        return location;
    }



    public ArrayList<Cursor> getData(String Query){
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String[] columns = new String[] { "mesage" };
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2= new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);


        try{
            String maxQuery = Query ;
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(maxQuery, null);


            //add value to cursor2
            Cursor2.addRow(new Object[] { "Success" });

            alc.set(1,Cursor2);
            if (null != c && c.getCount() > 0) {


                alc.set(0,c);
                c.moveToFirst();

                return alc ;
            }
            return alc;
        } catch(SQLException sqlEx){
            Log.d("printing exception", sqlEx.getMessage());
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+sqlEx.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        } catch(Exception ex){

            Log.d("printing exception", ex.getMessage());

            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+ex.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        }


    }

}