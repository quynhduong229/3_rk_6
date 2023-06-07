package com.example.airplanned.api;

import com.example.airplanned.model.Trip;
import com.example.airplanned.model.User;

import java.util.List;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * The purpose of this class is to organize all retrofit requests for user object
 * @author Saiyara Iftekharuzzaman and Julie Duong
 */
public interface UserApi {

    /**
     * method post a new user created on the registration page
     * @param name
     *  user input name
     * @param password
     *  user input password
     * @param emailId
     *  user input email id
     * @return
     *  if creation was successful
     */
    @FormUrlEncoded
    @POST("/users/create")
    Call<User> createUser(
            @Field("username") String name,
            @Field("password") String password,
            @Field("email") String emailId
    );

    @POST("/users/create")
    Call<Integer> createUser(@Body User user);

    @PUT("/users/login/{emailid}")
    Call<Integer> logInUser(@Path("emailid") String emailid, @Body String password);

    //Gets all the users
    @GET("/users/get")
    Call<List<User>> getUsers();

    @GET("/users/get/email/{emailid}")
    Call<User> getUserbyEmail(@Path("emailid") String emailid);

    @GET("/users/get/id/{id}")
    Call<User> getUserbyId(@Path("id") int id);

    @GET("/users/{id}/get/trips")
    Call<List<Trip>> getTripbyID(@Path("id") int id);

    @PUT("/users/update-password/email/{emailid}")
    Call<Integer> updatePassword(@Path("emailid") String emailid, @Body String password);

    @PUT("/users/assign-trip/{userId}/trip/{tripId}")
    Call<Integer> assignTriptoUser(@Path("userId") int userId, @Path("tripId") int tripId);

    @DELETE("/users/delete/email/{emailid}")
    Call<Integer> deleteAccount(@Path("emailid") String emailid);

    Call<User> loginInput(
            @Field("email") String emailId,
            @Field("password") String password
    );


    //works
//    /**
//     * Sends user object to backend to save as a new user
//     * @param newUser
//     * User being sent
//     * @return
//     * if call is successful
//     */
//    @POST("/users/create")
//    Call<User> PostUserByBodyReg(@Body User newUser);

    //does not work
    //might need to check if the user exists in the first place
    /**
     * sends password to check if it matches the email id given
     * @param emailid
     * user input
     * @param password
     * user input
     * @return
     * if password matched username (successful login)
     */
    @PUT("/users/login/{emailid}")
    Call<Integer> PostUserByBodyLog(@Path("emailid")String emailid, @Body String password);

    //GET
    //works
    /**
     * gets user by specified user id
     * @param id
     * user id
     * @return
     * if call was successful
     */
    @GET("/users/get/id/{id}")
    Call<User> getUser(@Path("id") String id);



    //get user by username -> this does not work
    /**
     * gets user by email id
     * @param emailid
     * user's email
     * @return
     * user info
     */
    @GET("/users/get/email/{emailid}")
    Call<User> getUserByEmail(@Path("emailid")String emailid);

//wyattd@iastate.edu
// test12345


}


