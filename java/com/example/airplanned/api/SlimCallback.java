package com.example.airplanned.api;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * This class generalized the call back function in retrofit calls
 * @param <T>
 *     generalized type
 * @author Saiyara Iftekharuzzaman
 */
public class SlimCallback<T> implements Callback<T> {

    LambdaInterface<T> lambdaInterface;

    String logTag;

    /**
     * constructor for slimback call
     * @param lambdaInterface
     * lambdaInterface class
     */
    public SlimCallback(LambdaInterface<T> lambdaInterface) {
        this.lambdaInterface = lambdaInterface;
    }

    /**
     * handles retrofit call response
     * @param call
     * retrofit call
     * @param response
     * response from backend
     */
    @Override
    public void onResponse(Call<T> call, Response<T> response) {

        if(response.isSuccessful()){
            lambdaInterface.doSomething(response.body());
        }
        else{
            Log.d(logTag,"Code:  " + response.code() + "Msg:" + response.message());
        }

    }

    /**
     * handles retrofit failure response
     * @param call
     * retrofit call
     * @param t
     * generalized type
     */
    @Override
    public void onFailure(Call<T> call, Throwable t){

        Log.d(logTag,"Thrown:  " + t.getMessage());
    }

}
