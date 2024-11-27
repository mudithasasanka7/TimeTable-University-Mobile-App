package com.example.timetableuni;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "timetable.db";
    private static final int DATABASE_VERSION = 1;

    // Table and column names
    private static final String TABLE_TIMETABLE = "timetable";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_DAY = "day";
    private static final String COLUMN_TIME = "time";
    private static final String COLUMN_SUBJECT = "subject";
    private static final String COLUMN_LECTURER = "lecturer";
    private static final String COLUMN_VENUE = "venue";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the timetable table
        String createTableQuery = "CREATE TABLE " + TABLE_TIMETABLE + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_DAY + " TEXT, " +
                COLUMN_TIME + " TEXT, " +
                COLUMN_SUBJECT + " TEXT, " +
                COLUMN_LECTURER + " TEXT, " +
                COLUMN_VENUE + " TEXT)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TIMETABLE);
        onCreate(db);
    }

    // Method to insert a new timetable entry into the database
    public long insertTimetable(ContentValues values) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.insert(TABLE_TIMETABLE, null, values);
    }

    // Method to get all timetable entries
    public Cursor getAllTimetables() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_TIMETABLE, null, null, null, null, null, null);
    }

    // Method to delete a timetable entry
    public int deleteTimetable(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COLUMN_ID + " = ?";
        String[] selectionArgs = { String.valueOf(id) };
        return db.delete(TABLE_TIMETABLE, selection, selectionArgs);
    }

    // Method to update a timetable entry
    public int updateTimetable(int id, ContentValues values) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COLUMN_ID + " = ?";
        String[] selectionArgs = { String.valueOf(id) };
        return db.update(TABLE_TIMETABLE, values, selection, selectionArgs);
    }
}
