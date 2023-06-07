package com.example.airplanned.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * The purpose of this class is to set up the retrofit calls
 * and create methods for each api type
 * @author Saiyara Iftekharuzzaman
 */
public class ApiClientFactory {

    static Retrofit apiClientSeed = null;

    //api client
    static Retrofit GetApiClientSeed()
    {
        if(apiClientSeed==null) {


            apiClientSeed = new Retrofit.Builder()
                    .baseUrl("http://coms-309-063.cs.iastate.edu:8080")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return apiClientSeed;
    }

    public static UserApi GetUserApi(){
        return GetApiClientSeed().create(UserApi.class);}


    public static TripApi GetTripApi() {
        return GetApiClientSeed().create(TripApi.class);
    }

    public static PostApi GetPostApi() {
        return GetApiClientSeed().create(PostApi.class);
    }

    public static FlightApi GetFlightApi() {
        return GetApiClientSeed().create(FlightApi.class);
    }

    public static HotelApi GetLogdingApi() {
        return GetApiClientSeed().create(HotelApi.class);
    }


//http://coms-309-063.cs.iastate.edu:8080
// https://30e2726d-8baa-423a-9fe8-b62fcc655c34.mock.pstmn.io

}
