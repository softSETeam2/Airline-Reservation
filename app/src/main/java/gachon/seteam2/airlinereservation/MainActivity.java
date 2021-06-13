package gachon.seteam2.airlinereservation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    // 메인 액티비티
    private Context mContext = MainActivity.this;
    // 사용자 로그인 정보
    public String uid = null;
    public String name;
    // 데이터베이스 정보
    Button searchBtn;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    // 드로어, 네비게이션 뷰
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private View drawerView;
    //예약하러가기 버튼
    Button reserveBtn;
    private long backBtnTime = 0;
    int dy=0, dm=0, dd=0;
    int ay=0, am=0, ad=0;
    String start;
    String destination;
    String odate;
    private RecyclerAdapter adapter;
    private List<String> aplist1 = new ArrayList<String>();
    private List<String> datelist = new ArrayList<String>();  //출발날짜
    private List<String> dtList = new ArrayList<String>();  //도착지
    private List<String> sourcelist = new ArrayList<String>();  //출발지
    private List<String> departureTimeList = new ArrayList<>();
    private List<String> arrivalTimeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //액션 바 등록하기
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("메인화면");
        //        actionBar.setDisplayHomeAsUpEnabled(true); //뒤로가기버튼
//        actionBar.setDisplayShowHomeEnabled(true); //홈 아이콘

        // 드로어, 네비게이션 뷰 등록
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawerView = (View) findViewById(R.id.navigationView);
        drawerLayout.setDrawerListener(listener); // 드로어 리스너 실행
        navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(navListener); // 네비게이션아이템 리스터 실행

        //로그인 성공시
        Toast.makeText(this, "환영합니다.", Toast.LENGTH_SHORT).show();
        //데이터베이스 연동
        mDatabase = FirebaseDatabase.getInstance().getReference();

        //로그인된 사용자 정보 가져오기
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        searchBtn=findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                startActivityForResult(intent, 1);
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent resultIntent) {
        super.onActivityResult(requestCode, resultCode, resultIntent);

        if (resultCode == 2) // 액티비티가 정상적으로 종료되었을 경우
        {
            if (requestCode == 1) // requestCode==1 로 호출한 경우에만 처리.
            {
                start=resultIntent.getStringExtra("start");
                destination=resultIntent.getStringExtra("destination");
                dy=resultIntent.getIntExtra("dy",0);
                dm=resultIntent.getIntExtra("dm",0);
                dd=resultIntent.getIntExtra("dd",0);
                odate=(String)(dy +String.format("%02d",dm)+String.format("%02d",dd));
                Log.w("cd", String.valueOf(odate));
                Log.w("cd", start);
                Log.w("cd", destination);
            }
        }

        mDatabase.child("Flight").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                datelist.clear();
                dtList.clear();
                sourcelist.clear();
                aplist1.clear();
                departureTimeList.clear();
                arrivalTimeList.clear();

                // Get Post object and use the values to update the UI
                for (DataSnapshot message : dataSnapshot.getChildren()) {
                    String str = (String) message.child("airline").getValue();
                    String str1 = (String) message.child("Departure Date").getValue();
                    String str2 = (String) message.child("destination apirport").getValue();
                    String str3 = (String) message.child("source airport").getValue();
                    String str4 = String.valueOf(message.child("Departure Time").getValue());
                    String str5 = String.valueOf(message.child("Arrival Time").getValue());

                    if(odate.equals("00000"))
                    {
                        Log.w("cd", str1+" "+odate);
                        if (str2.equals(destination)) {
                            if (str3.equals(start)) {
                                aplist1.add(str);
                                datelist.add(str1);
                                dtList.add(str2);
                                sourcelist.add(str3);
                                departureTimeList.add(str4);
                                arrivalTimeList.add(str5);
                            }
                        }
                    }
                    else if(odate.equals(str1))
                    {
                        Log.w("cd", str1+" "+odate);
                        if (str2.equals(destination)) {
                            if (str3.equals(start)) {
                                aplist1.add(str);
                                datelist.add(str1);
                                dtList.add(str2);
                                sourcelist.add(str3);
                                departureTimeList.add(str4);
                                arrivalTimeList.add(str5);
                            }
                        }
                    }
                }
                getData();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("FireBaseData", "loadPost:onCancelled", databaseError.toException());
            }
        });
    }

    // 액션바 등록
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar, menu);
        return true;
    }
    //액션아이템 클릭 리스너
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) { // 우측상단 메뉴버튼 클릭시
            case R.id.drawer:
                // 사용자 정보 가져오기
                mAuth = FirebaseAuth.getInstance();
                FirebaseUser currentUser = mAuth.getCurrentUser();
                // 이메일 set
                TextView userEmail = findViewById(R.id.userEmail);
                userEmail.setText(currentUser.getEmail());

                drawerLayout.openDrawer(drawerView);

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    //드로어 레이아웃 클릭 이벤트 리스너 정의
    DrawerLayout.DrawerListener listener = new DrawerLayout.DrawerListener() {
        @Override
        public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
        }

        @Override
        public void onDrawerOpened(@NonNull View drawerView) {
        }

        @Override
        public void onDrawerClosed(@NonNull View drawerView) {
        }

        @Override
        public void onDrawerStateChanged(int newState) {
        }
    };

    //네비게이션 드로어 아이템 클릭 이벤트 리스터 정의
    NavigationView.OnNavigationItemSelectedListener navListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.logout: // 로그아웃 버튼
                    new AlertDialog.Builder(mContext) // 확인창 표시
                            .setTitle("로그아웃").setMessage("로그아웃 하시겠습니까?")
                            .setPositiveButton("로그아웃", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                }
                            }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {

                        }
                    })
                            .show();
                    return true;
            }

            DrawerLayout drawer = findViewById(R.id.drawerLayout);
            drawer.closeDrawer(Gravity.RIGHT);
            return true;
        }
    };

    //뒤로가기 버튼 클릭시
    @Override
    public void onBackPressed() {
        // 드로어가 열려있으면 뒤로가기 버튼 클릭시 드로어만 종료
        DrawerLayout drawer = findViewById(R.id.drawerLayout);
        if (drawer.isDrawerOpen(Gravity.RIGHT)) {
            drawer.closeDrawer(Gravity.RIGHT);
        } else {
            long curTime = System.currentTimeMillis();
            long gapTime = curTime - backBtnTime;
            // 뒤로가기 두번 클릭시 앱 완전히 종료
            if(0 <= gapTime && 2000 >= gapTime) {
                ActivityCompat.finishAffinity(this);
                System.exit(0);
            }
            else {
                backBtnTime = curTime;
                Toast.makeText(this, "한번 더 누르면 종료됩니다.",Toast.LENGTH_SHORT).show();
            }

        }
    }
    private void getData() {
        init();
        Log.w("cd", "size"+aplist1.size());
        for (int i = 0; i < aplist1.size(); i++) {
            RecyclerItem data = new RecyclerItem();
            data.setairplane(aplist1.get(i));
            data.setodate(datelist.get(i));
            data.setdestination(dtList.get(i));
            data.setsource(sourcelist.get(i));

            if (departureTimeList.get(i).length() == 3)
                data.setdepartureTime("0" + departureTimeList.get(i));
            else
                data.setdepartureTime(departureTimeList.get(i));

            if (arrivalTimeList.get(i).length() == 3)
                data.setarrivalTime("0" + arrivalTimeList.get(i));
            else
                data.setarrivalTime(arrivalTimeList.get(i));

            adapter.addItem(data);
        }
        Log.d("NO",String.valueOf(adapter.getItemCount()));
    }
    private void init() {
        //초기화 함수
          /*'내용'과 '사진'중에서 선택했을 때, 어댑터의 switchLayout메서드를 호출하여 레이아웃바꿔줌.
      작성 버튼을 누르면, 두번째 프래그먼트를 띄워줌.*/


        //리사이클뷰 새로 만들어주기
        RecyclerView recyclerView = this.findViewById(R.id.recyclerView);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);

        adapter = new RecyclerAdapter();
        //리사이클뷰 클릭이벤트
        //특정 리사이클뷰 클릭시 그 글 post에 들어가고 title content key값 넘김
        adapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {

            }
        });
        recyclerView.setAdapter(adapter);
    }
}