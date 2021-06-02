package com.example.justcook;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    NavigationView navigationView;
    BottomNavigationView bottomNavigationView;
    DrawerLayout drawer_layout;
    View my_page;
    View drawer;

    Fragment fragment1;
    Fragment fragment2;
    Fragment fragment3;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawer_layout = findViewById(R.id.drawer_layout);
        my_page = findViewById(R.id.my_page);
        drawer = findViewById(R.id.drawer);

        fragment1 = new Fragment1();
        fragment2 = new Fragment2();
        fragment3 = new Fragment3();

        bottomNavigationView = findViewById(R.id.bottom);
        navigationView = findViewById(R.id.drawer);


        my_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer_layout.openDrawer(drawer);
            }
        });

        // 초기화면 설정
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment1).commit();

        // fragment 설정
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                        switch (menuItem.getItemId()) {
                            case R.id.navigation_1:
                                getSupportFragmentManager().beginTransaction().
                                        replace(R.id.container, fragment1).commit();
                                return true;

                            case R.id.navigation_2:
                                getSupportFragmentManager().beginTransaction().
                                        replace(R.id.container, fragment2).commit();
                                return true;

                            case R.id.navigation_3:
                                getSupportFragmentManager().beginTransaction().
                                        replace(R.id.container, fragment3).commit();
                                return true;
                        }
                        return false;
                    }
                }
        );


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId()) {
                    case R.id.bookmark:
                        Intent i_bookmark = new Intent(MainActivity.this, my_bookmark.class);
                        startActivity(i_bookmark);
                        break;

                    case R.id.note:
                        Intent i_note = new Intent(MainActivity.this, my_note.class);
                        startActivity(i_note);
                        break;

                    case R.id.memo:
                        Intent i_memo = new Intent(MainActivity.this, my_memo.class);
                        startActivity(i_memo);
                        break;
                }
                drawer_layout.closeDrawer(navigationView);
                return false;
            }

        });




    }// onCreate()



    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}