package com.example.airplanned.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.airplanned.R;
import com.example.airplanned.adapter.FlightAdapter;
import com.example.airplanned.adapter.HotelAdapter;
import com.example.airplanned.api.ApiClientFactory;
import com.example.airplanned.model.Flight;
import com.example.airplanned.model.Lodging;
import com.example.airplanned.model.LodgingType;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * The purpose of this activity is to search for a real-world hotel based on
 * check in date and check out date and arrive lcoation. So the user can search
 * for a specific real hotel and add it to their trip.
 *
 * @author Julie Duong
 */

public class LodgingPageActivity extends AppCompatActivity implements HotelAdapter.RecycleViewClickListener {
    RecyclerView hotelrecyclerView;
    EditText fromDate, toDate, Location;
    Button findHotel;
    ImageView backButton, doneCheck;
    HotelAdapter hotelAdapter;
    List<Lodging> hotels;
    private int userid;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lodging_page);

        backButton = findViewById(R.id.goingBack);
        doneCheck = findViewById(R.id.doneCheck);
        fromDate = findViewById(R.id.fromDate);
        toDate = findViewById(R.id.toDate);
        Location = findViewById(R.id.Locationhotel);
        hotelrecyclerView = findViewById(R.id.hotelRecycleView);
        hotelrecyclerView.setLayoutManager(new LinearLayoutManager(this));
        hotelrecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        findHotel = findViewById(R.id.findhotelButton);
        hotelAdapter = new HotelAdapter(hotels, this);

        id = getIntent().getIntExtra("tripId", 0);
        userid = getIntent().getIntExtra("emailid", 0);

        doneCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LodgingPageActivity.this, TripPlannedList.class).putExtra("emailid", userid));
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LodgingPageActivity.this, HomePageActivity.class).putExtra("emailid", userid));
            }
        });

        findHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findHotel();
            }
        });

    }


    /**
     * This method searches for a real hotel based on
     * @location any location
     * @checkinDate in form of YYYY-MM-DD
     * @checkoutDate in form of YYYY-MM-DD
     *
     */
    public void findHotel() {
        Call<List<Lodging>> call = ApiClientFactory.GetLogdingApi().getRealFLights(Location.getText().toString(), fromDate.getText().toString(), toDate.getText().toString());
        call.enqueue(new Callback<List<Lodging>>() {
            @Override
            public void onResponse(Call<List<Lodging>> call, Response<List<Lodging>> response) {
                hotels = response.body();
                hotelAdapter.setData(hotels);
                hotelrecyclerView.setAdapter(hotelAdapter);

            }
            @Override
            public void onFailure(Call<List<Lodging>> call, Throwable t) {

            }
        });
    }

    /**
     * This method is implemented from HotelAdapter class.
     * The purpose is to able to click on specific hotel on recycle view and save it to the trip.
     * @param position
     */
    @Override
    public void OnClick(int position) {
        Dialog dialog = new Dialog(LodgingPageActivity.this);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.hotelonclcik);
        Button save = dialog.findViewById(R.id.sureButtonHotel);
        dialog.show();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Lodging lodging = new Lodging();
                lodging.setName(hotels.get(position).getName());
                lodging.setPrice(Math.ceil(hotels.get(position).getPrice()));
                lodging.setCheckIn(hotels.get(position).getCheckIn());
                lodging.setCheckOut(hotels.get(position).getCheckOut());
                lodging.setLocation(hotels.get(position).getLocation());
                lodging.setType(LodgingType.HOTEL);

                Call<Lodging> call = ApiClientFactory.GetLogdingApi().saveLodging(lodging);
                call.enqueue(new Callback<Lodging>() {
                    @Override
                    public void onResponse(Call<Lodging> call, Response<Lodging> response) {
                        if (response.isSuccessful()) {
                            Lodging lodging1 = response.body();
                            int hotelID = lodging1.getId();
                            Call<Integer> call2 = ApiClientFactory.GetTripApi().putHotel(id, hotelID);
                            call2.enqueue(new Callback<Integer>() {
                                @Override
                                public void onResponse(Call<Integer> call, Response<Integer> response) {
                                    if (response.isSuccessful()) {
                                        Toast.makeText(LodgingPageActivity.this, "A new hotel added successfully!", Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Integer> call, Throwable t) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<Lodging> call, Throwable t) {

                    }
                });

            }

        });
    }
}