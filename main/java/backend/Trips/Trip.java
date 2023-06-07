package backend.Trips;

import javax.persistence.*;

import backend.Flights.Flight;
import backend.Lodgings.Lodging;
import com.fasterxml.jackson.annotation.JsonIgnore;

import backend.Users.User;

/**
 * the backend's version of the trip object.
 * @author asher
 */

@Entity
public class Trip {

    /*
     * The annotation @ID marks the field below as the primary key for the table created by springboot
     * The @GeneratedValue generates a value if not already present, The strategy in this case is to start from 1 and increment for each table
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /**
     * the primary key.
     */
    private int id;
    /**
     * true if the trip has already taken place, false otherwise.
     */
    private boolean isArchived;
    /**
     * the time between checkin and checkout.
     */
    private String duration;
    /**
     * the location of the trip. Same as the location of the lodging if present.
     */
    private String location;

    @OneToOne(cascade = CascadeType.ALL) //Haven't decided if these should be ManyToOne or OneToOne
    /**
     * the flight associated with this trip. Can be null if this trip does not have a flight.
     */
    private Flight flight;
    @OneToOne(cascade = CascadeType.ALL)
    /**
     * the lodging associated with this trip. Can be null if this trip does not have a lodging.
     */
    private Lodging lodging;

//    private double cpuClock;
//    private int cpuCores;
//    private int ram;
//    private String manufacturer;
//    private int cost;

    /*
     * @OneToOne creates a relation between the current entity/table(Laptop) with the entity/table defined below it(User)
     * @JsonIgnore is to assure that there is no infinite loop while returning either user/laptop objects (laptop->user->laptop->...)
     */
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="user_id")
    /**
     * the user that this trip belongs to.
     */
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
     * default no parameters constructor.
     */
    public Trip() {
    }

    // =============================== Getters and Setters for each field ================================== //

    /**
     *
     * @return the trip's id.
     */
    public int getId(){
        return id;
    }

    /**
     * set's the trip's id.
     * @param id
     */
    public void setId(int id){
        this.id = id;
    }

    /**
     *
     * @return the flight associated with this trip if present. null if this trip doesn't have a flight.
     */
    public Flight getFlight(){
        return flight;
    }

    /**
     * sets the trip's flight.
     * @param flight
     */
    public void setFlight(Flight flight){
        this.flight = flight;
    }

    /**
     *
     * @return the lodging associated with this trip if present. null if this trip doesn't have a lodging.
     */
    public Lodging getLodging(){
        return lodging;
    }

    /**
     * sets the trip's lodging.
     * @param lodging
     */
    public void setLodging(Lodging lodging){
        this.lodging = lodging;
    }

    /**
     *
     * @return if the trip has already taken place.
     */
    public boolean getIsArchived(){
        return isArchived;
    }

    /**
     * sets if the trip has taken place or not.
     * @param isArchived
     */
    public void setIsArchived(boolean isArchived){
        this.isArchived = isArchived;
    }

    /**
     *
     * @return the trip's duration.
     */
    public String getDuration() {
        return duration;
    }

    /**
     * sets the trip's duration.
     * @param duration
     */
    public void setDuration(String duration) {
        this.duration = duration;
    }

    /**
     *
     * @return the location of the trip
     */
    public String getLocation() {
        return location;
    }

    /**
     * sets the trip's location.
     * @param location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     *
     * @return the user who owns the trip.
     */
    public User getUser(){
        return user;
    }

    /**
     * sets the user who owns the trip.
     * @param user
     */
    public void setUser(User user){
        this.user = user;
    }

}
