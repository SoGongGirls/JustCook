package com.example.justcook;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.justcook.itemAdapter.RecipeAdapter;
import com.example.justcook.itemAdapter.RecipeItem;

public class my_bookmark extends AppCompatActivity {

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

        // 리스트 아이템 추가
        adapter.addItem(new RecipeItem("토마토달걀볶음", "foodtypename", -1, null ));
        B_ListView.setAdapter((ListAdapter) adapter);


        // 레시피 상세 보기
        B_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent b_recipe = new Intent(getApplicationContext(), recipe.class);
                startActivity(b_recipe);
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