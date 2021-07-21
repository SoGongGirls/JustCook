package com.example.justcook;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.justcook.itemAdapter.IngredientAdapter;
import com.example.justcook.itemAdapter.Ingredientitem;
import com.example.justcook.itemAdapter.ProcessAdapter;
import com.example.justcook.itemAdapter.Processitem;


import java.util.ArrayList;

public class recipe extends AppCompatActivity {
    String TAG = "recipe상세화면";
    SQLiteDatabase db;
    ProcessAdapter apdaterPro;
    String rcode;
    ListView lvProcess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        ListView myListView = findViewById(R.id.myListView);
        View btn_back = findViewById(R.id.btn_back);

        LinearLayout LayoutSrc = (LinearLayout) findViewById(R.id.LayoutSrc);
        LinearLayout LayoutSub = (LinearLayout) findViewById(R.id.LayoutSub);

        lvProcess = (ListView) findViewById(R.id.listview_process);
        GridView IngMain = (GridView) findViewById(R.id.gv_ing_main);
        GridView IngSrc = (GridView) findViewById(R.id.gv_ing_src);
        GridView IngSub = (GridView) findViewById(R.id.gv_ing_sub);

        Intent intent = getIntent();
        rcode = intent.getStringExtra("rcode");
        IngredientAdapter adapterING_Main = new IngredientAdapter(getApplicationContext());
        IngredientAdapter adapterING_Src = new IngredientAdapter(getApplicationContext());
        IngredientAdapter adapterING_Sub = new IngredientAdapter(getApplicationContext());
        apdaterPro = new ProcessAdapter(getApplicationContext());

        TextView tvName = (TextView)findViewById(R.id.tvName);
        TextView tvSummary = (TextView)findViewById(R.id.tvSummary);
        TextView tvType = (TextView)findViewById(R.id.tvType);
        TextView tvFoodtype = (TextView)findViewById(R.id.tvFoodtype);
        TextView tvTime = (TextView)findViewById(R.id.tvTime);
        TextView tvCalorie = (TextView)findViewById(R.id.tvCalorie);
        TextView tvPerson = (TextView)findViewById(R.id.tvPerson);
        TextView tvLevel = (TextView)findViewById(R.id.tvLevel);
        TextView tvBase = (TextView)findViewById(R.id.tvBase);
        //CardView recipe_image = (CardView)findViewById(R.id.cvFood);
        ImageView recipe_image = (ImageView)findViewById(R.id.cvFood);

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

        //db 값(recipe_info) 불러오기
        if (db != null) {
            String table1 = "recipe_info";
            String sql = "select name, summary, type_name, foodtypename, time, calorie, person, level, base, image_url from "+table1+" where rcode = "+rcode;
            Cursor cursor = db.rawQuery(sql, null);
            Log.v(TAG, "조회된 데이터 수 : " + cursor.getCount());

            cursor.moveToNext();
            tvName.setText(cursor.getString(0));
            tvSummary.setText(cursor.getString(1));
            tvType.setText(cursor.getString(2));
            tvFoodtype.setText(cursor.getString(3));
            tvTime.setText(cursor.getString(4));
            tvCalorie.setText(cursor.getString(5));
            tvPerson.setText(cursor.getString(6));
            tvLevel.setText(cursor.getString(7));
            tvBase.setText(cursor.getString(8));

            // Glide로 이미지 표시하기
            String imageUrl = cursor.getString(9);
            Log.v(TAG, imageUrl);
            Glide.with(this).load(imageUrl).error(R.drawable.test_food).into(recipe_image);

            cursor.close();
        } else {
            Log.e(TAG, "selectData() db없음.");
        }

        loadProcessData(); //과정list값 로드
        loadIngredientData(0, adapterING_Main, IngMain, null ); //main
        loadIngredientData(1, adapterING_Src, IngSrc, LayoutSrc); //src
        loadIngredientData(2, adapterING_Sub, IngSub, LayoutSub);//sub

