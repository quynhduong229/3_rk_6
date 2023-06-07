package com.example.airplanned.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.airplanned.R;
import com.example.airplanned.model.Lodging;

import java.util.List;

/**
 * This class is to help showing the hotel list from API in recycle view.
 * @author Julie Duong
 */

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.HotelAdapterVH> {
    private List<Lodging> hotelList;
    private Context context;
    private HotelAdapter.RecycleViewClickListener recycleViewClickListener;

    public HotelAdapter(List<Lodging> mHotel, HotelAdapter.RecycleViewClickListener recycleViewClickListener) {
        this.hotelList = mHotel;
        this.recycleViewClickListener = recycleViewClickListener;
    }


    public void setData(List<Lodging> hotelList) {
        this.hotelList = hotelList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public HotelAdapter.HotelAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new HotelAdapter.HotelAdapterVH(LayoutInflater.from(context).inflate(R.layout.hotelrow,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull HotelAdapter.HotelAdapterVH holder, int position) {
        Lodging hotelResponse = hotelList.get(position);
        holder.hotelName.setText(hotelResponse.getName());
        holder.hotelPrice.setText(String.valueOf(Math.ceil(hotelResponse.getPrice())));
        holder.hotelFrom.setText(hotelResponse.getCheckIn());
        holder.hotelTo.setText(hotelResponse.getCheckOut());
        holder.hotelLocation.setText(hotelResponse.getLocation());

    }

    @Override
    public int getItemCount() {

        return hotelList.size();
    }

    public interface RecycleViewClickListener {
        void OnClick(int position);
    }

    public class HotelAdapterVH extends RecyclerView.ViewHolder {
        TextView hotelName, hotelPrice, hotelFrom, hotelTo, hotelLocation;
        private HotelAdapter adapter;
        public HotelAdapterVH(@NonNull View itemView) {
            super(itemView);
            hotelName = itemView.findViewById(R.id.api_hotelname);
            hotelPrice = itemView.findViewById(R.id.api_hotelprice);
            hotelFrom = itemView.findViewById(R.id.api_hotelFrom);
            hotelTo = itemView.findViewById(R.id.api_hotelTo);
            hotelLocation = itemView.findViewById(R.id.api_hotelLocation);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recycleViewClickListener.OnClick(getAdapterPosition());
                }
            });

        }

        public HotelAdapter.HotelAdapterVH linkAdapterFlight(HotelAdapter adapter) {
            this.adapter = adapter;
            return this;
        }

    }

}