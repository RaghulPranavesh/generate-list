package com.example.ncc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class Executable extends BroadcastReceiver {

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    public void onReceive(Context context, Intent intent) {

        SharedPreferences isLoggedIn = context.getSharedPreferences("login", Context.MODE_PRIVATE);

        String name = isLoggedIn.getString("name", " ");
        String platoon = isLoggedIn.getString("platoon", " ");

        if(name != " " || platoon != " ") {

            database.getReference().child("all_users").child(platoon).child(name).child("status").setValue("NOT RESPONDED");

            if (database.getReference().child("all_users").child(platoon).child(name).child("senior") != null) {

                database.getReference().child("all_users").child(platoon).child(name).child("senior").removeValue();
            }

            Log.i("Task", "DONE");
        }

    }
}
