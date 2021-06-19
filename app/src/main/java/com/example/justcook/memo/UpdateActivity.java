package com.example.justcook.memo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.justcook.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class UpdateActivity extends AppCompatActivity {

    View btn_back;
    TextView bar_title;
    int _id;
    MemoDB helper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        bar_title = findViewById(R.id.bar_title);
        btn_back = findViewById(R.id.btn_back);
        bar_title.setText("메모 읽기");

        // back 버튼 구현
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        Intent intent=getIntent();
        _id=intent.getIntExtra("_id",0);

        helper=new MemoDB(this);
        db=helper.getWritableDatabase();
        Cursor cursor=db.rawQuery("select * from memo where _id="+_id, null);
        if(cursor.moveToNext()){
            TextView txtwdate=findViewById(R.id.txtwdate);
            txtwdate.setText("작성일:"+cursor.getString(2));
            TextView edtcontent=findViewById(R.id.edtcontent);
            edtcontent.setText("작성내용:"+cursor.getString(1));
        }

        FloatingActionButton btnsave=findViewById(R.id.btnsave);
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder box=new AlertDialog.Builder(UpdateActivity.this);
                box.setMessage("수정하시겠습니까?");
                box.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText edtcontent=findViewById(R.id.edtcontent);
                        String strcontent=edtcontent.getText().toString();
                        String sql="update memo set content='" + strcontent + "' ";
                        sql += " where _id=" +_id;
                        db.execSQL(sql);
                        finish();
                    }
                });
                box.setNegativeButton("닫기",null);
                box.show();
            }
        });
        //Toast.makeText(UpdateActivity.this,"_id="+_id,Toast.LENGTH_SHORT).show();
    }
    //뒤로가기 옵션 버튼에 이벤트주기
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}