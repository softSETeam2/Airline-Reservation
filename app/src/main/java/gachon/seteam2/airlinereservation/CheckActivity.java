package gachon.seteam2.airlinereservation;
//무엇을 선택했는지 보여주는 액티비티
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class CheckActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        Intent intent=getIntent();


        TextView txt1=(TextView) findViewById(R.id.txt1);
        String Seat=intent.getStringExtra("Seat");
        txt1.setText(Seat);

        TextView txt2=(TextView) findViewById(R.id.txt2);
        String Location=intent.getStringExtra("Location");
        txt2.setText(Location);

        TextView txt3=(TextView) findViewById(R.id.txt3);
        String number=intent.getStringExtra("Number");
        txt3.setText(number);

        TextView txt4=(TextView) findViewById(R.id.txt4);
        String total=intent.getStringExtra("Total");
        txt4.setText(total);


    }
}