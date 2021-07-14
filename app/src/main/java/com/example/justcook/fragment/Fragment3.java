package com.example.justcook.fragment;

import android.content.Intent;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.justcook.MySQLiteOpenHelper;
import com.example.justcook.R;
import com.example.justcook.SearchRealRcode;
import com.example.justcook.itemAdapter.RecipeAdapter;
import com.example.justcook.itemAdapter.RecipeItem;
import com.example.justcook.recipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

public class Fragment3 extends Fragment {
    SQLiteDatabase database;
    public static final String TAG ="태그 Fragment3.java" ;
    ListView R_ListView;
    RecipeAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_3, container, false);
        // 초기화, 참조 및 생성
        R_ListView = (ListView) view.findViewById(R.id.R_ListView);
        EditText r_search = (EditText) view.findViewById(R.id.r_search);
        Button btn_search = (Button) view.findViewById((R.id.btn_search));
        openDB();

        adapter = new RecipeAdapter(getActivity());
        // 리스트뷰 참조 및 Adapter 연결

        //맨처음 초기화 데이터 보여주기(select)
        if (database != null) {
            String tableName = "recipe_info";
            String query = "select name, foodtypename, rcode, image_url from "+tableName ;
            Cursor cursor = database.rawQuery(query, null);
            Log.v(TAG, "조회된 데이터 수 : " + cursor.getCount());

            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                String name = cursor.getString(0);
                String foodtypename = cursor.getString(1);
                int rcode = cursor.getInt(2);
                String img_url = cursor.getString(3);

                adapter.addItem(new RecipeItem(name, foodtypename, rcode, img_url ));
            }
            cursor.close();
        } else {
            Log.e(TAG, "selectData() db없음.");
        }
        R_ListView.setAdapter(adapter);

        //btn_search(검색버튼 클릭)
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
//                String opt = r_search.getText().toString();
//                ArrayList<String>optList= new ArrayList<String>(Arrays.asList(opt.split(" ")));
//                searchData(optList);
            }
        });

        // EditText 변경 이벤트 처리
        r_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable edit) {
                //입력이 끝났을 때 처리하는 부분
                String filterText = edit.toString() ;
                ((RecipeAdapter)R_ListView.getAdapter()).getFilter().filter(filterText) ;
                //((tempRecipeAdapter)R_ListView.getAdapter()).getFilter().filter(filterText) ;
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //입력하여 변화가 생기기전
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //변화와 동시에 처리
            }

        });

        // 레시피 상세 보기
        R_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent i_recipe = new Intent(getActivity(), recipe.class);
                RecipeItem item = (RecipeItem) adapter.getItem(i);
                String rcode = String.valueOf(item.getRcode());
                Log.v(TAG, "rcode는" + rcode);
                i_recipe.putExtra("rcode", rcode);
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

    public void openDB() {

        Log.v(TAG, "openDB()실행.");
        MySQLiteOpenHelper helper = new MySQLiteOpenHelper(getContext());
        database = helper.getWritableDatabase();

        if (database != null) {
            Log.v(TAG, "DB열기 성공!");
        } else {
            Log.e(TAG, "DB열기 실패!");
        }
    }

    public void searchData(List optList){
        Log.v(TAG, "searchData() 호출됨.");

        adapter.removeItemAll();

        if (database != null) {
            ArrayList<ArrayList> curList = new ArrayList<ArrayList>(); //[토마토rcode리스트, 계란rcode리스트]
            String IngredientTable= "recipe_ingredient";

            //재료별 rcode 저장
            for(int i=0; i<optList.size(); i++){
                String query = "select rcode from "+IngredientTable+" where search_name=\""+optList.get(i)+"\"";
                ArrayList<Integer> rcode = new ArrayList<Integer>();
                try {
                    Cursor cursor = database.rawQuery(query, null);
                    Log.v(TAG, "조회된 데이터 수 : " + cursor.getCount());
                    cursor.moveToFirst();
                    int j = 0;
                    if (cursor.getCount() > 0) {
                        for (j = 0; j < cursor.getCount(); j++) {
                            rcode.add(cursor.getInt(0));
                            cursor.moveToNext();
                        }
                        curList.add(rcode);
                        cursor.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("SearchData", e.getMessage());
                }
            }

            //일치하는 rcode만 찾기
            Log.v(TAG, "일치하는 rcode 찾기");
            ArrayList<Integer> realRcode = new ArrayList<>();
            realRcode = curList.remove(0);
            realRcode = SearchRealRcode.searchVal(realRcode, curList);

            //real rcode 조회
            for(int k = 0; k < realRcode.size(); k++){
                //for(int k = 0; k < 14; k++){
                Log.v(TAG, String.valueOf(realRcode.size()));
                Log.v(TAG, "real rcode 조회");
                String q = "select name, foodtypename, rcode, image_url from recipe_info where rcode="+realRcode.get(k);
                //String q = "select name, foodtypename from recipe_info where rcode="+curList.get(0).get(k);
                Cursor cursor = database.rawQuery(q, null);
                Log.v(TAG, "조회된 데이터 수 : " + cursor.getCount());
                cursor.moveToNext();
                String name = cursor.getString(0);
                String foodtypename = cursor.getString(1);
                int rcode = cursor.getInt(2);
                String img_url = cursor.getString(3);
                adapter.addItem(new RecipeItem(name, foodtypename, rcode, img_url ));
                cursor.close();
            }
            adapter.notifyDataSetChanged();
            R_ListView.setAdapter(adapter);
            Log.v(TAG, "gridview 적용");

        } else {
            Log.e(TAG, "searchData() db없음.");
        }
        Log.v(TAG, "searchDATA종료");
    }


}