package com.example.airplanned.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;


import com.example.airplanned.R;
import com.example.airplanned.api.ApiClientFactory;
import com.example.airplanned.model.Flight;
import com.example.airplanned.model.Lodging;
import com.example.airplanned.model.LodgingType;
import com.example.airplanned.model.Trip;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * This class is to help showing the trip list in recycle view.
 * @author Julie Duong
 */

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.TripAdapterVH> {

    private List<Trip> tripResponseList;
    private Context context;
    public TripAdapter() {

    }

    public void setData(List<Trip> tripResponseList) {
        this.tripResponseList = tripResponseList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TripAdapter.TripAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new TripAdapter.TripAdapterVH(LayoutInflater.from(context).inflate(R.layout.row_trips,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull TripAdapterVH holder, int position) {
        Trip tripResponse = tripResponseList.get(position);
        String tripName = tripResponse.getLocation();
        holder.tripName.setText(tripName);
        String tripDuration = tripResponse.getDuration();
        holder.tripDuration.setText(tripDuration);
        String tripFlightName = tripResponse.getFlight().getAirlineName();
        holder.tripFlightName.setText(tripFlightName);
        String flightPrice = String.valueOf(tripResponse.getFlight().getPrice());
        holder.tripFlightPrice.setText(flightPrice);
        String flightDate = tripResponse.getFlight().getDate();
        holder.tripFlightDay.setText(flightDate);
        String flightStart = "From: " + "\n" + tripResponse.getFlight().getDeparting();
        holder.tripFlightstartpoint.setText(flightStart);
        String flightStop = "To: " + "\n" + tripResponse.getFlight().getArriving();
        holder.tripFlightendpoint.setText(flightStop);
        String hotelName = tripResponse.getLodging().getName();
        holder.hotelName.setText(hotelName);
        String hotelPrice = String.valueOf(tripResponse.getLodging().getPrice());
        holder.hotelPrice.setText(hotelPrice);
        LodgingType hotelType = tripResponse.getLodging().getType();
        if(hotelType == LodgingType.HOTEL) {
            holder.hotelType.setText("HOTEL");
        }
        if(hotelType == LodgingType.CABIN) {
            holder.hotelType.setText("CABIN");
        }
        if(hotelType == LodgingType.BNB) {
            holder.hotelType.setText("BNB");
        }

        String hotelLocation = tripResponse.getLodging().getLocation();
        holder.hotelLocation.setText(hotelLocation);
        String hotelStart = "From: " + "\n" + tripResponse.getLodging().getCheckIn();
        holder.hotelStartday.setText(hotelStart);
        String hotelEnd = "To: " + "\n" + tripResponse.getLodging().getCheckOut();
        holder.hotelEndday.setText(hotelEnd);
        int idTrip = tripResponse.getId();
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteTrip(idTrip);
                tripResponseList.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
            }
        });


        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogPlus dialog = DialogPlus.newDialog(context)
                        .setContentHolder(new ViewHolder(R.layout.activity_update_trip))
                        .create();

                View view = dialog.getHolderView();
                EditText tripName = view.findViewById(R.id.trip_name);
                EditText tripDuration = view.findViewById(R.id.trip_duration);
                EditText flightName = view.findViewById(R.id.trip_flightName);
                EditText flightPrice = view.findViewById(R.id.flight_price);
                EditText flightDate = view.findViewById(R.id.tripDate);
                EditText flightStart = view.findViewById(R.id.trip_start);
                EditText flightEnd = view.findViewById(R.id.trip_end);
                EditText hotelName = view.findViewById(R.id.trip_hotelName);
                EditText hotelPrice = view.findViewById(R.id.hotel_price);
                EditText hotelLocation = view.findViewById(R.id.hotel_location);
                EditText hotelStart = view.findViewById(R.id.hotel_start);
                EditText hotelEnd = view.findViewById(R.id.hotel_end);
                EditText hotelType = view.findViewById(R.id.hotel_type);

                Button updateButton = view.findViewById(R.id.updateButton);

                tripName.setText(tripResponse.getLocation());
                tripDuration.setText(tripResponse.getDuration());
                flightName.setText(tripResponse.getFlight().getAirlineName());
                flightPrice.setText(String.valueOf(Math.ceil(tripResponse.getFlight().getPrice())));
                flightDate.setText(tripResponse.getFlight().getDate());
                flightStart.setText(tripResponse.getFlight().getDeparting());
                flightEnd.setText(tripResponse.getFlight().getArriving());
                hotelName.setText(tripResponse.getLodging().getName());
                hotelPrice.setText(String.valueOf(Math.ceil(tripResponse.getLodging().getPrice())));
                hotelLocation.setText(tripResponse.getLodging().getLocation());
                hotelStart.setText(tripResponse.getLodging().getCheckIn());
                hotelEnd.setText(tripResponse.getLodging().getCheckOut());
                hotelType.setText(tripResponse.getLodging().getType().toString());
                dialog.show();
                updateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Flight flight = new Flight();
                        flight.setAirlineName(flightName.getText().toString());
                        flight.setPrice(flightPrice.getInputType());
                        flight.setDate(flightDate.getText().toString());
                        flight.setDeparting(flightStart.getText().toString());
                        flight.setArriving(flightEnd.getText().toString());
                        flight.setPrice(Double.parseDouble(flightPrice.getText().toString()));
                        Lodging hotel = new Lodging();
                        hotel.setName(hotelName.getText().toString());
                        hotel.setPrice(Double.parseDouble(hotelPrice.getText().toString()));
                        hotel.setCheckIn(hotelStart.getText().toString());
                        hotel.setCheckOut(hotelEnd.getText().toString());
                        if(hotelType.getText().toString().equalsIgnoreCase("CABIN")) {
                            hotel.setType(LodgingType.CABIN);
                        }else if(hotelType.getText().toString().equalsIgnoreCase("HOTEL")) {
                            hotel.setType(LodgingType.HOTEL);
                        }else if(hotelType.getText().toString().equalsIgnoreCase("BNB")) {
                            hotel.setType(LodgingType.BNB);
                        }
                        hotel.setLocation(hotelLocation.getText().toString());
                        Trip trip = new Trip();
                        trip.setLocation(tripName.getText().toString());
                        trip.setDuration(tripDuration.getText().toString());
                        trip.setLodging(hotel);
                        trip.setFlight(flight);

                        Call<Trip> call = ApiClientFactory.GetTripApi().updateTrip(idTrip, trip);
                        call.enqueue(new Callback<Trip>() {
                            @Override
                            public void onResponse(Call<Trip> call, Response<Trip> response) {
                                Toast.makeText(context,"Update Success",Toast.LENGTH_SHORT).show();
                                dialog.dismiss();


                            }

                            @Override
                            public void onFailure(Call<Trip> call, Throwable t) {
                                dialog.dismiss();
                            }
                        });
                    }
                });

            }
        });
    }


    @Override
    public int getItemCount() {
        return tripResponseList.size();
    }

    public class TripAdapterVH extends RecyclerView.ViewHolder {
        TextView tripName, tripDuration, tripFlightName, tripFlightPrice, tripFlightDay, tripFlightstartpoint,tripFlightendpoint;
        TextView hotelName, hotelPrice, hotelLocation, hotelType, hotelStartday, hotelEndday;
        ImageView deleteButton, editButton;
        private TripAdapter adapter;
        public TripAdapterVH(@NonNull View itemView) {
            super(itemView);
            tripName = itemView.findViewById(R.id.trip_name);
            tripDuration = itemView.findViewById(R.id.trip_duration);
            tripFlightName = itemView.findViewById(R.id.trip_flightName);
            tripFlightPrice = itemView.findViewById(R.id.flight_price);
            tripFlightDay = itemView.findViewById(R.id.tripDate);
            tripFlightstartpoint = itemView.findViewById(R.id.trip_start);
            tripFlightendpoint = itemView.findViewById(R.id.trip_end);
            hotelName = itemView.findViewById(R.id.trip_hotelName);
            hotelPrice = itemView.findViewById(R.id.hotel_price);
            hotelLocation = itemView.findViewById(R.id.hotel_location);
            hotelType = itemView.findViewById(R.id.hotel_type);
            hotelStartday = itemView.findViewById(R.id.hotel_start);
            hotelEndday = itemView.findViewById(R.id.hotel_end);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            editButton = itemView.findViewById(R.id.editButton);
        }
        public TripAdapterVH linkAdapter(TripAdapter adapter) {
            this.adapter = adapter;
            return this;
        }
    }

    public void deleteTrip(int id) {

        Call<Trip> call = ApiClientFactory.GetTripApi().deleteTrip(id);
        call.enqueue(new Callback<Trip>() {
            @Override
            public void onResponse(Call<Trip> call, Response<Trip> response) {

            }

            @Override
            public void onFailure(Call<Trip> call, Throwable t) {

            }
        });

    }


}
