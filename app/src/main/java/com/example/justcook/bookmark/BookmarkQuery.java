package com.example.justcook.bookmark;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class BookmarkQuery {
    Context mContext = null;
    String TABLE_NAME = "bookmarkRcode";
    BookmarkDBHelper dbHelper;
    SQLiteDatabase bookDB;
    String TAG = "북마크쿼리";

    public BookmarkQuery(Context context){
        this.mContext = context;
    }
    //DB 생성 or 열기
    public void openDB(){
        try{
            dbHelper = new BookmarkDBHelper(this.mContext);
            bookDB = dbHelper.getWritableDatabase();
        }catch (Exception e){
            Log.e(TAG, "openDB 에러어ㅓㅓㅓ");
        }
    }


    //북마크에 저장
    public void insertBookmarkRcode(Integer rcode){
        openDB();
        String sql = "insert into "+TABLE_NAME+" (rcode) values ("
                + rcode +");";
        bookDB.execSQL(sql);
    }

    //북마크 삭제
    public void deleteBookmarkRcode(Integer r_num){
        openDB();
        String sql = "delete from "+TABLE_NAME+" where rcode = "
                + r_num ;

        bookDB.execSQL(sql);
    }


    //북마크 전부 조회
    public ArrayList<Integer> AllBookmarkData(){
        openDB();
        ArrayList<Integer> resultData = new ArrayList<Integer>();
        String[] token = null;
        String sql = "select * from " + TABLE_NAME;

        Cursor result = bookDB.rawQuery(sql, token);
        result.moveToFirst();
        int count  = result.getCount();
        Log.v(TAG, "북마크 모두 조회 개수 : "+count);

        while(! result.isAfterLast()){
            Integer rcode = result.getInt(1);

            resultData.add(rcode);
            result.moveToNext();
        }
        result.close();
        return resultData;
    }

    //북마크에 있는지 확인
    public boolean checkBookmarkData(int rcode){
        openDB();
        String sql = "select * from "+TABLE_NAME+" where rcode = " + rcode ;
        Cursor result = bookDB.rawQuery(sql, null);
        int count = result.getCount();

        if (count==0){return false;}
        else if(count == 1){return true;}
        else {return false;}

    }

}
