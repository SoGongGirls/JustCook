package com.example.justcook.bookmark;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.os.strictmode.SqliteObjectLeakedViolation;
import android.util.Log;
import android.widget.TableRow;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class BookmarkDBHelper extends SQLiteOpenHelper {
    public static String DB_NAME = "bookmark.db";
    public static int VERSION = 1;
    public static String TAG = "북마크";

    public BookmarkDBHelper(Context context){
        super(context, DB_NAME, null, VERSION);
    }

    public void onCreate(SQLiteDatabase db){
        Log.v(TAG, "onCreate 호출");
        String sql = "create table if not exists bookmarkRcode("
                + "_id integer PRIMARY KEY autoincrement, "
                + "rcode integer)";

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
