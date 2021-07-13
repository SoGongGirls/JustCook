package com.example.justcook;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.justcook.itemAdapter.InputIngredientsAdapter;
import com.example.justcook.itemAdapter.RecipeItem;

public class Input_Ingredients extends AppCompatActivity {
    String TAG = "Input_Ingredients 화면";
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_ingredients);

        View btn_back = findViewById(R.id.btn_back);
        TextView bar_title = findViewById(R.id.bar_title);
        EditText i_search = findViewById(R.id.i_search);
        bar_title.setText("재료 수동 입력");

        final ListView IgListView1;
        InputIngredientsAdapter adapter1;

        // Adapter 생성
        adapter1 = new InputIngredientsAdapter();

        // 리스트뷰 참조 및 Adapter 연결2
        IgListView1 = (ListView) findViewById(R.id.IgListView1);
        IgListView1.setAdapter(adapter1);

        Intent intent = getIntent();

        //db 오픈
        if (db == null){
            MySQLiteOpenHelper helper = new MySQLiteOpenHelper(getApplicationContext());
            db = helper.getWritableDatabase();
            if (db != null) {
                Log.v(TAG, "DB열기 성공!");
            } else {
                Log.e(TAG, "DB열기 실패!");
            }
        }
        //db 값 불러오기
        if (db != null) {
            String table1 = "unique_ingredient";
            String sql = "select name from "+table1;
            Cursor cursor = db.rawQuery(sql, null);
            Log.v(TAG, "조회된 데이터 수 : " + cursor.getCount());

            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                String name = cursor.getString(0);
                adapter1.addItem(
                        ContextCompat.getDrawable(this, R.drawable.appicon1),
                        name,
                        ContextCompat.getDrawable(this, R.drawable.add));
            }
            cursor.close();
        } else {
            Log.e(TAG, "selectData() db없음.");
        }

//        // 육류
//        adapter1.addItem(ContextCompat.getDrawable(this, R.drawable.beef_meat),"쇠고기", ContextCompat.getDrawable(this, R.drawable.add));
//        adapter1.addItem(ContextCompat.getDrawable(this, R.drawable.pork_meat),"돼지고기", ContextCompat.getDrawable(this, R.drawable.add));
//        adapter1.addItem(ContextCompat.getDrawable(this, R.drawable.chicken_meat),"닭고기", ContextCompat.getDrawable(this, R.drawable.add));
//
//        // 채소/과일
//        adapter1.addItem(ContextCompat.getDrawable(this, R.drawable.chili),"고추", ContextCompat.getDrawable(this, R.drawable.add));
//        adapter1.addItem(ContextCompat.getDrawable(this, R.drawable.carrot),"당근", ContextCompat.getDrawable(this, R.drawable.add));
//        adapter1.addItem(ContextCompat.getDrawable(this, R.drawable.leek),"대파", ContextCompat.getDrawable(this, R.drawable.add));
//        adapter1.addItem(ContextCompat.getDrawable(this, R.drawable.garlic),"마늘", ContextCompat.getDrawable(this, R.drawable.add));
//        adapter1.addItem(ContextCompat.getDrawable(this, R.drawable.apple),"사과", ContextCompat.getDrawable(this, R.drawable.add));
//        adapter1.addItem(ContextCompat.getDrawable(this, R.drawable.onion),"양파", ContextCompat.getDrawable(this, R.drawable.add));
//        adapter1.addItem(ContextCompat.getDrawable(this, R.drawable.tomato),"토마토", ContextCompat.getDrawable(this, R.drawable.add));
//
//        // 수산물
//        adapter1.addItem(ContextCompat.getDrawable(this, R.drawable.seaweed),"미역", ContextCompat.getDrawable(this, R.drawable.add));
//        adapter1.addItem(ContextCompat.getDrawable(this, R.drawable.shrimp),"새우", ContextCompat.getDrawable(this, R.drawable.add));
//        adapter1.addItem(ContextCompat.getDrawable(this, R.drawable.fish),"생선", ContextCompat.getDrawable(this, R.drawable.add));
//
//        // 가공/유제품
//        adapter1.addItem(ContextCompat.getDrawable(this, R.drawable.ramen),"라면", ContextCompat.getDrawable(this, R.drawable.add));
//        adapter1.addItem(ContextCompat.getDrawable(this, R.drawable.sausage),"소시지", ContextCompat.getDrawable(this, R.drawable.add));
//        adapter1.addItem(ContextCompat.getDrawable(this, R.drawable.fishcake),"어묵", ContextCompat.getDrawable(this, R.drawable.add));
//        adapter1.addItem(ContextCompat.getDrawable(this, R.drawable.egg),"달걀", ContextCompat.getDrawable(this, R.drawable.add));
//        adapter1.addItem(ContextCompat.getDrawable(this, R.drawable.plainbread),"식빵", ContextCompat.getDrawable(this, R.drawable.add));
//        adapter1.addItem(ContextCompat.getDrawable(this, R.drawable.butter),"버터", ContextCompat.getDrawable(this, R.drawable.add));
//        adapter1.addItem(ContextCompat.getDrawable(this, R.drawable.cheese),"치즈", ContextCompat.getDrawable(this, R.drawable.add));
//
//        // 곡류
//        adapter1.addItem(ContextCompat.getDrawable(this, R.drawable.rice),"밥", ContextCompat.getDrawable(this, R.drawable.add));
//        adapter1.addItem(ContextCompat.getDrawable(this, R.drawable.potato),"감자", ContextCompat.getDrawable(this, R.drawable.add));
//        adapter1.addItem(ContextCompat.getDrawable(this, R.drawable.sweetpotato),"고구마", ContextCompat.getDrawable(this, R.drawable.add));
//
//        // 조미료
//        adapter1.addItem(ContextCompat.getDrawable(this, R.drawable.salt),"소금", ContextCompat.getDrawable(this, R.drawable.add));
//        adapter1.addItem(ContextCompat.getDrawable(this, R.drawable.sugar),"설탕", ContextCompat.getDrawable(this, R.drawable.add));
//        adapter1.addItem(ContextCompat.getDrawable(this, R.drawable.soysauce),"간장", ContextCompat.getDrawable(this, R.drawable.add));
//        adapter1.addItem(ContextCompat.getDrawable(this, R.drawable.redpaste),"고추장", ContextCompat.getDrawable(this, R.drawable.add));
//        adapter1.addItem(ContextCompat.getDrawable(this, R.drawable.redpowder),"고춧가루", ContextCompat.getDrawable(this, R.drawable.add));
//        adapter1.addItem(ContextCompat.getDrawable(this, R.drawable.ketchup),"케첩", ContextCompat.getDrawable(this, R.drawable.add));
//        adapter1.addItem(ContextCompat.getDrawable(this, R.drawable.mayonnaise),"마요네즈", ContextCompat.getDrawable(this, R.drawable.add));
//        adapter1.addItem(ContextCompat.getDrawable(this, R.drawable.cookingoil),"식용유", ContextCompat.getDrawable(this, R.drawable.add));


        // 리스트뷰 크기 조절
        setListViewHeightBasedOnChildren(IgListView1);


        // 텍스트 변경 이벤트 처리
        i_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable edit) {
                String filterText = edit.toString();
                ((InputIngredientsAdapter)IgListView1.getAdapter()).getFilter().filter(filterText);
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

        });



        // back 버튼 구현
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }//OnCreate()


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


}