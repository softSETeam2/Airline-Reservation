package gachon.seteam2.airlinereservation;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class Fragment2 extends Fragment {
    Button Departure_day;
    Button Arrival_Date;
    int y=0, m=0, d=0;
    int dy=0, dm=0, dd=0;
    String start;
    String destination;
    private ArrayList<String>list=new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        list=getArguments().getStringArrayList("list");
        // Inflate the layout for this fragment
        ViewGroup rootView= (ViewGroup)inflater.inflate(R.layout.fragment_2, container, false);

        Spinner spinner1 = rootView.findViewById(R.id.spinner1);
        Spinner spinner2 = rootView.findViewById(R.id.spinner2);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getActivity(), android.R.layout.simple_spinner_item,list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        spinner2.setAdapter(adapter);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), list.get(position) +"(이)가 선택되었습니다", Toast.LENGTH_SHORT).show();
                start=list.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(),list.get(position)+"(이)가 선택되었습니다", Toast.LENGTH_SHORT).show();
                destination=list.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Departure_day=rootView.findViewById(R.id.Departure_day);
        Departure_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDate(Departure_day);
            }
        });

        return rootView;
    }
    void showDate(Button Departure_day) {
        Calendar cal=Calendar.getInstance();
        int year=cal.get(Calendar.YEAR);
        int month=cal.get(Calendar.MONTH);
        int date=cal.get(Calendar.DATE);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                dy = year;
                dm = month+1;
                dd = dayOfMonth;
                Departure_day.setText(dy+"년"+dm+"월"+dd+"일");

            }
        }, year,month,date);

        datePickerDialog.setMessage("메시지");
        datePickerDialog.show();
    }

}