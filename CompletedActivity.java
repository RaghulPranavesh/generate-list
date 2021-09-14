package com.example.ncc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewAnimator;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Path;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CompletedActivity extends AppCompatActivity {

    String platoon;
    String name;
    String cadet_status;

    TextView senior_desc, textView;
    EditText senior_name;
    Button status;

    SharedPreferences isLoggedIn;

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        switch(item.getItemId()) {

            case R.id.fillup:

                Intent intent = new Intent(getApplicationContext(), fillFormActivity.class);
                intent.putExtra("platoon", platoon);
                intent.putExtra("name", name);
                startActivity(intent);

                return true;

            case R.id.view:

                Intent viewIntent = new Intent(getApplicationContext(), listActivity.class);
                startActivity(viewIntent);

                return true;

            case R.id.profile:

                Intent profileIntent = new Intent(getApplicationContext(), profileActivity.class);
                profileIntent.putExtra("platoon", platoon);
                profileIntent.putExtra("name", name);
                startActivity(profileIntent);

                return true;

//            case R.id.logout:
//
//                isLoggedIn.edit().putInt("key", 0).apply();
//                Intent logout = new Intent(getApplicationContext(), MainActivity.class);
//                logout.putExtra("name", name);
//                logout.putExtra("platoon", platoon);
//                logout.putExtra("loggedOut", 1);
//                startActivity(logout);
//                finish();
//
//                return true;
        }

        return false;
    }

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed);

//        Intent intent = getIntent();
//        platoon = intent.getStringExtra("platoon");
//        name = intent.getStringExtra("name");

        senior_desc = findViewById(R.id.senior_desc);
        senior_name = findViewById(R.id.senior);
        status = findViewById(R.id.status);
        textView = findViewById(R.id.textView4);

        isLoggedIn = getSharedPreferences("login", Context.MODE_PRIVATE);
        name = isLoggedIn.getString("name", " ");
        platoon = isLoggedIn.getString("platoon", " ");


            database.getReference().child("all_users").child(platoon).child(name).child("status").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    cadet_status = dataSnapshot.getValue().toString();

                    if (!cadet_status.equals("NOT RESPONDED")) {

                        Responded();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

    }
    public void Responded(){

        senior_desc.setVisibility(View.INVISIBLE);
        senior_name.setVisibility(View.INVISIBLE);
        status.setVisibility(View.INVISIBLE);
        if(cadet_status.equals("ERROR")){
            textView.setText("YOU HAVE LOGGED IN AS ANOTHER CADET, PLEASE LOG IN BACK AS YOURSELF!");
        }
        textView.setText("YOU HAVE RESPONDED - " + cadet_status + " - FOR TODAY :)");

        Button completed = findViewById(R.id.completed);
        Button incomplete = findViewById(R.id.not_completed);

        completed.setVisibility(View.INVISIBLE);
        incomplete.setVisibility(View.INVISIBLE);
    }

    public void completed(View view){

        new AlertDialog.Builder(this)
                .setTitle("CONFIRMATION")
                .setMessage("Confirm that you have completed")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                       Intent intent = new Intent(getApplicationContext(), thankyouActivity.class);
                       intent.putExtra("platoon", platoon);
                       intent.putExtra("status", "COMPLETED");
                       intent.putExtra("name", name);
                       startActivity(intent);

                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();
    }

    public void not_completed(View view){

        senior_desc.setVisibility(View.VISIBLE);
        senior_name.setVisibility(View.VISIBLE);
        status.setVisibility(View.VISIBLE);

    }

    public void status(View view){

        String name_of_senior = senior_name.getText().toString();
        name_of_senior = name_of_senior.trim();

        //perform regex of senior names
        Pattern P = Pattern.compile("[A-Za-z]*$");
        Matcher M = P.matcher(name_of_senior);
        Boolean b = M.matches();

        if(name_of_senior.length() == 0){

            Toast.makeText(this, "PLEASE RECEIVE PERMISSION FROM A SENIOR CADET", Toast.LENGTH_LONG).show();
        }
        else{

            //Thank you for updating
            Intent intent = new Intent(getApplicationContext(), thankyouActivity.class);
            intent.putExtra("platoon", platoon);
            intent.putExtra("name", name);
            intent.putExtra("status", "NOT COMPLETED");
            intent.putExtra("senior", name_of_senior);
            startActivity(intent);
        }

    }
}