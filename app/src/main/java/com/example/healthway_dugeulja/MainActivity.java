package com.example.healthway_dugeulja;
/*
build.gradle에 dependencies 추가 해야 함
    implementation 'com.github.bumptech.glide:glide:4.11.0'
    implementation 'com.android.support:design:28.0.0'

https://everyshare.tistory.com/21
참고
 */

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ActionBar actionBar;
    BottomNavigationView bottomNavigationView;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    ImageView imageView_navigationDrawerHeader;
    View view_navigationDrawerHeader;
    com.example.ui_hs_0813.Fragment1 fragment1;
    Fragment2 fragment2;
    com.example.ui_hs_0813.Fragment3 fragment3;
    Fragment4 fragment4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        action bar 참고 사이트
        https://itpangpang.xyz/326
         */
        //Apply the toolbar as action bar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();

        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);    //remove the basic title
        //actionBar.setDisplayHomeAsUpEnabled(true);      //automatically make back button

        //display icon in action bar
        actionBar.setIcon(R.drawable.logo);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        //get id of bottomNavigationView
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        //get id of drawerLayout
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //get id of navigationView
        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close
        );
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        //get headerView from navigationView
        view_navigationDrawerHeader = (View) navigationView.getHeaderView(0);
        //get id of profile_img from headerView(drawer_header)
        imageView_navigationDrawerHeader = (ImageView) view_navigationDrawerHeader.findViewById(R.id.profile_img);
        //crop the image circularly and display the image into imageView in drawer header
        Glide.with(this)
                .load(R.drawable.user_image)
                .apply(new RequestOptions().circleCrop())
                .into(imageView_navigationDrawerHeader);

        //create Fragments
        fragment1 = new com.example.ui_hs_0813.Fragment1();
        fragment2 = new Fragment2();
        fragment3 = new com.example.ui_hs_0813.Fragment3();
        fragment4 = new Fragment4();

        //제일 처음 띄울 뷰를 세팅, commit();까지 해야 함
        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, fragment1).commitAllowingStateLoss();

        //bottomNavigationView의 아이콘을 선택했을 때 원하는 Fragment가 띄워질 수 있도록 linster를 추가
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                //bottom_navigation_menu.xml에서 지정해줬던 id를 받아와서 각 id마다 다른 event 발생
                switch (menuItem.getItemId()) {
                    case R.id.tab1:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, fragment1).commitAllowingStateLoss();
                        return true;
                    case R.id.tab2:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, fragment2).commitAllowingStateLoss();
                        return true;
                    case R.id.tab3:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, fragment3).commitAllowingStateLoss();
                        return true;
                    case R.id.tab4:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, fragment4).commitAllowingStateLoss();
                        return true;
                    default:
                        return false;
                }

            }
        });

        //When the item of NavigationDrawerView is selected.
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menu1:
                        Toast.makeText(getApplicationContext(), "메뉴1이 선택되었습니다.", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menu2:
                        Toast.makeText(getApplicationContext(), "메뉴2가 선택되었습니다.", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menu3:
                        Toast.makeText(getApplicationContext(), "메뉴3이 선택되었습니다.", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menu4:
                        Toast.makeText(getApplicationContext(), "메뉴4가 선택되었습니다.", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menu5:
                        Toast.makeText(getApplicationContext(), "메뉴5가 선택되었습니다.", Toast.LENGTH_SHORT).show();
                        break;
                }

                //When MenuItem is clicked, close the drawer.
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflate the menu
        getMenuInflater().inflate(R.menu.actionbar_menu, menu);


        return super.onCreateOptionsMenu(menu);
    }

    //When back button is pressed.
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        //as you specify a parent activity in AndroidMaifest.xml.

        switch (item.getItemId()) {
            case R.id.mypage_btn:
                //TODO: 메뉴 버튼 눌렀을 때 이벤트 구현
                Toast.makeText(this, "메뉴 버튼 눌림", Toast.LENGTH_SHORT).show();
                drawerLayout.openDrawer(GravityCompat.START);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}