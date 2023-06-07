package com.example.airplanned.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.airplanned.R;
import com.example.airplanned.adapter.FlightAdapter;
import com.example.airplanned.api.ApiClientFactory;
import com.example.airplanned.model.Flight;
import com.example.airplanned.model.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * The purpose of this activity is to display the flights in a searchable text view
 * so the user can search for a specific real flight and add it to their trip
 * @author Julie Duong
 */
public class FlightPageActivity extends AppCompatActivity implements FlightAdapter.RecycleViewClickListener {

    RecyclerView flightrecyclerView;
    EditText fromLocation, toLocation, date;
    Button findFlight;
    ImageView backButton;
    FlightAdapter.RecycleViewClickListener clickListener;
    List<Flight> flights;
    FlightAdapter flightAdapter;
    private int userID;
    private int tripUserid;
    private int flightID;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_page);
        backButton = findViewById(R.id.goBack);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FlightPageActivity.this, HomePageActivity.class).putExtra("emailid", userID));
            }
        });


        userID = getIntent().getIntExtra("emailid", 0);
        tripUserid = getIntent().getIntExtra("tripId", 0);

        fromLocation = findViewById(R.id.fromText);
        toLocation = findViewById(R.id.toText);
        date = findViewById(R.id.dateText);
        findFlight = findViewById(R.id.findFlightButton);
        flightrecyclerView = findViewById(R.id.flightRecycleView);
        flightrecyclerView.setLayoutManager(new LinearLayoutManager(this));
        flightrecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        flightAdapter = new FlightAdapter(flights, this);
        findFlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findingFlight();
            }
        });


    }


    public void findingFlight() {
        Call<List<Flight>> call = ApiClientFactory.GetFlightApi().getRealFLights(fromLocation.getText().toString(), toLocation.getText().toString(), date.getText().toString());
        call.enqueue(new Callback<List<Flight>>() {
            @Override
            public void onResponse(Call<List<Flight>> call, Response<List<Flight>> response) {
                flights = response.body();
                flightAdapter.setData(flights);
                flightrecyclerView.setAdapter(flightAdapter);
            }
            @Override
            public void onFailure(Call<List<Flight>> call, Throwable t) {

            }
        });
    }

    @Override
    public void OnClick(int position) {
        Dialog dialog = new Dialog(FlightPageActivity.this);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.activity_flightonclick);
        Button save = dialog.findViewById(R.id.sureButton);
        dialog.show();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Flight flight = new Flight();
                flight.setAirlineName(flights.get(position).getAirlineName());
                flight.setPrice(Math.ceil(flights.get(position).getPrice()));
                flight.setDeparting(flights.get(position).getDeparting());
                flight.setArriving(flights.get(position).getArriving());
                flight.setDate(flights.get(position).getDate());

                Call<Flight> call = ApiClientFactory.GetFlightApi().saveFlight(flight);
                call.enqueue(new Callback<Flight>() {
                    @Override
                    public void onResponse(Call<Flight> call, Response<Flight> response) {
                        Flight flight2 = response.body();
                        flightID = flight2.getId();
                        Call<Integer> call2 = ApiClientFactory.GetTripApi().putFlight(tripUserid, flightID);
                        call2.enqueue(new Callback<Integer>() {
                            @Override
                            public void onResponse(Call<Integer> call, Response<Integer> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(FlightPageActivity.this, "A new flight added successfully!", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(FlightPageActivity.this, LodgingPageActivity.class);
                                    intent.putExtra("tripId", tripUserid);
                                    intent.putExtra("emailid", userID);
                                    startActivity(intent);
                                }
                            }

                            @Override
                            public void onFailure(Call<Integer> call, Throwable t) {

                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<Flight> call, Throwable t) {

                    }
                });

            }

        });

    }
}
