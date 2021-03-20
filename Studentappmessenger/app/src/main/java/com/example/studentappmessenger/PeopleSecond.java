package com.example.studentappmessenger;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.telephony.SmsManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentappmessenger.R;

import com.example.studentappmessenger.GPSTracker;
import com.example.studentappmessenger.SessionManager;

import java.util.HashMap;


public class PeopleSecond extends AppCompatActivity {

    ImageButton backButton;


    SmsManager sm;
    TextView tv1,tv2,tv3,tv4;
    ImageButton edit;


    // Session Manager Class
    SessionManager session;


    public static final String DEFAULT="N/A";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.people_second);


        Toolbar tb = (Toolbar) findViewById(R.id.toolbar1);
        backButton = (ImageButton) findViewById(R.id.back);
        // title = (TextView) findViewById(R.id.toolbar_title);

        //  title.setText(R.string.welcome);
        backButton.setVisibility(View.VISIBLE);
        backButton.setImageResource(R.drawable.ic_keyboard_arrow_left_24dp);
//    notificationIcon.setImageResource(R.drawable.notification);
//    notificationIcon.setVisibility(View.VISIBLE);

        setSupportActionBar(tb);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.title);
        //  getSupportActionBar().setTitle("Help");
        TextView tv= (TextView) findViewById(R.id.ab);
        tv.setText(R.string.savedcontacts);
        // Session Manager
        session = new SessionManager(getApplicationContext());

        // Email, Password input text
        tv1 = (TextView) findViewById(R.id.textview1);
        tv2 = (TextView) findViewById(R.id.textview2);
        tv3 = (TextView) findViewById(R.id.textview3);
        //Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();


        // Login button
        edit = (ImageButton) findViewById(R.id.edit);
        session.checkStatusOfContactsAdd();

        HashMap<String, String> user = session.getUserDetails();

        String number1 = user.get(SessionManager.CONTACT1);
        // name
        String number2 = user.get(SessionManager.CONTACT2);

        // email
        String number3 = user.get(SessionManager.CONTACT3);

        // displaying user data
        tv1.setText(number1);
        tv2.setText(number2);
        tv3.setText(number3);


        // Login button click event
        edit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                session.logoutUser();

            }
        });



        //Location
        GPSTracker mGPS = new GPSTracker(this);

        tv4 = (TextView) findViewById(R.id.texts);
        if(mGPS.canGetLocation ){
            mGPS.getLocation();
            tv4.setText(  mGPS.getLatitude() + "," + mGPS.getLongitude());

        }else{
            tv4.setText("Unabletofind");
//            System.out.println("Unable");
        }



        tv1= (TextView) findViewById(R.id.textview1);
        tv2= (TextView) findViewById(R.id.textview2);
        tv3= (TextView) findViewById(R.id.textview3);



        sm=SmsManager.getDefault();



    }



    public void send(View view){

        sm.sendTextMessage(tv1.getText().toString(),null,"I'm in danger..My current location is http://maps.google.com/?q="+tv4.getText().toString(),null,null);
        sm.sendTextMessage(tv2.getText().toString(),null,"I'm in danger..My current location is http://maps.google.com/?q="+tv4.getText().toString(),null,null);
        sm.sendTextMessage(tv3.getText().toString(),null,"I'm in danger..My current location is http://maps.google.com/?q="+tv4.getText().toString(),null,null);

        Toast.makeText(this,"message sent",Toast.LENGTH_LONG).show();

    }

}

