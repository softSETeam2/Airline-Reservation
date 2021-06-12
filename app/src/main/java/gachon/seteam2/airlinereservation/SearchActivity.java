package gachon.seteam2.airlinereservation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    Fragment2 fragment2;
    Button round_trip;
    Button one_way;
    Button save;
    int fg=1;
    private ArrayList<String> dtList = new ArrayList<String>();  //도착지
    private ArrayList<String> sourcelist = new ArrayList<String>();  //출발지

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Flight").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dtList.clear();
                sourcelist.clear();
                // Get Post object and use the values to update the UI
                for (DataSnapshot message : dataSnapshot.getChildren()) {
                    String str2 = (String) message.child("destination apirport").getValue();
                    String str3 = (String) message.child("source airport").getValue();
                    if(!dtList.contains(str2)) {
                        dtList.add(str2);
                        sourcelist.add(str3);
                    }

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("FireBaseData", "loadPost:onCancelled", databaseError.toException());
            }
        });

        //oncreate()안에 각각 객체로 만들어 변수에 할당
        Bundle bundle1 = new Bundle(1); // 파라미터는 전달할 데이터 개수
        bundle1.putStringArrayList("list", dtList); // key , value


        fragment2 = new Fragment2();
        fragment2.setArguments(bundle1);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment2).commit();
        fg=2;

        save=findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();

                if(fg==2)
                {
                    if(fragment2.start.equals(fragment2.destination))
                    {
                        Toast.makeText(getApplicationContext(), "잘못된 지역입니다..", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        intent.putExtra("start", fragment2.start);
                        intent.putExtra("destination", fragment2.destination);
                        intent.putExtra("dy", fragment2.dy);
                        intent.putExtra("dm", fragment2.dm);
                        intent.putExtra("dd", fragment2.dd);
                        setResult(2, intent);
                        Log.w("cd", "a" + String.valueOf(fragment2.dy)+String.format("%02d",fragment2.dm)+String.format("%02d",fragment2.dd));
                        finish();
                    }
                }
            }
        });
    }
}