        // back 버튼 구현
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        ReviewAdapter adapter = new ReviewAdapter();
        adapter.addItem(new ReviewItem("커비", "15분 전", "그럭저럭 맛있네요"));
        adapter.addItem(new ReviewItem("에비츄", "10분 전", "직접 해먹은 요리라 그런지 뿌듯해요!"));
        adapter.addItem(new ReviewItem("루피","5분 전","레시피 그대로 했는데 별로.." +
                "재밌게 봐서 다른 사람들에게도 추천해주려고요."));

        myListView.setAdapter(adapter);
        setListViewHeightBasedOnChildren(myListView);
        setListViewHeightBasedOnChildren(lvProcess);



    }//onCreate()

    public static long setListViewHeightBasedOnChildren(@NonNull ListView listView) {
        long timer= System.currentTimeMillis();
        ListAdapter listAdapter = listView.getAdapter();
        int totalHeight = 0;
        if(listAdapter.getCount()> 0){
            View listItem = listAdapter.getView(0, null, listView);
            listItem.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            totalHeight = listItem.getMeasuredHeight()* listAdapter.getCount();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1)) + 100;
        listView.setLayoutParams(params);
        listView.requestLayout();
        return System.currentTimeMillis()- timer;
    }


    class ReviewAdapter extends BaseAdapter {
        ArrayList<ReviewItem> items = new ArrayList<ReviewItem>();

        // 몇 개의 item이 있는지 알려주는 메소드
        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(ReviewItem item) {
            items.add(item);
        }

        //  원하는 위치에 있는 item을 가져오는 메소드
        @Override
        public Object getItem(int i) {
            return items.get(i);
        }

        // id로 item을 가져오는 메소드
        @Override
        public long getItemId(int i) {
            return i;
        }

        // 데이터를 관리하는 어댑터가 화면에 보여질 뷰를 만듭니다.
        @Override
        public View getView(int i, View convertview, ViewGroup viewGroup) {
            ReviewItemView vw = new ReviewItemView(getApplicationContext());

            ReviewItem item = items.get(i);
            vw.setName(item.getRv_name());
            vw.setTime(item.getRv_time());
            vw.setComment(item.getRv_comment());

            return vw;
        }


    }
    public void loadProcessData(){
        Log.v(TAG, "loadProcessData() 호출됨.");
        if (db != null) {
            String tableName3 = "recipe_process";
            String sql = "SELECT * FROM "+tableName3+" WHERE rcode="+rcode+" ORDER BY 3 ASC";
            Cursor cursor = db.rawQuery(sql, null);
            Log.v(TAG, "조회된 데이터 수 : " + cursor.getCount());

            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                String explain = cursor.getString(1);
                String order = String.valueOf(cursor.getInt(2));
                String url = cursor.getString(3);
                String tips = cursor.getString(4);

                apdaterPro.addItem(new Processitem(explain, order, url, tips));
            }
            lvProcess.setAdapter(apdaterPro);
            cursor.close();
        } else {
            Log.e(TAG, "selectData() db없음.");
        }

    }
    public void loadIngredientData(int num, IngredientAdapter adpater, GridView GV, LinearLayout Layout){
        Log.v(TAG, "loadIngredientData() 호출됨.");
        String TCN=null;
        if (num ==0){
            TCN = "주재료";
        }else if(num ==1){
            TCN = "양념";
        }else if(num ==2){
            TCN = "부재료";
        }

        if (db != null) {
            String tableName2 = "recipe_ingredient";
            //String sql = "select name, quantity,type_code_name from "+tableName2+" where rcode="+rcode;
            String sql = "select name, quantity,type_code_name from "+tableName2+" where rcode="+rcode+" and type_code_name=\'"+TCN+"\'";
            Cursor cursor = db.rawQuery(sql, null);
            Log.v(TAG, "조회된 데이터 수 : " + cursor.getCount());

            if (cursor.getCount() == 0){
                Layout.setVisibility(View.GONE);
            }else{
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToNext();
                    String name = cursor.getString(0);
                    String quantity = cursor.getString(1);
                    String type_code_name = cursor.getString(2);

                    adpater.addItem(new Ingredientitem(name, quantity, type_code_name));
                }
                GV.setAdapter(adpater);
                cursor.close();
            }

        } else {
            Log.e(TAG, "selectData() db없음.");
        }

    }
}