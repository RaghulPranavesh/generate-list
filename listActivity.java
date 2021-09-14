package com.example.ncc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class listActivity<Engineers> extends AppCompatActivity {

    ArrayList<String> Engineers = new ArrayList<>();
    ArrayList<String> EME = new ArrayList<>();
    ArrayList<String> Signals = new ArrayList<>();

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    TextView list;

    //Engineers List
    ArrayList<String> Engineers_completed = new ArrayList<>();
    ArrayList<String> Engineers_not_completed = new ArrayList<>();
    ArrayList<String> Engineers_seniors = new ArrayList<>();
    ArrayList<String> Engineers_not_responded = new ArrayList<>();

    //Signals List
    ArrayList<String> Signals_completed = new ArrayList<>();
    ArrayList<String> Signals_not_completed = new ArrayList<>();
    ArrayList<String> Signals_seniors = new ArrayList<>();
    ArrayList<String> Signals_not_responded = new ArrayList<>();

    //EME list
    ArrayList<String> EME_completed = new ArrayList<>();
    ArrayList<String> EME_not_completed = new ArrayList<>();
    ArrayList<String> EME_seniors = new ArrayList<>();
    ArrayList<String> EME_not_responded = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Date d = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String date = formatter.format(d);

        list = findViewById(R.id.viewList);

        list.setMovementMethod(new ScrollingMovementMethod());
        list.setText("*Fit India 2.0 Activities " + date + "*" + "\n\n");

        database.getReference().child("all_users").child("ENGINEERS").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                String cadet_status = dataSnapshot.child("status").getValue().toString();

                if (cadet_status.equals("COMPLETED")) {

                    Engineers_completed.add(dataSnapshot.getKey());

                } else if (cadet_status.equals("NOT COMPLETED")) {

                    Engineers_not_completed.add(dataSnapshot.getKey());
                    Engineers_seniors.add(dataSnapshot.child("senior").getValue().toString());  //receive seniors name

                } else if (cadet_status.equals("NOT RESPONDED")) {

                    Engineers_not_responded.add(dataSnapshot.getKey());
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {}
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        database.getReference().child("all_users").child("ENGINEERS").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.append("*ENGINEERS platoon*\n\n");

                int i = 0;
                for (String name : Engineers_completed) {  //add the serial number

                    list.append(++i + ") " + name + '\n');
                }

                list.append("\n");
                list.append("*CADETS WHO RECEIVED PERMISSION*\n\n");

                int j = 0;
                for (String name : Engineers_not_completed) {

                    list.append(j+1 + ") " + name + " - " +  Engineers_seniors.get(j) + '\n');
                    j ++;
                }

                list.append("\n");
                list.append("*CADETS WHO HAVE NOT RESPONDED*\n\n");

                int k = 0;
                for(String name : Engineers_not_responded) {

                    list.append(++k + ") " + name + '\n');
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        database.getReference().child("all_users").child("EME").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                String cadet_status = dataSnapshot.child("status").getValue().toString();

                if(cadet_status.equals("COMPLETED")){

                    EME_completed.add(dataSnapshot.getKey());
                } else if(cadet_status.equals("NOT COMPLETED")) {

                    EME_not_completed.add(dataSnapshot.getKey());
                    EME_seniors.add(dataSnapshot.child("senior").getValue().toString());   //receive seniors name

                } else if(cadet_status.equals("NOT RESPONDED")) {

                    EME_not_responded.add(dataSnapshot.getKey());
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {}
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        database.getReference().child("all_users").child("EME").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.append("*EME platoon* \n\n");

                int i = 0;
                for(String name : EME_completed) {

                    list.append(++i + ") " + name + '\n');
                }

                list.append("\n");
                list.append("*CADETS WHO RECEIVED PERMISSION*\n\n");

                int j = 0;
                for(String name : EME_not_completed){

                    list.append(j+1 + ") " + name + " - " + EME_seniors.get(j) + '\n');
                }

                list.append("\n");
                list.append("*CADETS WHO HAVE NOT RESPONDED*\n\n");

                int k = 0;
                for(String name : EME_not_responded) {

                    list.append(++k + ") " + name + '\n');
                }

                list.append("\n");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        database.getReference().child("all_users").child("SIGNALS").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                String cadet_status = dataSnapshot.child("status").getValue().toString();

                if(cadet_status.equals("COMPLETED")){

                    Signals_completed.add(dataSnapshot.getKey());

                } else if(cadet_status.equals("NOT COMPLETED")) {

                    Signals_not_completed.add(dataSnapshot.getKey());
                    Signals_seniors.add(dataSnapshot.child("senior").getValue().toString());  //recieve seniors name

                } else if(cadet_status.equals("NOT RESPONDED")) {

                    Signals_not_responded.add(dataSnapshot.getKey());
                }

                list.append("\n");

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {}
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        database.getReference().child("all_users").child("SIGNALS").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.append("*SIGNALS platoon* \n\n");

                int i = 0;
                for(String name : Signals_completed) {

                    list.append(++i + ") " + name + '\n');
                }

                list.append("\n");
                list.append("*CADETS WHO RECEIVED PERMISSION* \n\n");

                int j = 0;
                for(String name : Signals_not_completed) {

                    list.append(j+1 + ") " + name + " - " + Signals_seniors.get(j) + '\n');
                }

                list.append("\n");
                list.append("*CADETS WHO HAVE NOT RESPONDED*\n\n");

                int k = 0;
                for(String name : Signals_not_responded) {

                    list.append(++k + ") " + name + '\n');
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void copy(View view){

        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("Copied Text", list.getText().toString());

        clipboardManager.setPrimaryClip(clipData);

        Toast.makeText(getApplicationContext(), "LIST COPIED TO CLIPBOARD", Toast.LENGTH_LONG).show();
    }
}
