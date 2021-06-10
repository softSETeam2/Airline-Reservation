package gachon.seteam2.airlinereservation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class ReserveActivity extends AppCompatActivity {
    public ReserveActivity(){}
    TextView textView3;
    Spinner spinner1,spinner2;
    Button button1;

    private Context context;

    String[] departure={"서울","인천","김포","부산"};
    String[] arrival={"일본","중국","미국","영국","제주도"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve);

        spinner1=(Spinner) findViewById(R.id.spinner1);
        spinner2=(Spinner) findViewById(R.id.spinner2);
        textView3=(TextView)findViewById(R.id.textView3);

        ArrayAdapter<String> adapter1=new ArrayAdapter<>(
                getApplicationContext(),android.R.layout.simple_spinner_item,departure);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView adapterView, View view, int position, long id){
                textView3.setText("경로: "+departure[position]+"-->"+spinner2.getSelectedItem().toString());
            }
            @Override
            public void onNothingSelected(AdapterView adapterView){
                textView3.setText("");
            }
        });

        ArrayAdapter<String> adapter2=new ArrayAdapter<>(
                getApplicationContext(),android.R.layout.simple_spinner_item,arrival);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView parent,View view2, int position,long id){
                textView3.setText("경로: "+" "+spinner1.getSelectedItem().toString()+"-->"+arrival[position]);
            }
            @Override
            public void onNothingSelected(AdapterView adapterView){
                textView3.setText("");
            }
        });

        button1=findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                Intent intent=new Intent(ReserveActivity.this,PayActivity.class); //(getApplicationContext(),NewActivity.class);
                //finish();
                startActivity(intent);
            }
        });
    }
}