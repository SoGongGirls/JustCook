package com.example.justcook;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;


import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

public class Fragment3 extends Fragment {
    SQLiteDatabase database;
    public static final String TAG ="in Fragment3.java" ;
    /*0531*/
    //애샛파일을 디바이스에 복사하는 코드
    public static final String ROOT_DIR = "/data/data/com.example.justcook/";
    public static final String DATABASE_NAME = "recipe2.db";
    public static void initialize(Context ctx) {
        // check
        File folder = new File(ROOT_DIR + "databases");
        folder.mkdirs();
        File outfile = new File(ROOT_DIR + "databases/" + DATABASE_NAME);
        if (outfile.length() <= 0) {
            AssetManager assetManager = ctx.getResources().getAssets();
            try {
                InputStream is = assetManager.open(DATABASE_NAME, AssetManager.ACCESS_BUFFER);
                long filesize = is.available();
                byte [] tempdata = new byte[(int)filesize];
                is.read(tempdata);
                is.close();

                outfile.createNewFile();
                FileOutputStream fo = new FileOutputStream(outfile);
                fo.write(tempdata);
                fo.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /*0531*/

    /*<-- 추가된 부분-->*/
//    public List<recipe_info> infoList; //DB 를 리스트로 받을 것
//
//    private void initLoadDB(){
//        Log.v(TAG, "initLoadDB 실행");
//        DataAdapter mDbHelper = new DataAdapter(getActivity().getApplicationContext());
//        mDbHelper.createDatabase();
//        mDbHelper.open();
//        mDbHelper.open();
//        //db에 있는 값을 model을 적용하여 넣기
//        infoList = mDbHelper.getTableData();
//        //db닫기
//        mDbHelper.close();
//    }

    /*<--list에 저장한것 사용-->*/

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_3, container, false);
        // 초기화, 참조 및 생성
        final ListView R_ListView = (ListView) view.findViewById(R.id.R_ListView);
        EditText r_search = (EditText) view.findViewById(R.id.r_search);
        //DBopen
        openDatabase("recipe2.db");

        RecipeAdapter adapter = new RecipeAdapter();
        // 리스트뷰 참조 및 Adapter 연결
        R_ListView.setAdapter(adapter);
          // 리스트 아이템 추가
//        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.test_food),
////                "토마토 달걀 볶음", ContextCompat.getDrawable(getActivity(), R.drawable.bookmark_none));
        //데이터 select
        if(database != null){
            String sql = "select name, foodtypename from " + "recipe_info_ver4";
            Cursor cursor = database.rawQuery(sql, null);

            for (int i=0; i < cursor.getCount(); i++){
                cursor.moveToNext();
                String name = cursor.getString(0);
                //String foodtypename = cursor.getString(1);
                adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.test_food),
                        name, ContextCompat.getDrawable(getActivity(), R.drawable.bookmark_none));
            }
        }
        R_ListView.setAdapter(adapter);

//        //initLoadDB();
//        //DataAdapter adapter = new DataAdapter(getActivity().getApplicationContext());
//        //R_ListView.setAdapter((ListAdapter) adapter);
//
//        //데이터 select
//        if(database != null){
//            String sql = "select name, foodtypename from " + "recipe_info_ver4"";
//            Cursor cursor = database.rawQuery(sql, null);
//
//            for (int i=0; i < cursor.getCount(); i++){
//                cursor.moveToNext();
//                String name = cursor.getString(0);
//                String foodtypename = cursor.getString(1);
//                adapter.addItem(new tempRecipeItem(name, foodtypename));
//            }
//        }
//        R_ListView.setAdapter(adapter);

        // EditText 변경 이벤트 처리
        r_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable edit) {
                String filterText = edit.toString() ;
//                ((RecipeAdapter)R_ListView.getAdapter()).getFilter().filter(filterText) ;
                //((tempRecipeAdapter)R_ListView.getAdapter()).getFilter().filter(filterText) ;
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

        });


        // 레시피 상세 보기
        R_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent i_recipe = new Intent(getActivity(), recipe.class);
                startActivity(i_recipe);
            }
        });

        return view;


    }//onCreateView

    /* 리스트뷰 크기 조절 메소드 */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    /*0531*/
    public void openDatabase(String dbName){
        Log.v(TAG, "openDatabase()호출");
        database = SQLiteDatabase.openOrCreateDatabase(dbName, null, null);
        if(database != null){
           Log.v(TAG, "데이터베이스 오픈됨.");
        }
    }

    public void selectData(String tableName){
        Log.v(TAG, "selectData호출.");
        if(database != null){
            String sql = "select name, foodtypename from " + tableName;
            Cursor cursor = database.rawQuery(sql, null);

            for (int i=0; i < cursor.getCount(); i++){
                cursor.moveToNext();
                String name = cursor.getString(0);
                String foodtypename = cursor.getString(1);
                //Log.v(TAG, name+", "+foodtypename+". ");
            }
        }
    }
    /*0531*/
}