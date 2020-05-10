package io.devalfa.focustimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ChooseTimer extends AppCompatActivity {
    LinearLayout timer_25, timer_45, timer_60;
    TextView userName;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_timer);
        timer_25 = findViewById(R.id.timer_25);
        timer_45 = findViewById(R.id.timer_45);
        timer_60 = findViewById(R.id.timer_60);
        userName = findViewById(R.id.userName);
        String uncut_name = getIntent().getStringExtra("userName");
        if(uncut_name==null) uncut_name = "User";
        StringBuilder name = new StringBuilder();
        for(int i = 0; i < uncut_name.length(); i++)
        {
            if(uncut_name.charAt(i)==' ') break;
            else name.append(uncut_name.charAt(i));
        }
        userName.setText("Hi "+name+"!");
        intent = new Intent(ChooseTimer.this, Timer.class);
        timer_25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long val = 25*60*1000;
                intent.putExtra("time", val);
                startActivity(intent);
                finish();
            }
        });
        timer_45.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long val = 45*60*1000;
                intent.putExtra("time", val);
                startActivity(intent);
                finish();
            }
        });
        timer_60.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long val = 60*60*1000;
                intent.putExtra("time", val);
                startActivity(intent);
                finish();
            }
        });
    }
}
