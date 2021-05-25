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

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


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

        switch(item.getItemId()) {
            case R.id.bookmark:
                Intent bookmark_i1 = new Intent(MainActivity.this, my_bookmark.class);
                startActivity(bookmark_i1);
            case R.id.note:
                Toast.makeText(getApplicationContext(),"노트", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.memo:
                return true;
        }

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

        my_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer_layout.openDrawer(drawer);
            }
        });

        // 초기화면 설정
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment1).commit();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom);

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




    }// onCreate()

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int id = menuItem.getItemId();

        if (id == R.id.bookmark) {

            Intent bookmark_i1 = new Intent(MainActivity.this, my_bookmark.class);
            startActivity(bookmark_i1);

        } else if (id == R.id.note) {
            Toast.makeText(getApplicationContext(), "망망망", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.memo) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

}