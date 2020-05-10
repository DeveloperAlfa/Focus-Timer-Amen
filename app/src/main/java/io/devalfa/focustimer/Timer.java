package io.devalfa.focustimer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.animation.ObjectAnimator;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Timer extends AppCompatActivity {
    TextView remainingTime;
    NotificationCompat.Builder builder;
    NotificationManagerCompat notificationManager;
    ImageButton cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        long duration = getIntent().getLongExtra("time", 0);
        ProgressBar progressBar = findViewById(R.id.progressBar);
        Long tsLong = System.currentTimeMillis()/1000;
        progressBar.setMax((int) duration/1000);
        ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "progress", 0, (int) duration/1000); // see this max value coming back here, we animate towards that value
        animation.setDuration(duration); // in milliseconds
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();
        remainingTime = findViewById(R.id.remainingTime);
        CountDownTimer myTimer = new CountDownTimer(duration, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long tot_sec = millisUntilFinished/1000;
                long mint = tot_sec / 60;
                long sec = tot_sec % 60;
                String mints = mint+"", secs = sec+"";
                if(mints.length()==1) mints = "0"+mints;
                if(secs.length()==1) secs = "0"+secs;
                remainingTime.setText(mints+":"+secs);

                //intent.putExtra("time", millisUntilFinished+"");
            }

            @Override
            public void onFinish() {
                notificationManager.cancelAll();
                PendingIntent pendingIntent2 = PendingIntent.getActivity(Timer.this, 0, new Intent(Timer.this, MainActivity.class), PendingIntent.FLAG_NO_CREATE);
                builder = new NotificationCompat.Builder(Timer.this, "one")
                        .setSmallIcon(R.drawable.circular)
                        .setContentTitle("Pomodoro Completed")
                        .setContentText("Time to celebrate")
                        .setContentIntent(pendingIntent2)
                        .setOngoing(false)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                notificationManager.notify(1, builder.build());
            }
        };
        myTimer.start();
        cancel = findViewById(R.id.cancelBtn);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Timer.this, MainActivity.class));
                Toast.makeText(Timer.this, "Hope you'll start another one soon!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}