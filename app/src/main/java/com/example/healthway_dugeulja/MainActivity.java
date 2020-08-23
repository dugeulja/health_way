package com.example.healthway_dugeulja;
/*
build.gradle에 dependencies 추가 해야 함
    implementation 'com.github.bumptech.glide:glide:4.11.0' <-했어요!
    implementation 'com.android.support:design:28.0.0'
    빨간줄 떴었는데 refactoring 하고 material로 바뀌었습니다.

https://everyshare.tistory.com/21
참고
 */

import android.os.Bundle;
import android.util.Log;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ActionBar actionBar;
    BottomNavigationView bottomNavigationView;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    ImageView imageView_navigationDrawerHeader;
    View view_navigationDrawerHeader;
    Fragment1 fragment1;
    Fragment2 fragment2;
    Fragment3 fragment3;
    Fragment4 fragment4;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Video> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;


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
        fragment1 = new Fragment1();
        fragment2 = new Fragment2();
        fragment3 = new Fragment3();
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


    //RecyclerView를 위한 코드

        recyclerView = findViewById(R.id.recyclerView); // 아이디 연결
        recyclerView.setHasFixedSize((true)); //리사이클러뷰 기존 성능 강화
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager((layoutManager));
        arrayList = new ArrayList<>(); //Video 객체를 담을 어레이 리스트 (어댑터쪽으로)

        database = FirebaseDatabase.getInstance(); //파이업ㅔ이스 데이터베이스 연동

        databaseReference = database.getReference("Video_part1"); //DB 테이블 연결
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //파이어베이스 데이터베잇의 데이터를 받아오는 곳
                arrayList.clear(); //기존 배열 리스트 존재하지 않게 초기화
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) { //반복문으로 데이터 List를 추출해냠
                    Video video = snapshot.getValue(Video.class);//만들어뒀던 Video 객체에 데이터를 담는다.
                    arrayList.add(video); //담은 데이터들을 배열리스트에 넣고 리사이클러뷰로 보낼 준비
                }
                adapter.notifyDataSetChanged(); //리스트 저장 및 새로고침
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //디비를 가져오던 중 에러 발생 시
                Log.e("MainActivity", String.valueOf(databaseError.toException()));//에러문 출력
            }
        });

        adapter = new CustomAdapter(arrayList, this);
        recyclerView.setAdapter(adapter); //리사이클러뷰에 어댑터 연결

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