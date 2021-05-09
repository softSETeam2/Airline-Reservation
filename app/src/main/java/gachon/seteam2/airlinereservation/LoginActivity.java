package gachon.seteam2.airlinereservation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {
    Button mLoginBtn,mResigetBtn;
    EditText mEmailText, mPasswordText;
    private DatabaseReference mDatabase;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        firebaseAuth =  FirebaseAuth.getInstance();
        // 뷰 등록
        mLoginBtn = findViewById(R.id.login_button);
        mResigetBtn = findViewById(R.id.join_button);
        mEmailText = findViewById(R.id.login_email);
        mPasswordText = findViewById(R.id.login_password);

        // 가입버튼 눌리면 실행
        mResigetBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // 인텐트 함수를 통해 register 액티비티 함수 호출
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
                overridePendingTransition(R.anim.slide_enter, R.anim.none);
            }
        });

        // 로그인 버튼 눌리면 실행
        mLoginBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String email = mEmailText.getText().toString().trim();
                String pwd = mPasswordText.getText().toString().trim();
                if(email.equals("") || pwd.equals("")){
                    Toast.makeText(LoginActivity.this, "계정과 비밀번호를 입력하세요.", Toast.LENGTH_LONG).show();
                }
                else {
                    firebaseAuth.signInWithEmailAndPassword(email, pwd)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(LoginActivity.this,  "hello", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);

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
