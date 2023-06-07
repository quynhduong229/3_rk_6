package backend.Users;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import backend.Posts.Post;
import backend.Trips.Trip;

/**
 * The backend's version of the user object
 * @author asher
 */
@Entity
public class User {

    /*
     * The annotation @ID marks the field below as the primary key for the table created by springboot
     * The @GeneratedValue generates a value if not already present, The strategy in this case is to start from 1 and increment for each table
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /**
     * the primary key of the user
     */
    private int id;
    /**
     * a secondary key of the user. Unique and used for logging in.
     */
    private String emailId;
    /**
     * the user's name.
     */
    private String name;
    /**
     * the day that the user's account was created.
     */
    private Date joiningDate;
    /**
     * the user's activity status.
     */
    private boolean isActive;
    /**
     * the user's password.
     */
    private String password;
    /**
     * the account type of the user. Either BASIC, ADMIN, or POSTER.
     */
    private UserType accountType;

    /*
     * @OneToOne creates a relation between the current entity/table(Laptop) with the entity/table defined below it(User), the cascade option tells springboot
     * to create the child entity if not present already (in this case it is laptop)
     * @JoinColumn specifies the ownership of the key i.e. The User table will contain a foreign key from the laptop table and the column name will be laptop_id
     */
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "trip_id")
    /**
     *
     */
    private List<Trip> trips;

    @OneToMany
    @JoinColumn(name = "post_id")
    private List<Post> posts;

//    @OneToMany
//    @JoinColumn(name = "flight_id")
//    private List<Flight> flights;
//
//    @OneToMany
//    @JoinColumn(name = "lodging_id")
//    private List<Lodging> lodgings;

//    @OneToMany
//    @JoinColumn(name = "transport_id")
//    private List<Transport> transports;

     /*
     * @OneToMany tells springboot that one instance of User can map to multiple instances of Phone OR one user row can map to multiple rows of the phone table 
     */
//    @OneToMany
//    private List<Phone> phones;

     // =============================== Constructors ================================== //


    /**
     *
     * @param name the user's name
     * @param emailId the user's email address. Unique, and used as a secondary key for the user object.
     * @param password the user's password. Used for logging in
     * @param accountType the account type of the user. Either BASIC, ADMIN, or POSTER. POSTER has all
     *                    the privileges of a basic user plus the ability to add flights or lodgings
     *                    to the discover page on the front end. ADMIN has all the privileges of a
     *                    POSTER user plus the ability to delete posts of any user.
     */
    public User(String name, String emailId, String password, UserType accountType) {
        this.name = name;
        this.emailId = emailId;
        this.joiningDate = new Date();
        this.isActive = true;
        this.password = password;
        this.accountType = accountType;
        trips = new ArrayList<>();
        posts = new ArrayList<>();
//        flights = new ArrayList<>();
//        lodgings = new ArrayList<>();
//        transports = new ArrayList<>();
    }

    /**
     * default no parameters constructor
     */
    public User() {
        trips = new ArrayList<>();
        posts = new ArrayList<>();
//        flights = new ArrayList<>();
//        lodgings = new ArrayList<>();
//        transports = new ArrayList<>();
    }

    
    // =============================== Getters and Setters for each field ================================== //


    /**
     *
     * @return user's id
     */
    public int getId(){
        return id;
    }

    /**
     * sets the user's id
     * @param id
     */
    public void setId(int id){
        this.id = id;
    }

    /**
     *
     * @return user's name
     */
    public String getName(){
        return name;
    }

    /**
     * sets the user's name
     * @param name
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     *
     * @return user's email address
     */
    public String getEmailId(){
        return emailId;
    }

    /**
     * sets the user's email address
     * @param emailId
     */
    public void setEmailId(String emailId){
        this.emailId = emailId;
    }

    /**
     *
     * @return user's password
     */
    public String getPassword(){
        return password;
    }

    /**
     * sets the user's password
     * @param password
     */
    public void setPassword(String password){
        this.password = password;
    }

    /**
     *
     * @return the day that the user's account was created
     */
    public Date getJoiningDate(){
        return joiningDate;
    }

    /**
     * sets the date of the user's account
     * @param joiningDate
     */
    public void setJoiningDate(Date joiningDate){
        this.joiningDate = joiningDate;
    }

    /**
     *
     * @return whether the user is active
     */
    public boolean getIsActive(){
        return isActive;
    }

    /**
     * sets the user's activity status
     * @param isActive
     */
    public void setIsActive(boolean isActive){
        this.isActive = isActive;
    }

    /**
     *
     * @return a list of all the user's trips
     */
    public List<Trip> getTrips(){
        return trips;
    }

    /**
     * sets the user's trip list to the input
     * @param trips
     */
    public void setTrips(List<Trip> trips){
        this.trips = trips;
    }

    /**
     * adds the given trip to the user's list of trips
     * @param trip
     */
    public void addTrip(Trip trip){
        this.trips.add(trip);
    }

    /**
     *
     * @return a list of all the user's posts
     */
    public List<Post> getPosts(){
        return posts;
    }

    /**
     * sets the user's post list to the input
     * @param posts
     */
    public void setPosts(List<Post> posts){
        this.posts = posts;
    }

    /**
     * adds the given post to the user's list of posts
     * @param post
     */
    public void addPost(Post post){
        this.posts.add(post);
    }

    /**
     *
     * @return the user's account type
     */
    public UserType getAccountType() {
        return accountType;
    }

    /**
     * sets the user's account type
     * @param accountType
     */
    public void setAccountType(UserType accountType) {
        this.accountType = accountType;
    }

    //    public List<Flight> getFlights(){
//        return flights;
//    }
//
//    public void setFlights(List<Flight> flights){
//        this.flights = flights;
//    }
//
//    public void addFlight(Flight flight){
//        this.flights.add(flight);
//    }
//
//    public List<Lodging> getLodgings(){
//        return lodgings;
//    }
//
//    public void setLodgings(List<Lodging> lodgings){
//        this.lodgings = lodgings;
//    }
//
//    public void addLodging(Lodging lodging){
//        this.lodgings.add(lodging);
//    }

//    public List<Transport> getTransports(){
//        return transports;
//    }
//
//    public void setTransports(List<Transport> transports){
//        this.transports = transports;
//    }
//
//    public void addTransport(Transport transport){
//        this.transports.add(transport);
//    }
//    public List<Phone> getPhones() {
//        return phones;
//    }
//
//    public void setPhones(List<Phone> phones) {
//        this.phones = phones;
//    }
//
//    public void addPhones(Phone phone){
//        this.phones.add(phone);
//    }
    
}
