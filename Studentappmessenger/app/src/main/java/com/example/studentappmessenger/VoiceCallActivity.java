package com.example.studentappmessenger;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.studentappmessenger.R;

import java.text.SimpleDateFormat;
import java.util.TimeZone;



public class VoiceCallActivity extends AppCompatActivity {
    LinearLayout lv_endcall;
    TextView tv_time;
    long startTime, timeInMilliseconds = 0;
    Handler customHandler = new Handler();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voice_layout);
        lv_endcall = (LinearLayout) findViewById(R.id.lv_endcall);
        tv_time = (TextView) findViewById(R.id.time);

        lv_endcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customHandler.removeCallbacks(updateTimerThread);
                finish();
            }
        });
        startTime = SystemClock.uptimeMillis();
        customHandler.postDelayed(updateTimerThread, 0);
    }

    private Runnable updateTimerThread = new Runnable() {
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            tv_time.setText(getDateFromMillis(timeInMilliseconds));
            customHandler.postDelayed(this, 1000);
        }
    };


        public static String getDateFromMillis(long d) {
            SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
            df.setTimeZone(TimeZone.getTimeZone("GMT"));
            return df.format(d);
        }
    }

  /*  private void timer(int timeInMillis) {
        new CountDownTimer(timeInMillis, 1000) {
            @Override
            public void onTick(long leftTimeInMilliseconds) {
                long seconds = leftTimeInMilliseconds / 1000;

                minute = (seconds % 3600) / 60;
                sec = (seconds % 60);

                timer.setText(String.format("%02d:%02d",
                        (seconds % 3600) / 60, (seconds % 60)));

            }

            @Override
            public void onFinish() {
                endCall();
//                timer.setText("Time up!");
                timer.setVisibility(View.VISIBLE);
            }

        }.start();
    }
*/


