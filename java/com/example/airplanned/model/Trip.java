package com.example.airplanned.model;



/**
 * This method is to create a trip object
 * @author Julie Duong
 */
public class Trip{


    private int id;
    private boolean isArchived;
    private String duration;
    private String location;
    private Flight flight;
    private Lodging lodging;
    Flight aflight;
    Lodging hotel;


    private User user;


    /**
     *
     * @param lodging the lodging associated with this trip. Can be null if this trip does not have a lodging.
     * @param flight the flight associated with this trip. Can be null if this trip does not have a flight.
     * @param duration the time between checkin and checkout.
     * @param location The location of the trip. Same as the location of the lodging if present.
     */
    public Trip(Lodging lodging, Flight flight, String duration, String location) {
        this.lodging = lodging;
        this.flight = flight;
        this.duration = duration;
        this.location = location;
        isArchived = false;
    }

    /**
     * Constructor
     */
    public Trip() {
    }

    // =============================== Getters and Setters for each field ================================== //

    /**
     * get trip id
     * @return
     */
    public int getId(){
        return id;
    }

    /**
     * set trip id
     * @param id
     */
    public void setId(int id){
        this.id = id;
    }

    /**
     * get Flight object
     * @return
     */
    public Flight getFlight(){
        return flight;
    }

    /**
     * set Flight object
     * @param flight
     */
    public void setFlight(Flight flight){
        this.flight = flight;
    }

    /**
     * get Lodging object
     * @return
     */
    public Lodging getLodging(){
        return lodging;
    }

    /**
     * set Lodging object
     * @param lodging
     */
    public void setLodging(Lodging lodging){
        this.lodging = lodging;
    }

    public boolean getIsArchived(){
        return isArchived;
    }

    public void setIsArchived(boolean isArchived){
        this.isArchived = isArchived;
    }

    /**
     * get trip duration
     * @return
     */
    public String getDuration() {
        return duration;
    }

    /**
     * set trip duration
     * @param duration
     */
    public void setDuration(String duration) {
        this.duration = duration;
    }

    /**
     * get trip location
     * @return
     */
    public String getLocation() {
        return location;
    }

    /**
     * set trip location
     * @param location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * get user
     * @return
     */
    public User getUser(){
        return user;
    }

    /**
     * set user
     * @param user
     */
    public void setUser(User user){
        this.user = user;
    }

    /**
     * print trip
     * @return
     */
    public String printable(){
        return "id: " + this.id
                + "\n isArchive: " + this.isArchived
                + "\n Flight: " + this.flight
                + "\n Hotel: " + this.lodging
                + "\n Duration: " + this.duration
                + "\n Location: " + this.location;
    }
}
