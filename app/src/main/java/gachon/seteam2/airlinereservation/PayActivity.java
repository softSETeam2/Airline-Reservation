package gachon.seteam2.airlinereservation;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class PayActivity extends AppCompatActivity {
    public static int cnt=1,costvalue=0,costvaluetemp=0,totalcost=0;
    private FirebaseAuth firebaseAuth;

    TextView cost;
    RadioButton seat1,seat2,seat3,location1,location2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        Intent get = getIntent();
        String airline = get.getExtras().getString("airline");
        String source = get.getExtras().getString("source");
        String destination = get.getExtras().getString("destination");
        String date = get.getExtras().getString("date");
        String arrivalTime = get.getExtras().getString("arrivalTime");
        String departureTime = get.getExtras().getString("departureTime");

        //액션 바 등록하기
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("좌석 선택");

        actionBar.setDisplayHomeAsUpEnabled(true); //뒤로가기버튼
        actionBar.setDisplayShowHomeEnabled(true); //홈 아이콘
        
        TextView cntcost=(TextView)findViewById(R.id.cntcost);
        //RadioGroup-좌석등급
        RadioGroup seatLevel=(RadioGroup)findViewById((R.id.seatLevel));
        ImageView imageView=findViewById(R.id.imageView);
        seatLevel.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.seat1){
                    costvalue=0;
                    costvalue=costvalue+1200000;
                    imageView.setImageResource(R.drawable.first);
                }
                else if(checkedId==R.id.seat2){
                    costvalue=0;
                    costvalue=costvalue+400000;
                    imageView.setImageResource(R.drawable.business);
                }
                else if(checkedId==R.id.seat3){
                    costvalue=0;
                    costvalue=costvalue+100000;
                    imageView.setImageResource(R.drawable.economy);
                }
                totalcost=costvalue+costvaluetemp;
                cntcost.setText(""+totalcost);
            }
        });

        //좌석위치
        RadioGroup seatLocation=(RadioGroup)findViewById((R.id.seatLocation));
        seatLocation.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.location1){costvaluetemp=0;}
                else if(checkedId==R.id.location2){costvaluetemp=0;}
                else if(checkedId==R.id.location3){
                    costvaluetemp=0;
                    costvaluetemp=costvaluetemp+100000;
                }
                totalcost=costvalue+costvaluetemp;
                cntcost.setText(""+totalcost);
            }
        });

        //count people
        TextView cntpeople=(TextView)findViewById(R.id.cntpeople);
        Button minusbutton=(Button)findViewById(R.id.minusbutton);
        Button plusbutton=(Button)findViewById(R.id.plusbutton);
        plusbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                cntpeople.setText(""+(++cnt));
                if(cnt!=1){minusbutton.setEnabled(true);}
                cntcost.setText(""+(totalcost*cnt));
            }
        });
        minusbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                if(cnt==1){minusbutton.setEnabled(false);}
                else if(cnt!=0){
                    minusbutton.setEnabled(true);
                    cntpeople.setText(""+(--cnt));
                }
                cntcost.setText(""+(totalcost*cnt));
            }
        });
        ////////

        Button btnPay=(Button)findViewById(R.id.btnPay);
        btnPay.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                int id=seatLevel.getCheckedRadioButtonId();
                RadioButton rb=(RadioButton) findViewById(id);
                String level=rb.getText().toString();

                int id2=seatLocation.getCheckedRadioButtonId();
                RadioButton rb2=(RadioButton) findViewById(id2);
                String level2=rb2.getText().toString();

                String cnt_toString=String.valueOf(cnt);
                String finaltotal=String.valueOf(totalcost*cnt);


                Intent intent = new Intent(getApplicationContext(), CheckActivity.class);
                intent.putExtra("airline", airline);
                intent.putExtra("source", source);
                intent.putExtra("destination", destination);
                intent.putExtra("date", date);
                intent.putExtra("arrivalTime", arrivalTime);
                intent.putExtra("departureTime", departureTime);
                intent.putExtra("Seat", level);
                intent.putExtra("Location", level2);
                intent.putExtra("Number", cnt_toString);
                intent.putExtra("Total", finaltotal);

                setResult(Activity.RESULT_OK, intent);
                startActivity(intent);
                finish();
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