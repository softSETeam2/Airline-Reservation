package gachon.seteam2.airlinereservation;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class SearchingReservationActivity extends AppCompatActivity {

    String name;
    private TextView airline;
    private EditText date;
    private Button dateButton;
    private Button setting_save_button;
    String[] SourceAirportData = {"무안   |   0", "광주   |   1", "군산   |   2", "여수   |   3", "원주   |   4", "양양   |   5", "제주   |   6", "김해   |   7", "사천   |   8", "울산   |   9", "인천   |   10", "김포   |   11", "포항   |   12", "대구   |   13", "청주   |   14" };
    String[] DestinationAirportData = {"무안   |   0", "광주   |   1", "군산   |   2", "여수   |   3", "원주   |   4", "양양   |   5", "제주   |   6", "김해   |   7", "사천   |   8", "울산   |   9", "인천   |   10", "김포   |   11", "포항   |   12", "대구   |   13", "청주   |   14" };
    String[] Hour = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24"};
    String[] Minute = {"00", "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55"};
    int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching_reservation);

        //액션 바 등록하기
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("예약 데이터 검색");

        actionBar.setDisplayHomeAsUpEnabled(true); //뒤로가기버튼
        actionBar.setDisplayShowHomeEnabled(true); //홈 아이콘

        airline = findViewById(R.id.airline);
        FirebaseDatabase.getInstance().getReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.child("FlightUsers").getChildren()) {
                    name = snapshot.child("name").getValue().toString();
                    airline.setText(name);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });

        date = (EditText)findViewById(R.id.date);

        dateButton = (Button)findViewById(R.id.dateButton);
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(v);
            }
        });

        Spinner sourceAirportSpinner = (Spinner) findViewById(R.id.sourceAirport);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, SourceAirportData);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sourceAirportSpinner.setAdapter(adapter2);

        Spinner destinationAirportSpinner = (Spinner) findViewById(R.id.destinationAirport);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, DestinationAirportData);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        destinationAirportSpinner.setAdapter(adapter3);

        Spinner departureTimeSpinner = (Spinner) findViewById(R.id.departureTimeHour);
        ArrayAdapter<String> adapter4 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Hour);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        departureTimeSpinner.setAdapter(adapter4);

        Spinner departureTimeSpinner2 = (Spinner) findViewById(R.id.departureTimeMinute);
        ArrayAdapter<String> adapter5 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Minute);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        departureTimeSpinner2.setAdapter(adapter5);

        Spinner arrivalTimeSpinner = (Spinner) findViewById(R.id.arrivalTimeHour);
        ArrayAdapter<String> adapter6 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Hour);
        adapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arrivalTimeSpinner.setAdapter(adapter6);

        Spinner arrivalTimeSpinner2 = (Spinner) findViewById(R.id.arrivalTimeMinute);
        ArrayAdapter<String> adapter7 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Minute);
        adapter7.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arrivalTimeSpinner2.setAdapter(adapter7);


        setting_save_button = findViewById(R.id.setting_save_button);
        setting_save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sourceAirport = sourceAirportSpinner.getSelectedItem().toString();
                String destinationAirport = destinationAirportSpinner.getSelectedItem().toString();
                String departureTime = departureTimeSpinner.getSelectedItem().toString();
                String departureTime2 = departureTimeSpinner2.getSelectedItem().toString();
                String arrivalTime = arrivalTimeSpinner.getSelectedItem().toString();
                String arrivalTime2 = arrivalTimeSpinner2.getSelectedItem().toString();

                HashMap<Object,String> hashMap = new HashMap<>();
                hashMap.put("Arrival Time", arrivalTime + arrivalTime2);
                hashMap.put("Departure Time", departureTime + departureTime2);

                flag = 0;

                if (airline.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "다시 시도해 주세요", Toast.LENGTH_LONG).show();
                    flag++;
                } else {
                    String[] flight = name.split("   ");
                    hashMap.put("airline", flight[0]);
                    hashMap.put("airline ID", flight[2]);
                }

                hashMap.put("destination apirport", destinationAirport.substring(0, 3));
                hashMap.put("destination airport id", destinationAirport.substring(9));
                hashMap.put("source airport", sourceAirport.substring(0, 3));
                hashMap.put("source airport id", sourceAirport.substring(9));

                if ((arrivalTime + arrivalTime2).equals(departureTime + departureTime2)) {
                    Toast.makeText(getApplicationContext(), "출발시간과 도착시간이 같을 수 없습니다", Toast.LENGTH_LONG).show();
                    flag++;
                }
                if (sourceAirport.equals(destinationAirport)) {
                    Toast.makeText(getApplicationContext(), "출발지와 도착지가 같을 수 없습니다.", Toast.LENGTH_LONG).show();
                    flag++;
                }
                if (date.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "날짜를 입력해주세요", Toast.LENGTH_LONG).show();
                    flag++;
                }

                if (flag == 0) {
                    startActivity(new Intent(SearchingReservationActivity.this, ReservationUserInfoActivity.class));
                    finish();
                }
            }
        });
    }

    public void showDatePicker(View view) {
        DialogFragment newFragment = new DatePickerFragment2();
        newFragment.show(getSupportFragmentManager(),"datePicker");
    }

    public void processDatePickerResult(int year, int month, int day){
        String month_string = Integer.toString(month+1);
        String day_string = Integer.toString(day);
        String year_string = Integer.toString(year);
        String dateMessage = (year_string + "." + month_string + "." + day_string);
        date.setText(dateMessage);
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