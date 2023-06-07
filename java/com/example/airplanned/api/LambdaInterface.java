package com.example.airplanned.api;

/**
 * This class is used to generalize the slim call back class
 * @param <T>
 *     generalized type
 * @author Saiyara Iftekharuzzaman
 */
public interface LambdaInterface<T>{
    public void doSomething(T result);

}
