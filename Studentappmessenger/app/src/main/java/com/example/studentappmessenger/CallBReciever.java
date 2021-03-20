package com.example.studentappmessenger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.studentappmessenger.RecievedCall;



public class CallBReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, RecievedCall.class);
        context.startActivity(i);

    }
}