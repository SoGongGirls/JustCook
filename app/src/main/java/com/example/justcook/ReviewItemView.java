package com.example.justcook;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class ReviewItemView extends LinearLayout {

    ImageView rv_profile;
    TextView rv_name;
    TextView rv_time;
    TextView rv_comment;

    public ReviewItemView(Context context) {
        super(context);

        init(context);
    }

    public ReviewItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    // 초기화를 위한 메소드
    private  void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.review_item, this, true);

        rv_name = (TextView) findViewById(R.id.rv_name);
        rv_time = (TextView) findViewById(R.id.rv_time);
        rv_comment = (TextView) findViewById(R.id.rv_comment);
        rv_profile = (ImageView) findViewById(R.id.rv_profile);
    }


    // 실제 데이터를 설정해주는 메소드
    public void setName(String name) {
        rv_name.setText(name);
    }

    public void setTime(String time) {
        rv_time.setText(time);
    }

    public void setComment(String comment) {
        rv_comment.setText(comment);
    }
}
