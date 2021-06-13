package gachon.seteam2.airlinereservation;
//무엇을 선택했는지 보여주는 액티비티
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class CheckActivity extends AppCompatActivity {
    private long count;
    private FirebaseAuth firebaseAuth2;
    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);

        Intent intent=getIntent();
        firebaseAuth2 = FirebaseAuth.getInstance();

        TextView txt1 = (TextView)findViewById(R.id.txt1);
        String seat = intent.getStringExtra("Seat");
        txt1.setText(seat);

        TextView txt2=(TextView) findViewById(R.id.txt2);
        String location = intent.getStringExtra("Location");
        txt2.setText(location);

        TextView txt3=(TextView) findViewById(R.id.txt3);
        String number=intent.getStringExtra("Number");
        txt3.setText(number);

        TextView txt4=(TextView) findViewById(R.id.txt4);
        String total=intent.getStringExtra("Total");
        txt4.setText(total);

        //이전 창에서 입력한 출발지와 도착지 불러오기
        SharedPreferences pref=getSharedPreferences("pref",MODE_PRIVATE);
        String source=pref.getString("source","");
        String destination=pref.getString("destination","");

        TextView txt5=(TextView) findViewById(R.id.txt5);
        txt5.setText(source);

        TextView txt6=(TextView) findViewById(R.id.txt6);
        txt6.setText(destination);

        TextView txt7 = findViewById(R.id.txt7);
        String airline=intent.getStringExtra("airline");
        txt7.setText(airline);

        TextView txt8 = findViewById(R.id.txt8);
        String date=intent.getStringExtra("date");
        txt8.setText(date);

        TextView txt9 = findViewById(R.id.txt9);
        String departureTime=intent.getStringExtra("departureTime");
        txt9.setText(departureTime);

        TextView txt10 = findViewById(R.id.txt10);
        String arrivalTime=intent.getStringExtra("arrivalTime");
        txt10.setText(arrivalTime);

        FirebaseUser user = firebaseAuth2.getCurrentUser();
        String uid = user.getUid();

        //해쉬맵 테이블을 파이어베이스 데이터베이스에 저장
        HashMap<Object,String> hashMap = new HashMap<>();
        //해쉬맵에 저장
        hashMap.put("id",uid);
        hashMap.put("airline", airline);
        hashMap.put("date", date);
        hashMap.put("Source",source);
        hashMap.put("Destination",destination);
        hashMap.put("departure time", departureTime);
        hashMap.put("arrival time", arrivalTime);
        hashMap.put("seat",seat);
        hashMap.put("location",location);
        hashMap.put("number",number);
        hashMap.put("total",total);
        //실시간 데이터베이스에 저장
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Reserve_List");
        reference.child(uid).setValue(hashMap);

        Toast.makeText(CheckActivity.this, "DB전송 성공.", Toast.LENGTH_SHORT).show();

        //예약정보 삭제
       Button deletebtn=(Button)findViewById(R.id.deletebtn);
        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child(uid).removeValue();
            }
        });

        Button homebtn = findViewById(R.id.homebtn);
        homebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CheckActivity.this, MainActivity.class));
                finish();
            }
        });
    }
}
