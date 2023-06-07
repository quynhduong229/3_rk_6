package com.example.airplanned.activity;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.airplanned.R;
import com.example.airplanned.api.ApiClientFactory;
import com.example.airplanned.model.Flight;
import com.example.airplanned.model.Lodging;
import com.example.airplanned.model.LodgingType;
import com.example.airplanned.model.Trip;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * This purpose of this activity is to create a trip for user's preference.
 * The users can designing their own trip by typing the information of the trip,
 * hotel and flight based on the text box that we provided.
 * @author Julie Duong
 */

public class AddingTripActivity extends AppCompatActivity {

    private EditText trip_location, trip_duration;
    private EditText flightName, flightPrice, flightDate, flightDeparting, flightArriving;
    private EditText hotelName, hotelPrice, hotelCheckIn, hotelCheckOut, hotelLocation, hotelType;
    private Button  addingtrip_button;
    private ImageView flightView, hotelView;
    int email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_trip);

        trip_location = (EditText) findViewById(R.id.plantrip_location);
        trip_duration = (EditText) findViewById(R.id.length_of_trip);
        addingtrip_button = (Button) findViewById(R.id.addtrip_button);
        flightName = (EditText) findViewById(R.id.addtrip_flight_name);
        flightPrice = (EditText) findViewById(R.id.addtrip_flight_price);
        flightDate = (EditText) findViewById(R.id.addtrip_flight_date);
        flightDeparting = (EditText) findViewById(R.id.addtrip_flight_departing);
        flightArriving = (EditText) findViewById(R.id.addtrip_flight_arriving);

        hotelName = (EditText)findViewById(R.id.addtrip_lodging_name);
        hotelPrice = (EditText)findViewById(R.id.addtrip_lodging_price);
        hotelCheckIn = (EditText)findViewById(R.id.addtrip_lodging_checkIn);
        hotelCheckOut = (EditText)findViewById(R.id.addtrip_lodging_checkOut);
        hotelLocation = (EditText)findViewById(R.id.addtrip_lodging_location);
        hotelType = (EditText)findViewById(R.id.addtrip_lodging_type);


        hotelView = findViewById(R.id.hotelView);
        hotelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddingTripActivity.this, LodgingPageActivity.class));
            }
        });


        flightView = findViewById(R.id.flightView);
        flightView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddingTripActivity.this, FlightPageActivity.class));
            }
        });


        addingtrip_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTripbyUser(createTrip());

            }
        });

    }


    /**
     * This method creates a Trip with Flight object and Hotel object.
     * It lets the user type the information of trip, hotel and flight
     * from the text box provided.
     * @return Trip
     */
    public Trip createTrip() {
        Flight flight = new Flight();
        flight.setAirlineName(flightName.getText().toString());
        flight.setPrice(flightPrice.getInputType());
        flight.setDate(flightDate.getText().toString());
        flight.setDeparting(flightDeparting.getText().toString());
        flight.setArriving(flightArriving.getText().toString());
        flight.setPrice(Double.parseDouble(flightPrice.getText().toString()));
        Lodging hotel = new Lodging();
        hotel.setName(hotelName.getText().toString());
        hotel.setPrice(Double.parseDouble(hotelPrice.getText().toString()));
        hotel.setCheckIn(hotelCheckIn.getText().toString());
        hotel.setCheckOut(hotelCheckOut.getText().toString());
        if(hotelType.getText().toString().equalsIgnoreCase("CABIN")) {
            hotel.setType(LodgingType.CABIN);
        }else if(hotelType.getText().toString().equalsIgnoreCase("HOTEL")) {
            hotel.setType(LodgingType.HOTEL);
        }else if(hotelType.getText().toString().equalsIgnoreCase("BNB")) {
            hotel.setType(LodgingType.BNB);
        }
        hotel.setLocation(hotelLocation.getText().toString());
        Trip trip = new Trip();
        trip.setLocation(trip_location.getText().toString());
        trip.setDuration(trip_duration.getText().toString());
        trip.setLodging(hotel);
        trip.setFlight(flight);
        return trip;
    }


    /**
     * This method is to save the trip which was created from createTrip() method.
     * The trip is saved by user.
     * Each user will have his/her own trip.
     * @param trip
     */
    public void saveTripbyUser(Trip trip) {
        email = getIntent().getIntExtra("emailid", 0);
        System.out.print(email);
        Call<Trip> call = ApiClientFactory.GetTripApi().saveTripbyUser(trip, email);
        call.enqueue(new Callback<Trip>() {
            @Override
            public void onResponse(Call<Trip> call, Response<Trip> response) {
                if(response.isSuccessful()) {
                    Toast.makeText(AddingTripActivity.this, "A new trip added successfully!", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(AddingTripActivity.this, TripPlannedList.class).putExtra("emailid", email));

                }
            }
            @Override
            public void onFailure(Call<Trip> call, Throwable t) {

            }
        });
    }

}
