package com.example.justcook;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.justcook.fragment.Fragment3;
import com.example.justcook.itemAdapter.RecipeAdapter;
import com.example.justcook.itemAdapter.RecipeItem;

import java.util.ArrayList;
import java.util.Arrays;

public class my_bookmark extends AppCompatActivity {
    SQLiteDatabase database;
    String TAG = "마이 북마크";
    View btn_back;
    TextView bar_title;
    ListView B_ListView;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bookmark);

        bar_title = findViewById(R.id.bar_title);
        btn_back = findViewById(R.id.btn_back);
        B_ListView = findViewById(R.id.B_ListView);
        bar_title.setText("북마크");

        RecipeAdapter adapter = new RecipeAdapter(getApplicationContext());

        // 리스트뷰 참조 및 Adapter 연결
        B_ListView.setAdapter(adapter);
        //임시 rcode 리스트 생성
        //ArrayList<Integer> rcode_list = new ArrayList<>(Arrays.asList(1,2,3));
        ArrayList<Integer> rcode_list = FavoriteQuery.AllBookmarkData();

        // DB오픈
        Log.v(TAG, "openDB()실행.");
        MySQLiteOpenHelper helper = new MySQLiteOpenHelper(getApplicationContext());
        database = helper.getWritableDatabase();
        if (database != null) { Log.v(TAG, "DB열기 성공!");
        } else { Log.e(TAG, "DB열기 실패!"); }

        //데이터 가져오기
        for(int k = 0; k < rcode_list.size(); k++){
            Log.v(TAG, String.valueOf(rcode_list.size()));
            String q = "select name, foodtypename, rcode, image_url from recipe_info where rcode="+rcode_list.get(k);
            Cursor cursor = database.rawQuery(q, null);
            cursor.moveToNext();

            String name = cursor.getString(0);
            String foodtypename = cursor.getString(1);
            int rcode = cursor.getInt(2);
            String img_url = cursor.getString(3);
            adapter.addItem(new RecipeItem(name, foodtypename, rcode, img_url ));

            cursor.close();
        }
        B_ListView.setAdapter(adapter);


        // 레시피 상세 보기
        B_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent i_recipe = new Intent(getApplicationContext(), recipe.class);
                RecipeItem item = (RecipeItem) adapter.getItem(i);
                String rcode = String.valueOf(item.getRcode());
                Log.v(TAG, "rcode는" + rcode);
                i_recipe.putExtra("rcode", rcode);
                startActivity(i_recipe);
            }
        });


        // back 버튼 구현
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }//onCreate()
}