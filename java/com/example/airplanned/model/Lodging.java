package com.example.airplanned.model;

/**
 *The purpose of this class is to list all retrofit requests for hotels
 * @author Julie Duong
 */
public class Lodging {
    private int id;
    private String name;
    private double price;
    private String checkIn;
    private String checkOut;
    private String location; //Might eventually use some API to make this an actual location if we have extra time.
    private LodgingType type;


    private Trip trip;

    /**
     *
     * @param name name of the lodging
     * @param price price with two decimal points
     * @param checkIn date of check in in the format "mm/dd/yyyy"
     * @param checkOut date of check out in the format "mm/dd/yyyy"
     * @param location name of the place where the lodging is located
     * @param type the type of lodging
     */
    public Lodging(String name, double price, String checkIn, String checkOut, String location, LodgingType type){
        this.name = name;
        this.price = price;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.location = location;
        this.type = type;
    }

    //constructor
    public Lodging(){

    }

    /**
     * get hotel id
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * set hotel id
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * get hotel name
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * set hotel name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
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
     * get check in date
     * @return
     */
    public String getCheckIn() {
        return checkIn;
    }

    /**
     * set check in date
     * @param checkIn
     */
    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    /**
     * get check out date
     * @return
     */
    public String getCheckOut() {
        return checkOut;
    }

    /**
     * set check out date
     * @param checkOut
     */
    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    /**
     * get hotel location
     * @return
     */
    public String getLocation() {
        return location;
    }

    /**
     * set hotel location
     * @param location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * get lodging type
     * @return
     */
    public LodgingType getType() {
        return type;
    }

    /**
     * set lodging type
     * @param type
     */
    public void setType(LodgingType type) {
        this.type = type;
    }

    /**
     * print lodging
     * @return
     */
    public String printable(){
        return "id: " + this.id
                + "\n Hotel Name: " + this.name
                + "\n Price: " + this.price
                + "\n Check In: " + this.checkIn
                + "\n Check Out: " + this.checkOut
                + "\n Location: " + this.location
                + "\n Type: " +this.type;
    }

}