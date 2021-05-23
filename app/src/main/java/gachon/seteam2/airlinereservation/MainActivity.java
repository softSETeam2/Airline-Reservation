package gachon.seteam2.airlinereservation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    String uid; // 사용자 로그인 정보
    Button searchBtn;
    private DatabaseReference mDatabase;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //액션 바 등록하기
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("메인화면");

        //데이터베이스 연동
        mDatabase = FirebaseDatabase.getInstance().getReference();

        {
            //사용자 로그인 정보 받기
            Intent intent = getIntent();
            uid = intent.getExtras().getString("uid");

            mDatabase.child("Users").child(uid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    // Get Post object and use the values to update the UI
                    if (dataSnapshot.getValue(User.class) != null) {
                        User post = dataSnapshot.getValue(User.class);
                        Log.w("FireBaseData", "getData" + post.toString());
                    } else {
                        Toast.makeText(MainActivity.this, "데이터 없음...", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Getting Post failed, log a message
                    Log.w("FireBaseData", "loadPost:onCancelled", databaseError.toException());
                }
            });

            Toast.makeText(this, "환영합니다.", Toast.LENGTH_SHORT).show();
        }


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
                Log.w("cd", String.valueOf(dd));
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
                    // Get Post object and use the values to update the UI
                    for (DataSnapshot message : dataSnapshot.getChildren()) {
                        String str = (String) message.child("airline").getValue();
                        String str1 = (String) message.child("Departure Date").getValue();
                        String str2 = (String) message.child("destination apirport").getValue();
                        String str3 = (String) message.child("source airport").getValue();
                        if(odate.equals("00000"))
                        {
                            if (str2.equals(destination)) {
                                if (str3.equals(start)) {
                                    aplist1.add(str);
                                    datelist.add(str1);
                                    dtList.add(str2);
                                    sourcelist.add(str3);
                                }
                            }
                        }
                        else if(str1.equals(odate))
                        {
                            Log.w("cd", str1+" "+odate);
                            if (str2.equals(destination)) {
                                if (str3.equals(start)) {
                                    aplist1.add(str);
                                    datelist.add(str1);
                                    dtList.add(str2);
                                    sourcelist.add(str3);
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
        getMenuInflater().inflate(R.menu.actionbar, menu) ;

        return true ;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.drawer :
//                Toast.makeText(this, "drawer", Toast.LENGTH_SHORT).show();
//                Intent drawerIntent = new Intent(getApplicationContext(), DrawerActivity.class);
//                startActivity(drawerIntent);
//                overridePendingTransition(R.anim.slide_enter,R.anim.none);
                return true ;

            default :
                return super.onOptionsItemSelected(item) ;
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