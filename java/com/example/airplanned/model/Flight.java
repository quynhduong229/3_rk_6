package com.example.airplanned.model;

/**
 * The purpose of this class is to list all retrofit requests for flights
 * @author Julie Duong
 */

public class Flight {

    private int id;
    private String airlineName;
    private double price;
    private String date;
    private String departing; //Might use some API to make this an actual location if we have extra time.
    private String arriving; //Might use some API to make this an actual location if we have extra time.
    //More stuff for this class to be made later


    private Trip trip;

    /**
     *
     * @param airlineName name of the airline
     * @param price price with two decimal points
     * @param date date in the format "mm/dd/yyyy"
     * @param departing location the flight is departing from
     * @param arriving location the flight is arriving to
     */
    public Flight(String airlineName, double price, String date, String departing, String arriving){
        this.airlineName = airlineName;
        this.price = price;
        this.date = date;
        this.departing = departing;
        this.arriving = arriving;
    }

    // constructor
    public Flight(){

    }

    /**
     * @param flight
     */
    public Flight(Flight flight) {
        this.airlineName = flight.airlineName;
        this.price = flight.price;
        this.date = flight.date;
        this.departing = flight.departing;
        this.arriving = flight.arriving;
    }

    /**
     * get Trip
     * @return
     */
    public Trip getTrip(){
        return trip;
    }

    /**
     * set Trip
     * @param trip
     */
    public void setTrip(Trip trip){
        this.trip = trip;
    }

    /**
     * get flight id
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * set flight id
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * get flight name
     * @return
     */
    public String getAirlineName() {
        return airlineName;
    }

    /**
     * set flight name
     * @param airlineName
     */
    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }

    /**
     * get price
     * @return
     */
    public double getPrice() {
        return price;
    }

    /**
     * set price
     * @param price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * get date
     * @return
     */
    public String getDate() {
        return date;
    }

    /**
     * set date
     * @param date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * get departing location
     * @return
     */
    public String getDeparting() {
        return departing;
    }

    /**
     * set departing location
     * @param departing
     */
    public void setDeparting(String departing) {
        this.departing = departing;
    }

    /**
     * get arriving location
     * @return
     */
    public String getArriving() {
        return arriving;
    }

    /**
     * set arriving location
     * @param arriving
     */
    public void setArriving(String arriving) {
        this.arriving = arriving;
    }

    /**
     * print flight
     * @return
     */
    public String printable(){
        return "id: " + this.id
                + "\n Flight Name: " + this.airlineName
                + "\n Price: " + this.price
                + "\n Date: " + this.date
                + "\n Departing: " + this.departing
                + "\n Arriving: " + this.arriving;
    }


}
