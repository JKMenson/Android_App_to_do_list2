package com.example.mobilecomputingcat;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "tasks.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("CREATE TABLE tasks( taskId INT PRIMARY KEY, title TEXT, description TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("DROP TABLE IF EXISTS tasks");
        onCreate(DB);
    }

    public boolean insertUserData(String taskId, String title, String description) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("taskId", taskId);
        contentValues.put("title", title);
        contentValues.put("description", description);
        long result = DB.insert("tasks", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean updateUserData(String taskId, String title, String description) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("description", description);
        Cursor cursor = DB.rawQuery("SELECT * FROM tasks WHERE taskId = ?", new String[]{taskId});
        if (cursor.getCount() > 0) {
            long result = DB.update("tasks", contentValues, "taskId=?", new String[]{taskId});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public boolean deleteUserData(String taskId) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM tasks WHERE taskId= ?", new String[]{taskId});
        if (cursor.getCount() > 0) {
            long result = DB.delete("tasks", "taskId=?", new String[]{taskId});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public Cursor getdata() {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM tasks", null);
        return cursor;
    }
}

