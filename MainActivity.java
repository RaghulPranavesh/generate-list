package com.example.ncc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.spec.ECField;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    EditText name, platoon;

    SharedPreferences isLoggedIn;

    AlarmManager alarmMgr;
    Intent intent;
    PendingIntent pendingIntent;

    String cadet_name, cadet_platoon;

    Integer loggedOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        name = findViewById(R.id.Name);
        platoon = findViewById(R.id.Platoon);

        loggedOut = 0;

        Intent getIntent = getIntent();
        String received_name = getIntent.getStringExtra("name");
        String received_platoon = getIntent.getStringExtra("platoon");
        loggedOut = getIntent.getIntExtra("loggedOut", 0);

        isLoggedIn = getSharedPreferences("login", Context.MODE_PRIVATE);


        if(getSharedPreferences() > 0){

            Intent intent = new Intent(getApplicationContext(), CompletedActivity.class);
            intent.putExtra("name", received_name);
            intent.putExtra("platoon", received_platoon);
            startActivity(intent);
        }


        intent = new Intent(getApplicationContext(), Executable.class);
        intent.putExtra("name", cadet_name);
        intent.putExtra("platoon", cadet_platoon);


        Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);

        if(PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_NO_CREATE) == null){

            pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent,0);
            alarmMgr = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);

            try {
                alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
            } catch (Exception e){
                e.printStackTrace();
            }

        }

    }


    public int getSharedPreferences(){

        return isLoggedIn.getInt("key", 0);
    }

    public void Register(View view){

        cadet_name = name.getText().toString();
        cadet_platoon = platoon.getText().toString();

        isLoggedIn.edit().putInt("key", 1).apply();

        //perform Regex
        Pattern P = Pattern.compile("^[^<>%$.#]*$");
        Matcher M = P.matcher(cadet_name);
        Boolean b = M.matches();

        Boolean check = cadet_platoon.equals("ENGINEERS") || cadet_platoon.equals("SIGNALS") || cadet_platoon.equals("EME");

        if(!check) {

            Toast.makeText(this, "ENTER 'ENGINEERS'/'SIGNALS'/'EME'", Toast.LENGTH_LONG).show();
        }

        if(!b) {

            Toast.makeText(this, "DO NOT INCLUDE '.' '$' '#' '[' ']' IN NAME",Toast.LENGTH_LONG).show();
        }

        if(check && b) {

            //add the user to database
            if(loggedOut == 0) {

                database.getReference().child("all_users").child(cadet_platoon).child(cadet_name).child("Cadet").setValue(cadet_name);
                database.getReference().child("all_users").child(cadet_platoon).child(cadet_name).child("status").setValue("NOT RESPONDED");

                Log.i("DATA", "Done in page 1");
            }

            //move to next activity
            Intent intent;
            intent = new Intent(getApplicationContext(), CompletedActivity.class);
//            intent.putExtra("platoon", cadet_platoon);
//            intent.putExtra("name", cadet_name);
            isLoggedIn.edit().putString("name", cadet_name).apply();
            isLoggedIn.edit().putString("platoon", cadet_platoon).apply();
            startActivity(intent);
        }

    }
}