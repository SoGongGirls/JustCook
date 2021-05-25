package com.example.justcook;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.provider.ContactsContract;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

/*애샛DB를 연결*/
public class DataBaseHelper extends SQLiteOpenHelper {

    private static String TAG = "DataBaseHelper 실행";
    private static String DB_PATH = ""; //assets폴더 :""
    private static String DB_NAME = "recipe2.db";

    private SQLiteDatabase mDatabase;
    private final Context mContext;

    public DataBaseHelper(Context context){
        super(context, DB_NAME, null, 1);
        if(Build.VERSION.SDK_INT >= 17){
            DB_PATH = context.getApplicationInfo().dataDir + "/database/";
        }else{
            DB_PATH = "/data/data/" + context.getPackageName() + "/database/";
        }
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void createDataBase() throws IOException{
        //DB가 없으면 assets폴더에서 복사 과정
        boolean mDataBaseExist = checkDataBase();
        if(!mDataBaseExist){
            this.getReadableDatabase();
            this.close();
            try{
                //애샛폴더에서 복사
                copyDataBase();
                Log.e(TAG, "createDatabase실행 : DB가 생성되었음.");
            }catch(IOException mIOException){
                throw new Error("createDatabase실행 : DB복사중 에러발생");
            }
        }
    }

    private boolean checkDataBase(){
        // /data/data/패키지이름/databases/DB이름  <- 경로에 DB존재 확인용
        File dbFile = new File(DB_PATH + DB_NAME);
        return dbFile.exists();
   }

   private void copyDataBase() throws IOException{
        //찐 복사함수
       InputStream mInput = mContext.getAssets().open(DB_NAME);
       String outFileName = DB_PATH + DB_NAME;
       OutputStream mOutput = new FileOutputStream(outFileName);
       byte[] mBuffer = new byte[1024];
       int mLength;
       while ((mLength = mInput.read(mBuffer))>0){
           mOutput.write(mBuffer, 0, mLength);
       }
       mOutput.flush();
       mOutput.close();
       mInput.close();
   }

   public boolean openDataBase() throws SQLException{
        //DB열어서 쿼리 사용
        String mPath = DB_PATH + DB_NAME;
        mDatabase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        return mDatabase != null;
   }

   @Override
    public synchronized void close(){
        if(mDatabase != null)
            mDatabase.close();
        super.close();
   }
}
