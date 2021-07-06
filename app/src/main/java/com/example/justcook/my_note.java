package com.example.justcook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.justcook.memo.InsertActivity;
import com.example.justcook.memo.MemoDB;
import com.example.justcook.memo.UpdateActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


public class my_note extends AppCompatActivity {

    View btn_back;
    TextView bar_title;
    MemoDB helper;
    SQLiteDatabase db;
    MyAdapter adapter;
    Cursor cursor;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_note);

        bar_title = findViewById(R.id.bar_title);
        btn_back = findViewById(R.id.btn_back);
        bar_title.setText("레시피 노트");

        // back 버튼 구현
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        helper=new MemoDB(this);
        db=helper.getReadableDatabase();

        cursor=db.rawQuery("select * from memo order by wdate desc",null);
        list=findViewById(R.id.list);
        adapter=new MyAdapter(this,cursor);
        list.setAdapter(adapter);


        //리스터를 걸겠다.
        FloatingActionButton btnwrite=findViewById(R.id.btnwrite);
        btnwrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //activity이동
                Intent i_insert=new Intent(my_note.this,InsertActivity.class);
                startActivity(i_insert);
            }
        });

    }

    class MyAdapter extends CursorAdapter{
        public MyAdapter(Context context, Cursor c) {
            super(context, c);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            //아이템 만들기
            return getLayoutInflater().inflate(R.layout.item,parent,false);
        }

        @Override
        public void bindView(View view, Context context, final Cursor cursor) {
            TextView txtcontent=view.findViewById(R.id.txtcontent);
            txtcontent.setText(cursor.getString(1));
            TextView txtwdate=view.findViewById(R.id.txtwdate);
            txtwdate.setText(cursor.getString(3));

            //ListView에 item을 생성했을때
            ImageView btndel=view.findViewById(R.id.btndel);

            //아이디값 가져오기
            final int _id=cursor.getInt(0);
            //삭제버튼 이벤트 설정
            btndel.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    //CONFRIM
                    AlertDialog.Builder box=new AlertDialog.Builder(my_note.this);
                    box.setMessage(_id+"을(를) 삭제하시겠습니까?");
                    box.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String sql="delete from memo where _id="+_id;
                            db.execSQL(sql);
                            //새로고침
                            onRestart();
                        }
                    });
                    box.setNegativeButton("닫기",null);
                    box.show();
                }
            });

            View ListNote=view.findViewById(R.id.ListNote);
            ListNote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(my_note.this,UpdateActivity.class);
                    intent.putExtra("_id", _id);
                    startActivity(intent);
                }
            });
        }
    }
    //옵션메뉴 생성
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);

        //검색버튼
        MenuItem search=menu.findItem(R.id.search);
        SearchView view=(SearchView)search.getActionView();
        //query text가 변했을 때 발생
        //ActionView에 리스너를 걸어준다.
        view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            //엔터를 칠때 검색
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String sql="select * from memo where content like  '%" + newText + "%'";
                cursor=db.rawQuery(sql,null);
                adapter.changeCursor(cursor);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
    //옵션메뉴를 눌렀을때 발생하는 이벤트
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.itemcontent:
                cursor=db.rawQuery("select * from memo order by content",null);
                break;
            case R.id.itemwdate:
                cursor=db.rawQuery("select * from memo order by wdate desc",null);
                break;
        }
        //커서내용이 변경되었으므로 바뀐 커서값을 어덥터에서 바꿔줌
        adapter.changeCursor(cursor);
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onRestart() {
        cursor=db.rawQuery("select * from memo order by wdate desc",null);
        adapter.changeCursor(cursor);
        super.onRestart();
    }
}