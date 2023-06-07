package com.example.airplanned.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.airplanned.R;
import com.example.airplanned.activity.AddingTripActivity;
import com.example.airplanned.activity.FlightPageActivity;
import com.example.airplanned.activity.LodgingPageActivity;
import com.example.airplanned.model.Flight;
import com.example.airplanned.model.LodgingType;
import com.example.airplanned.model.Trip;
import com.example.airplanned.model.User;
import com.example.airplanned.model.UserType;
import com.orhanobut.dialogplus.DialogPlus;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Julie Duong
 */

public class FlightAdapter extends RecyclerView.Adapter<FlightAdapter.FlightAdapterVH>{
    private List<Flight> flightResponseList = new ArrayList<>();
    private Context context;
    private RecycleViewClickListener recycleViewClickListener;

    public FlightAdapter() {}

    public FlightAdapter(List<Flight> mFlights, RecycleViewClickListener recycleViewClickListener) {
        this.flightResponseList = mFlights;
        this.recycleViewClickListener = recycleViewClickListener;
    }


    public void setData(List<Flight> flightResponseList) {
        this.flightResponseList = flightResponseList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public FlightAdapter.FlightAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new FlightAdapter.FlightAdapterVH(LayoutInflater.from(context).inflate(R.layout.flightrow,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull FlightAdapter.FlightAdapterVH holder, int position) {
        Flight flightResponse = flightResponseList.get(position);
        holder.flightName.setText(flightResponse.getAirlineName());
        holder.flightPrice.setText(String.valueOf(Math.ceil(flightResponse.getPrice())));
        holder.flightFrom.setText(flightResponse.getDeparting());
        holder.flightTo.setText(flightResponse.getArriving());
        holder.flightDate.setText(flightResponse.getDate());

    }

    @Override
    public int getItemCount() {
        return flightResponseList.size();
    }

    public interface RecycleViewClickListener {
        void OnClick(int position);
    }

    public class FlightAdapterVH extends RecyclerView.ViewHolder  {
        TextView flightName, flightPrice, flightFrom, flightTo, flightDate;
        private FlightAdapter adapter;

        public FlightAdapterVH(@NonNull View itemView) {
            super(itemView);
            flightName = itemView.findViewById(R.id.api_flightname);
            flightPrice = itemView.findViewById(R.id.api_flightprice);
            flightFrom = itemView.findViewById(R.id.api_flightFrom);
            flightTo = itemView.findViewById(R.id.api_flightTo);
            flightDate = itemView.findViewById(R.id.api_flightDate);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recycleViewClickListener.OnClick(getAdapterPosition());
                }
            });
        }

        public FlightAdapter.FlightAdapterVH linkAdapterFlight(FlightAdapter adapter) {
            this.adapter = adapter;
            return this;
        }

    }

}
