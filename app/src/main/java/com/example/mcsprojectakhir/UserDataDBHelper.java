package com.example.mcsprojectakhir;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.mcsprojectakhir.model.UserData;

import java.util.ArrayList;
import java.util.List;

public class UserDataDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "UserDataDB.db";
    public static final Integer DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "tbluserdata";
    public static final String TABLE_COLUMN_ID = "id";
    public static final String TABLE_COLUMN_NAME = "ud_name";
    public static final String TABLE_COLUMN_PASSWORD = "ud_password";

    List<UserData> UD = new ArrayList<>();

    public UserDataDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void insertUD(UserData new_UD) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

//        cv.put(TABLE_COLUMN_ID, new_UD.getUserId());
        cv.put(TABLE_COLUMN_NAME, new_UD.getUserName());
        cv.put(TABLE_COLUMN_PASSWORD, new_UD.getUserPassword());

        db.insert(TABLE_NAME, null, cv);
    }


    Cursor readAllUserData () {
        SQLiteDatabase db = this.getReadableDatabase();
        String UD_query = "SELECT * FROM " + TABLE_NAME;

        Cursor cursor = null;

//        if (db == null){
//            Toast.makeText((Context) cursor, "no data", Toast.LENGTH_SHORT).show();
//        }

        if (db != null) {
            cursor = db.rawQuery(UD_query, null);
        }

        return cursor;
    }


    public boolean updateUD (UserData update_UD) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(TABLE_COLUMN_ID, update_UD.getUserId());
        cv.put(TABLE_COLUMN_NAME, update_UD.getUserName());
        cv.put(TABLE_COLUMN_PASSWORD, update_UD.getUserPassword());

        db.update(TABLE_NAME, cv, "id = ?", new String [] { update_UD.getUserId() } );

        return true;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String create_table_query = "CREATE TABLE " + TABLE_NAME + " (" + TABLE_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TABLE_COLUMN_NAME + " TEXT NOT NULL, " + TABLE_COLUMN_PASSWORD + " TEXT NOT NULL)";

        db.execSQL(create_table_query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
