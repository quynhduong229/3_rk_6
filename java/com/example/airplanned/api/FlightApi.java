package com.example.airplanned.api;

import com.example.airplanned.model.Flight;
import com.example.airplanned.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * The purpose of this class is to list all retrofit requests for flights
 * @author  Julie Duong
 */
public interface FlightApi {
    //GETS from API to get info on flights

    @GET("/flights")
    Call<List<Flight>> getAllFlight();


    @POST("/flights")
    Call<Flight> saveFlight(@Body Flight flight);


    /**
     * get specific flight
     * @param flightId
     *  flight id
     * @return
     *  specified flight
     */
    @GET("/flights/{flightsId}")
    Call<Flight> getFlight(@Path("flightsId") String flightId);

    /**
     * assigns flight to trip
     * @param tripsId
     *  specific trip id
     * @param flightId
     *  specific flight
     * @return
     * success or failure of call
     */
    @PUT("trips/{tripsId}/flights/{flightsId}")
    Call<Flight> assignFlightToTrip(@Path("tripsId") String tripsId,@Path("flightsId") String flightId);

    @GET("/flights/from/{from}/to/{to}/date/{date}/mock")
    Call<List<Flight>> getMockFLights(@Path("from") String from, @Path("to") String to, @Path("date") String date);

    @GET("/flights/from/{from}/to/{to}/date/{date}")
    Call<List<Flight>> getRealFLights(@Path("from") String from, @Path("to") String to, @Path("date") String date);
}
