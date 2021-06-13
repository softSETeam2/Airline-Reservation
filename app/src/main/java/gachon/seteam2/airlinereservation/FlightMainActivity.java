package gachon.seteam2.airlinereservation;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class FlightMainActivity extends AppCompatActivity {

    Button flightData;
    Button reservationData;
    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_main);

        //액션 바 등록하기
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("기능 선택");
        actionBar.setDisplayShowHomeEnabled(true); //홈 아이콘

        flightData = findViewById(R.id.flightData);
        flightData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FlightMainActivity.this, FlightActivity.class));
            }
        });

        logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FlightMainActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
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