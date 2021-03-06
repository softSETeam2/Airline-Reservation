package gachon.seteam2.airlinereservation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import lombok.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {
    Button mLoginBtn, mResigetBtn;
    EditText mEmailText, mPasswordText;
    CheckBox mAutoCheck, mLoginFlight;
    private DatabaseReference mDatabase;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase data;
    String uid;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 파이어 베이스 접근 레퍼런스 선언
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // 액션바 숨기기
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        // 파이어베이스 로그인 인스턴스
        firebaseAuth = FirebaseAuth.getInstance();

        // 뷰 등록
        mLoginBtn = findViewById(R.id.login_button);
        mResigetBtn = findViewById(R.id.join_button);
        mEmailText = findViewById(R.id.login_email);
        mPasswordText = findViewById(R.id.login_password);
        mAutoCheck = findViewById(R.id.auto_check);
        mLoginFlight = findViewById(R.id.login_flight);

        // 아이디 자동 입력 처리
        SharedPreferences pref = getSharedPreferences("login", MODE_PRIVATE);
        Boolean check_bool = pref.getBoolean("checkbox", false);
        if (check_bool) {
            String local_email = pref.getString("email", "");
            String local_password = pref.getString("password", "");
            mEmailText.setText(local_email);
            mPasswordText.setText(local_password);
            mAutoCheck.toggle();
        } else {
            mEmailText.setText("");
            mPasswordText.setText("");
        }

        // 가입버튼 눌리면 실행
        mResigetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 인텐트 함수를 통해 register 액티비티 함수 호출
                startActivity(new Intent(getApplicationContext(), SelectUserActivity.class));
                overridePendingTransition(R.anim.slide_enter, R.anim.none);
            }
        });

        // 로그인 버튼 눌리면 실행
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmailText.getText().toString().trim();
                String pwd = mPasswordText.getText().toString().trim();

                if (email.equals("") || pwd.equals("")) {
                    Toast.makeText(LoginActivity.this, "계정과 비밀번호를 입력하세요.", Toast.LENGTH_LONG).show();
                } else {
                    firebaseAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Toast.makeText(getApplicationContext(), "잠시만 기다려주세요....", Toast.LENGTH_LONG).show();

                            if (task.isSuccessful()) { // 로그인 성공
                                if (mLoginFlight.isChecked()) {
                                    FirebaseUser user = firebaseAuth.getCurrentUser();
                                    uid = user.getUid();

                                    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                                    rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@androidx.annotation.NonNull @NotNull DataSnapshot snapshot) {
                                            Log.d("MainActivity", uid);
                                            if (snapshot.child("FlightUsers").hasChild(uid)) {
                                                startActivity(new Intent(getApplicationContext(), FlightMainActivity.class));
                                                finish();
                                            } else {
                                                Log.d("MainActivity", "fail");
                                                Toast.makeText(getApplicationContext(), "항공사 계정이 아니거나, 등록된 계정이 없습니다.", Toast.LENGTH_LONG).show();
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@androidx.annotation.NonNull @NotNull DatabaseError error) {

                                        }
                                    });

                                } else {
                                    FirebaseUser user = firebaseAuth.getCurrentUser();
                                    uid = user.getUid();

                                    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                                    rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@androidx.annotation.NonNull @NotNull DataSnapshot snapshot) {
                                            Log.d("MainActivity", uid);
                                            if (snapshot.child("Users").hasChild(uid)) {
                                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                startActivity(intent);
                                                finish();
                                            } else {
                                                Log.d("MainActivity", "fail");
                                                Toast.makeText(getApplicationContext(), "고객 계정이 아니거나, 등록된 계정이 없습니다.", Toast.LENGTH_LONG).show();
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@androidx.annotation.NonNull @NotNull DatabaseError error) {

                                        }
                                    });
                                }

                                // 자동로그인 체크상태일 경우
                                SharedPreferences.Editor editor = pref.edit();
                                if (mAutoCheck.isChecked()) {
                                    editor.putString("email", mEmailText.getText().toString());
                                    editor.putString("password", mPasswordText.getText().toString());
                                    editor.putBoolean("checkbox", true);
                                } else {
                                    editor.putString("email", "");
                                    editor.putString("password", "");
                                    editor.putBoolean("checkbox", false);
                                }
                                editor.commit();


                            } else {
                                Toast.makeText(LoginActivity.this, "로그인 오류", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
