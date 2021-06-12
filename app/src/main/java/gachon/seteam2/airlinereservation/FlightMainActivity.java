package gachon.seteam2.airlinereservation;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class FlightMainActivity extends AppCompatActivity {

    Button flightData;
    Button reservationData;

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

        reservationData = findViewById(R.id.reservationData);
        reservationData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FlightMainActivity.this, SearchingReservationActivity.class));
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