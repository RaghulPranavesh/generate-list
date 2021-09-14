package com.example.ncc;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

public class AlarmHandler {

    private Context context;

    public AlarmHandler(Context context) {
        this.context = context;
    }

    //set Alarm
    public void setAlarm() {

        Intent intent = new Intent(context, Executable.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 2, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Calendar calender;

        if(alarmManager != null) {

        }
    }

    //cancel Alarm
    public void cancelAlarm() {

    }
}
