package gachon.seteam2.airlinereservation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class SearchingReservationActivity extends AppCompatActivity {

    private EditText date;
    private Button dateButton;
    String[] SourceAirportData = {"무안   |   0", "광주   |   1", "군산   |   2", "여수   |   3", "원주   |   4", "양양   |   5", "제주   |   6", "김해   |   7", "사천   |   8", "울산   |   9", "인천   |   10", "김포   |   11", "포항   |   12", "대구   |   13", "청주   |   14" };
    String[] DestinationAirportData = {"무안   |   0", "광주   |   1", "군산   |   2", "여수   |   3", "원주   |   4", "양양   |   5", "제주   |   6", "김해   |   7", "사천   |   8", "울산   |   9", "인천   |   10", "김포   |   11", "포항   |   12", "대구   |   13", "청주   |   14" };
    String[] Hour = {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24"};
    String[] Minute = {"00", "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching_reservation);

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
}