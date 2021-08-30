package com.example.justcook.bookmark;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class CameraDBHelper extends SQLiteOpenHelper {

    public static String DB_NAME = "CameraInputIngredients.db";
    public static int VERSION = 1;
    public static String TAG = "카메라DBHelper";

    public CameraDBHelper(Context context){
        super(context, DB_NAME, null, VERSION);
    }

    public void onCreate(SQLiteDatabase db){
        Log.v(TAG, "onCreate 호출");
        String sql = "create table if not exists ingredients("
                + "_id integer PRIMARY KEY autoincrement, "
                + "name text)";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.v(TAG, "onUpgrade 호출"+oldVersion+"->"+newVersion);
        if (newVersion > 1){
            db.execSQL("DROP TABLE IF EXISTS bookmarkRcode");
        }
    }
}
