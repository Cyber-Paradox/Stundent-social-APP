package com.example.studentappmessenger;

import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.studentappmessenger.R;

import javax.swing.text.html.ImageView;


public class RecievedCall extends AppCompatActivity implements View.OnClickListener {

    ImageView attendCall, endCall;
    private Ringtone r;
    private Vibrator myVib;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recievedcall);

        playRingtone();

        attendCall = (ImageView) findViewById(R.id.attend_call);
        endCall = (ImageView) findViewById(R.id.end_call);
        attendCall.setOnClickListener(this);
        endCall.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.attend_call:
                if (r.isPlaying()) {
                    r.stop();
                    myVib.cancel();
                }
                Intent intent1 = new Intent(this, VoiceCallActivity.class);
                startActivity(intent1);
                finish();
                break;

            case R.id.end_call:
                if (r.isPlaying()) {
                    r.stop();
                    myVib.cancel();
                }

                finish();
                break;
        }
    }

    private Ringtone playRingtone() {
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);

        r = RingtoneManager.getRingtone(getApplicationContext(), notification);

        myVib = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);
        myVib.vibrate(40000);

        r.play();
        return r;
    }
}
