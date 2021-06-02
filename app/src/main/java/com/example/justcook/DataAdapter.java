package com.example.justcook;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataAdapter {
    protected static final String TAG = "DataAdapter";
    // todo : 테이블 이름 명시
    protected static final String TABLE_NAME = "recipe_info_ver4";

    private final Context mContext;
    private SQLiteDatabase mDb;
    private DataBaseHelper mDbHelper;

    public DataAdapter(Context context) {
        this.mContext = context;
        mDbHelper = new DataBaseHelper(mContext);
    }

    public DataAdapter createDatabase() throws SQLException {
        try {
            mDbHelper.createDataBase();
        } catch (IOException mIOException) {
            Log.e(TAG, mIOException.toString() + " UnableToCreateDatabase");
            throw new Error("UnableToCreateDatabase");
        }
        return this;
    }

    public DataAdapter open() throws SQLException {
        try {
            mDbHelper.openDataBase();
            mDbHelper.close();
            mDb = mDbHelper.getReadableDatabase();
        } catch (SQLException mSQLException) {
            Log.e(TAG, "open >>" + mSQLException.toString());
            throw mSQLException;
        }
        return this;
    }

    public void close() {
        mDbHelper.close();
    }

    public List getTableData() {
        try {
            //Table 이름 -> antpool_bitcoin 불러오기???
            String sql = "SELECT * FROM " + TABLE_NAME;
            List infoList = new ArrayList();//모델을 넣을 리스트
            recipe_info r_info = null; // Todo :모델 선언?

            Cursor mCur = mDb.rawQuery(sql, null);
            if (mCur != null) {
                //칼럼 마지막까지
                while (mCur.moveToNext()) {
                    // Todo : 커스텀 모델 생성
                    r_info = new recipe_info();

                    // Todo : Record 기술
                    r_info.setRcode(mCur.getInt(0)); //음식번호
                    r_info.setName(mCur.getString(1)); // 계정명
                    r_info.setSummary(mCur.getString(2)); // 한줄설명?
                    r_info.setType(mCur.getInt(3)); // 한중일양식 필터 코드
                    r_info.setType_name(mCur.getString(4)); // 한식 퓨전 서양 등등
                    r_info.setFood_type(mCur.getInt(5)); // 부침 밥 과자 세부 필터 코드
                    r_info.setFood_type_name(mCur.getString(6)); // 부침 밥 과자 등등
                    r_info.setTime(mCur.getInt(7));//조리시간
                    r_info.setCalorie(mCur.getInt(8));
                    r_info.setPerson(mCur.getInt(9));//n인분의 n
                    r_info.setLevel(mCur.getString(10));//초보환영 보통 어려움
                    r_info.setBase(mCur.getString(11));// 곡류, 밀가류 어패류 이런거

                    //리스트에 넣기
                    infoList.add(r_info);
                }
            }
            return infoList;
        } catch (SQLException mSQLException) {
            Log.e(TAG, "getTestData >>" + mSQLException.toString());
            throw mSQLException;
        }
    }
}



