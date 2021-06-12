package gachon.seteam2.airlinereservation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterFlightActivity extends AppCompatActivity {

    private static final String TAG = "RegisterFlightActivity";
    private FirebaseAuth firebaseAuth;
    Spinner join_name;
    EditText join_email;
    EditText join_password;
    EditText join_pwck;
    String[] airlineData = {"아시아나항공   |   0", "에어부산   |   1", "에어서울   |   2", "이스타항공   |   3", "플라이강원   |   4", "하이에어   |   5", "제주공항   |   6", "진에어   |   7", "대한항공   |   8", "티웨이항공   |   9"};
    Button delete;
    Button join_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_flight);

        Log.d(TAG, "start");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("회원가입");
        actionBar.setDisplayHomeAsUpEnabled(true); //뒤로가기버튼
        actionBar.setDisplayShowHomeEnabled(true); //홈 아이콘

        firebaseAuth = FirebaseAuth.getInstance();

        join_name = findViewById(R.id.join_name);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, airlineData);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        join_name.setAdapter(adapter);

        join_email = findViewById(R.id.join_email);
        join_password = findViewById(R.id.join_password);
        join_pwck = findViewById(R.id.join_pwck);

        delete = findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterFlightActivity.this, SelectUserActivity.class));
                finish();
            }
        });

        join_button = findViewById(R.id.join_button);
        join_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterFlightActivity.this, LoginActivity.class));
                finish();
            }
        });

        Log.d(TAG, "start2");

        //가입버튼 클릭리스너   -->  firebase에 데이터를 저장한다.
        join_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //가입 정보 가져오기
                Log.d(TAG, "click");

                final String email = join_email.getText().toString().trim();
                String pwd = join_password.getText().toString().trim();
                String pwCheck = join_pwck.getText().toString().trim();

                Log.d(TAG, "getText");

                if(pwd.equals(pwCheck)) {
                    Log.d(TAG, "if");
                    Log.d(TAG, "등록 버튼 " + email + " , " + pwd);
                    final ProgressDialog mDialog = new ProgressDialog(RegisterFlightActivity.this);
                    mDialog.setMessage("가입중입니다...");
                    mDialog.show();

                    //파이어베이스에 신규계정 등록하기
                    firebaseAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(RegisterFlightActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            //가입 성공시
                            if (task.isSuccessful()) {
                                mDialog.dismiss();

                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                String email = user.getEmail();
                                String uid = user.getUid();
                                String name = join_name.getSelectedItem().toString().trim();

                                //해쉬맵 테이블을 파이어베이스 데이터베이스에 저장
                                HashMap<Object,String> hashMap = new HashMap<>();
                                //해쉬맵에 저장
                                hashMap.put("uid", uid);
                                hashMap.put("email", email);
                                hashMap.put("name", name);
                                //실시간 데이터베이스에 저장
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference reference = database.getReference("FlightUsers");
                                reference.child(uid).setValue(hashMap);

                                //가입이 이루어져을시 가입 화면을 빠져나감.
                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                finish();
                                Toast.makeText(RegisterFlightActivity.this, "회원가입에 성공하셨습니다.", Toast.LENGTH_SHORT).show();
                            } else {
                                mDialog.dismiss();
                                Toast.makeText(RegisterFlightActivity.this, "이미 존재하는 아이디 입니다.", Toast.LENGTH_SHORT).show();
                                return;  //해당 메소드 진행을 멈추고 빠져나감.
                            }
                        }
                    });
                    //비밀번호 오류시
                } else{
                    Toast.makeText(RegisterFlightActivity.this, "비밀번호가 틀렸습니다. 다시 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();; // 뒤로가기 버튼이 눌렸을시
        overridePendingTransition(R.anim.none, R.anim.slide_exit);
        return super.onSupportNavigateUp(); // 뒤로가기 버튼
    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.none, R.anim.slide_exit);
    }
}