package com.example.ncc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.FirebaseDatabase;

public class thankyouActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    String platoon, name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thankyou);

        Intent intent = getIntent();
        platoon = intent.getStringExtra("platoon");
        String status = intent.getStringExtra("status");
        String senior = intent.getStringExtra("senior");
        name = intent.getStringExtra("name");

        if(status.equals("COMPLETED")){
            database.getReference().child("all_users").child(platoon).child(name).child("status").setValue(status);
        } else {
            database.getReference().child("all_users").child(platoon).child(name).child("status").setValue(status);
            database.getReference().child("all_users").child(platoon).child(name).child("senior").setValue(senior);
        }
    }

    public void count(View view){

        Intent copyIntent = new Intent(getApplicationContext(), listActivity.class);
        copyIntent.putExtra("platoon", platoon);
        copyIntent.putExtra("name", name);
        startActivity(copyIntent);
    }
}