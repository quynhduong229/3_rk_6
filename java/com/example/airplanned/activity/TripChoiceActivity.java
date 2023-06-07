package com.example.airplanned.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.airplanned.R;

/**
 * This class shows two option for the user to create their trip
 * The first option is to design their own trip
 * The second option is to save trip from real-world flight and hotel.
 */
public class TripChoiceActivity extends AppCompatActivity {
    TextView ownTrip;
    TextView tripfromAPI;
    ImageView goback;
    int userIDchoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_choice);

        goback = findViewById(R.id.gobackList);
        ownTrip = findViewById(R.id.designTrip);
        tripfromAPI = findViewById(R.id.autoTrip);


        userIDchoice = getIntent().getIntExtra("emailid", 0);

        ownTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(new Intent(TripChoiceActivity.this, AddingTripActivity.class));
                intent.putExtra("emailid", userIDchoice);
                startActivity(intent);
            }
        });

        tripfromAPI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(new Intent(TripChoiceActivity.this, DesigningTripActivity.class));
                intent.putExtra("emailid", userIDchoice);
                startActivity(intent);
            }
        });

        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(new Intent(TripChoiceActivity.this, TripPlannedList.class));
                intent.putExtra("emailid", userIDchoice);
                startActivity(intent);
            }
        });
    }
}
