package com.example.justcook;

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

public class FavoriteQuery {
    Context mContext = null;
    ProgressDialog progressDialog;

    static String TABLE_NAME = "BookmarkRcode";
    //Boolean aconTable = true;

    static String DBFile = Environment.getExternalStorageDirectory().getPath()+"/"+"myBookmarkDB";
    static SQLiteDatabase bookDB;

    public FavoriteQuery(Context context){this.mContext = context;};

    //DB 생성 or 열기
    public static void openDB(String DbFile){
        try{
            //BookDBHelper helper = new BookDBHelper(null, 1);
            //bookDB = helper.getWritableDatabase();  //데이터베이스에 쓸수 있는 권한을 리턴해줌(갖게됨)

            bookDB = SQLiteDatabase.openOrCreateDatabase("Bookmark.db", null, null);
            //bookDB = SQLiteDatabase.openDatabase(DbFile, null, SQLiteDatabase.OPEN_READWRITE + SQLiteDatabase.CREATE_IF_NECESSARY);
        }catch (Exception e){
            Log.e("북마크 openDB", "에러어ㅓㅓㅓ");
        }
    }

    //데이터베이스 연결 닫기
    public static void closeDB(){
        try {
            bookDB.close();
        }catch(Exception e){}
    }

    //북마크에 저장
    public static void insertBookmarkRcode(Integer rcode){
        openDB(DBFile);
        Integer[] tokens = {null, rcode};
        String sql = "insert into "+TABLE_NAME+" (num, rcode)";
        sql += " values (?, ?);";

        if(tokens != null){bookDB.execSQL(sql, tokens);}
        closeDB();
    }

    //북마크 삭제
    public static void deleteBookmarkRcode(Integer r_num){
        openDB(DBFile);
        Integer[] tokens = {null, r_num};

        String sql = "delete from "+TABLE_NAME+" ";
        sql += "where num = ? and rcode = ?";

        if(tokens != null){bookDB.execSQL(sql, tokens);}
        closeDB();
    }

    //Table 만들기
    public void createTable(){
        openDB(DBFile);
        try{
            bookDB.execSQL("CREATE TABLE IF NOT EXISTS aconfaorite(num INT, rcode INT);");
        }catch(SQLException e){ }
    }

    //북마크 전부 조회
    public static ArrayList<Integer> AllBookmarkData(){
        openDB(DBFile);
        ArrayList<Integer> resultData = new ArrayList<Integer>();
        String[] token = null;
        String sql = "select * from " + TABLE_NAME;

        Cursor result = bookDB.rawQuery(sql, token);
        result.moveToFirst();
        int count  = result.getCount();

        while(! result.isAfterLast()){
            Integer rcode = result.getInt(1);

            resultData.add(rcode);
            result.moveToNext();
        }
        closeDB();
        return resultData;
    }

    static class BookDBHelper extends SQLiteOpenHelper{
        static String DATABASE_NAME = "bookmark.db";

        public BookDBHelper(Context context, int version){
            super(context, DATABASE_NAME, null, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db){
            db.execSQL("CREATE TABLE "+TABLE_NAME+" (num int, rcode int)");
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
            onCreate(db);
        }
    }
}
