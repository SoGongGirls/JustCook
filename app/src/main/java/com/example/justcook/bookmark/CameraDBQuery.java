package com.example.justcook.bookmark;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

public class CameraDBQuery {

    Context mContext = null;
    String TABLE_NAME = "ingredients";
    CameraDBHelper dbHelper;
    SQLiteDatabase DB;
    String TAG = "북마크쿼리";

    public CameraDBQuery(Context context){
        this.mContext = context;
    }
    //DB 생성 or 열기
    public void openDB(){
        try{
            dbHelper = new CameraDBHelper(this.mContext);
            DB = dbHelper.getWritableDatabase();
        }catch (Exception e){
            Log.e(TAG, "CameraDB 오픈 에러");
        }
    }


    //북마크에 저장
    public void insertCmrDB(String name){
        openDB();
        String sql = "insert into "+TABLE_NAME+" (name) values (\""
                + name +"\");";
        DB.execSQL(sql);
    }

    //북마크 삭제
    public void deleteCmrDB(String name){
        openDB();
        String sql = "delete from "+TABLE_NAME+" where name = \""
                + name +"\"" ;

        DB.execSQL(sql);
    }


    //북마크 전부 조회
    public ArrayList<String> AllCmrDBData(){
        openDB();
        ArrayList<String> resultData = new ArrayList<String>();
        String[] token = null;
        String sql = "select * from " + TABLE_NAME;

        Cursor result = DB.rawQuery(sql, token);
        result.moveToFirst();
        int count  = result.getCount();
        Log.v(TAG, "북마크 모두 조회 개수 : "+count);

        while(! result.isAfterLast()){
            String name = result.getString(1);

            resultData.add(name);
            result.moveToNext();
        }
        result.close();
        return resultData;
    }

    //북마크에 있는지 확인
    public boolean checkCmrDBkData(String name){
        openDB();
        String sql = "select * from "+TABLE_NAME+" where name=\"" + name+"\"" ;
        Log.e(TAG, sql);
        Cursor result = DB.rawQuery(sql, null);
        int count = result.getCount();

        if (count==0){return false;}
        else if(count == 1){return true;}
        else {return false;}

    }
}
