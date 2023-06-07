package backend.Flights;

import backend.Trips.Trip;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
//import java.util.List;

/**
 * the backend's version of the flight object.
 * @author asher
 */

@Entity
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /**
     * the primary key.
     */
    private int id;
    /**
     * the name the airliner.
     */
    private String airlineName;
    /**
     * the price of the flight.
     */
    private double price;
    /**
     * the day of the flight.
     */
    private String date;
    /**
     * the location that the flight is departing from.
     */
    private String departing; //Might use some API to make this an actual location if we have extra time.
    /**
     * the location that the flight is arriving to.
     */
    private String arriving; //Might use some API to make this an actual location if we have extra time.



    //More stuff for this class to be made later

    @OneToOne //Haven't decided if this should be OneToOne or OneToMany
    @JoinColumn(name = "trip_id")
    /**
     * The trip that this flight belongs to. Can be null if this flight does not yet belong to a trip.
     */
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

    /**
     * default no parameters constructor.
     */
    public Flight(){

    }


    /**
     *
     * @return the trip that this flight belongs to.
     */
    public Trip getTrip(){
        return trip;
    }

    /**
     * sets the trip that this flight belongs to.
     * @param trip
     */
    public void setTrip(Trip trip){
        this.trip = trip;
    }

    /**
     *
     * @return the trip's id.
     */
    public int getId() {
        return id;
    }

    /**
     * sets the id of the trip.
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return the name of the airliner.
     */
    public String getAirlineName() {
        return airlineName;
    }

    /**
     * sets the name of the airliner.
     * @param airlineName
     */
    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }

    /**
     *
     * @return the price of this flight.
     */
    public double getPrice() {
        return price;
    }

    /**
     * sets the price of this flight.
     * @param price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     *
     * @return the flight's date
     */
    public String getDate() {
        return date;
    }

    /**
     * sets the date of the flight.
     * @param date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     *
     * @return the location the flight is leaving from.
     */
    public String getDeparting() {
        return departing;
    }

    /**
     * sets the location the flight is leaving from.
     * @param departing
     */
    public void setDeparting(String departing) {
        this.departing = departing;
    }

    /**
     *
     * @return the location the flight is arriving to.
     */
    public String getArriving() {
        return arriving;
    }

    /**
     * sets the location the flight is arriving to.
     * @param arriving
     */
    public void setArriving(String arriving) {
        this.arriving = arriving;
    }

}
