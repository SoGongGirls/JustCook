package com.example.justcook;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toolbar;

public class my_bookmark extends AppCompatActivity {

    TextView bar_title;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bookmark);

        bar_title = findViewById(R.id.bar_title);
        bar_title.setText("북마크");

        

    }//onCreate()
}