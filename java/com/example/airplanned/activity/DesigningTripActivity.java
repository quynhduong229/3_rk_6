package com.example.airplanned.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.airplanned.R;
import com.example.airplanned.api.ApiClientFactory;
import com.example.airplanned.model.Trip;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * This purpose of this activity is to create a trip that let the user get
 * hotel and flight from API. The users can save their own trip from the real-world
 * flights and hotels that they search for.
 * @author Julie Duong
 */

public class DesigningTripActivity extends AppCompatActivity {

    Button addButton;
    EditText location, length;
    int emailIDdesign;
    int TripIDDesign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_designing_trip);

        addButton = findViewById(R.id.addtrip_design);
        location = findViewById(R.id.designtrip_location);
        length= findViewById(R.id.designlength_of_trip);

        emailIDdesign = getIntent().getIntExtra("emailid", 0);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTripbyUser(createTrip());
            }
        });
    }

    /**
     * This method create a trip with location and duration.
     * @return
     */
    public Trip createTrip() {
        Trip trip = new Trip();
        trip.setLocation(location.getText().toString());
        trip.setDuration(length.getText().toString());
        return trip;
    }

    /**
     * This method is to save the trip which was created from createTrip() method.
     * The trip is saved by user.
     * Each user will have his/her own trip.
     * After successfully creating a trip, it will let the users go to flight and hotel page
     * in order to save flight and hotel from real-world information.
     * @param trip
     */
    public void saveTripbyUser(Trip trip) {
        emailIDdesign = getIntent().getIntExtra("emailid", 0);
        Call<Trip> call = ApiClientFactory.GetTripApi().saveTripbyUser(trip, emailIDdesign);
        call.enqueue(new Callback<Trip>() {
            @Override
            public void onResponse(Call<Trip> call, Response<Trip> response) {
                if(response.isSuccessful()) {
                    Trip trip2 = response.body();
                    TripIDDesign = trip2.getId();
                    Toast.makeText(DesigningTripActivity.this, "A new trip added successfully!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(DesigningTripActivity.this, FlightPageActivity.class);
                    intent.putExtra("tripId", TripIDDesign);
                    intent.putExtra("emailid", emailIDdesign);
                    startActivity(intent);
                }
            }
            @Override
            public void onFailure(Call<Trip> call, Throwable t) {

            }
        });
    }
}
