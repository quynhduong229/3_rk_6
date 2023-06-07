package backend.Lodgings;

import backend.Trips.Trip;

import javax.persistence.*;
import java.util.Date;

/**
 * the backend's version of the lodging object
 * @author asher
 */

@Entity
public class Lodging {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /**
     * the primary key
     */
    private int id;
    /**
     * the name of the location.
     */
    private String name;
    /**
     * the total price of the stay.
     */
    private double price;
    /**
     * the day of checkin.
     */
    private String checkIn;
    /**
     * the day of checkout.
     */
    private String checkOut;
    /**
     * the place where the lodging is located.
     */
    private String location; //Might eventually use some API to make this an actual location if we have extra time.
    /**
     * the type of lodging. Either HOTEL, CABIN, or BNB
     */
    private LodgingType type;

    @OneToOne
    @JoinColumn(name = "trip_id")
    /**
     * The trip that this lodging belongs to. Can be null if this lodging does not yet belong to a trip.
     */
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

    /**
     * default no parameters constructor.
     */
    public Lodging(){

    }

//    public Trip getTrip(){
//        return trip;
//    }
//
//    public void setTrip(Trip trip){
//        this.trip = trip;
//    }

    /**
     *
     * @return the lodging's id.
     */
    public int getId() {
        return id;
    }

    /**
     * sets the id of the lodging.
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return the lodging's name.
     */
    public String getName() {
        return name;
    }

    /**
     * sets the name of the lodging.
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return the total price of the stay.
     */
    public double getPrice() {
        return price;
    }

    /**
     * sets the total price of the stay.
     * @param price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     *
     * @return the day of checkin.
     */
    public String getCheckIn() {
        return checkIn;
    }

    /**
     * sets the day of checkin.
     * @param checkIn
     */
    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    /**
     *
     * @return the day of checkout.
     */
    public String getCheckOut() {
        return checkOut;
    }

    /**
     * sets the day of checkout.
     * @param checkOut
     */
    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    /**
     *
     * @return the location of the lodging.
     */
    public String getLocation() {
        return location;
    }

    /**
     * sets the location of the lodging.
     * @param location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     *
     * @return the type of lodging.
     */
    public LodgingType getType() {
        return type;
    }

    /**
     * sets the type of the lodging.
     * @param type
     */
    public void setType(LodgingType type) {
        this.type = type;
    }
}
