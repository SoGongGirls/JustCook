package com.example.justcook;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class recipe extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);


        ListView myListView = findViewById(R.id.myListView);

        View btn_back = findViewById(R.id.btn_back);

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
}