package com.example.airplanned.api;

import com.example.airplanned.model.Flight;
import com.example.airplanned.model.Lodging;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * The purpose of this class is to list all retrofit requests for hotels
 * @author  Julie Duong
 */


public interface HotelApi {

    @GET("/lodgings")
    Call<List<Lodging>> getAllHotels();

    @POST("/lodgings")
    Call<Lodging> saveLodging(@Body Lodging hotels);

    @GET("lodgings/location/{location}/checkin/{checkIn}/checkout/{checkOut}")
    Call<List<Lodging>> getRealFLights(@Path("location") String location, @Path("checkIn") String checkIn, @Path("checkOut") String checkOut);

}

