package com.example.airplanned.api;

import com.example.airplanned.model.Trip;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
/**
 * The purpose of this class is to organize all retrofit requests for trip object
 * @author Julie Duong
 */
public interface TripApi {

    /**
     * gets all trips
     * @return
     * if call was successful
     */
    @GET("/trips")
    Call<List<Trip>> getAllTrips();

    /**
     * Sends trip object to backend to save as a new trip
     * @param trip
     * @return
     * if call is successful
     */
    @POST("/trips")
    Call<Trip> saveTrip(@Body Trip trip);

    @POST("/trips/user/{userid}")
    Call<Trip> saveTripbyUser(@Body Trip trip, @Path("userid") int userid);

    @PUT("trips/{tripsId}/flights/{flightsId}")
    Call<Integer> putFlight(@Path("tripsId") int tripsId, @Path("flightsId") int flightsId);


    @PUT("trips/{tripsId}/lodgings/{lodgingsId}")
    Call<Integer> putHotel(@Path("tripsId") int tripsId, @Path("lodgingsId") int lodgingsId);


    @PUT("/trips/{id}")
    Call<Trip> updateTrip(@Path("id") int id, @Body Trip trip);


    @DELETE("/trips/{id}")
    Call<Trip> deleteTrip(@Path("id") int id);

}
