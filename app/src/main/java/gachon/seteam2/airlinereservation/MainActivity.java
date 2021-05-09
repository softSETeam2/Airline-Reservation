package gachon.seteam2.airlinereservation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    String uid; // 사용자 로그인 정보
    private DatabaseReference mDatabase;
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

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

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
}