package gachon.seteam2.airlinereservation;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class SelectUserActivity extends AppCompatActivity {

    LinearLayout customer;
    Button button_customer;
    LinearLayout flight;
    Button button_flight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_user);

        //액션 바 등록하기
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("회원가입");
        actionBar.setDisplayHomeAsUpEnabled(true); //뒤로가기버튼
        actionBar.setDisplayShowHomeEnabled(true); //홈 아이콘

        customer = findViewById(R.id.customer);
        customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectUserActivity.this, RegisterActivity.class));
                finish();
            }
        });

        button_customer = findViewById(R.id.button_customer);
        button_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectUserActivity.this, RegisterActivity.class));
                finish();
            }
        });

        flight = findViewById(R.id.flight);
        flight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectUserActivity.this, RegisterFlightActivity.class));
                finish();
            }
        });

        button_flight = findViewById(R.id.button_flight);
        button_flight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectUserActivity.this, RegisterFlightActivity.class));
                finish();
            }
        });
    }

    public boolean onSupportNavigateUp(){
        onBackPressed();; // 뒤로가기 버튼이 눌렸을시
        overridePendingTransition(R.anim.none, R.anim.slide_exit);
        return super.onSupportNavigateUp(); // 뒤로가기 버튼
    }
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.none, R.anim.slide_exit);
    }
}