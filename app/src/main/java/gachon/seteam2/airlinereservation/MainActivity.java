package gachon.seteam2.airlinereservation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

    }
}