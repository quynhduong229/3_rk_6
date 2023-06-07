package backend.Users;

import java.util.ArrayList;
import java.util.List;

import backend.Posts.Post;
import backend.Posts.PostRepository;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import backend.Trips.Trip;
import backend.Trips.TripRepository;

/**
 * Controller for all User Operations
 * @author Wyatt Duberstein and Asher Gust
 *
 */

@ApiModel(description = "All Operations for Users")
@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TripRepository tripRepository;

    @Autowired
    PostRepository postRepository;

    private final int FAIL = 0;
    private final int SUCCESS = 1;
    private final int BAD_PASSWORD = 2;
    private final int USER_DOES_NOT_EXIST = 3;
    private final int USER_IS_NOT_ACTIVE = 4;

    /**
     * Retrieves a complete list of users
     * @return complete list of users
     */
    @GetMapping(path = "/users/get")
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    /**
     * Fetches a user object
     * @param id id of the user
     * @return user object
     */
    @GetMapping(path = "/users/get/id/{id}")
    public User getUserById(@PathVariable int id){
        return userRepository.findById(id);
    }

    /**
     * Fetches a user object
     * @param emailid email of the user
     * @return user object
     */
    @GetMapping(path = "/users/get/email/{emailid}")
    public User getUserByEmail(@PathVariable String emailid) {
        return userRepository.findUserByEmailId(emailid);
    }

    /**
     * Fetches all posts authorred by a specific user
     * @param emailid emailId of the user
     * @return list of posts
     */
    @GetMapping(path = "/users/{emailid}/get/posts")
    public List<Post> getPostsByUser(@PathVariable String emailid){
        User user = userRepository.findUserByEmailId(emailid);
        return user.getPosts();
    }

    /**
     * Fetches all trips of a specific user
     * @param id id of the user
     * @return list of trips
     */
    @GetMapping(path = "/users/{id}/get/trips")
    public List<Trip> getTripsByUser(@PathVariable int id){
        User user = userRepository.findById(id);
        return user.getTrips();
    }

    /**
     * Fetches all archived trips of a specific user
     * @param id id of the user
     * @return list of trips
     */
    @GetMapping(path = "/users/{id}/get/trips/archived")
    public List<Trip> getArchivedTripsByUser(@PathVariable int id){
        User user = userRepository.findById(id);
        if (user == null) return null;
        List<Trip> unarchived = user.getTrips();
        for (int i = 0; i < unarchived.size(); i++) {
            if (!unarchived.get(i).getIsArchived()) {
                unarchived.remove(i);
                i--;
            }
        }
        return user.getTrips();
    }

    /**
     * Fetches all unarchived trips of a specific user
     * @param id id of the user
     * @return list of trips
     */
    @GetMapping(path = "/users/{id}/get/trips/unarchived")
    public List<Trip> getUnarchivedTripsByUser(@PathVariable int id){
        User user = userRepository.findById(id);
        if (user == null) return null;
        List<Trip> unarchived = user.getTrips();
        for (int i = 0; i < unarchived.size(); i++) {
            if (unarchived.get(i).getIsArchived()) {
                unarchived.remove(i);
                i--;
            }
        }
        return user.getTrips();
    }

    /**
     * Creates a new user
     * @param user user object to insert into database
     * @return success/fail message
     */
    @PostMapping(path = "/users/create")
    public int createUser(@RequestBody User user){
        if (user == null)
            return FAIL;
	    user.setIsActive(true);
        userRepository.save(user);
        return SUCCESS;
    }

    /**
     * Logs a user in
     * @param emailid email of the user
     * @param password password of the user
     * @return success/fail message
     */
    @PutMapping(path = "/users/login/{emailid}")
    public int loginUser(@PathVariable String emailid, @RequestBody String password) {
        User user = userRepository.findUserByEmailId(emailid);

	if (password.contains("\"")) { // Sanitize password input
		password = password.substring(1, password.length() - 1);
	}

        if (user == null) {
            return USER_DOES_NOT_EXIST;
        }

        if (!user.getIsActive()) {
            return USER_IS_NOT_ACTIVE;
	}

	if (!user.getPassword().equals(password)) {
   	    return BAD_PASSWORD;
        }

        return SUCCESS;
    }

    /**
     * Replaces an existing user with a new one
     * @param id id of the user to be replaced
     * @param request new user object
     * @return newly updated user object
     */
    @PutMapping("/users/replace-user/id/{id}")
    public User replaceUserById(@PathVariable int id, @RequestBody User request){
        User user = userRepository.findById(id);
        if(user == null)
            return null;
        userRepository.save(request);
        return userRepository.findById(id);
    }

    /**
     * Replaces an existing user with a new one
     * @param emailid email of the user to be replaced
     * @param request new user object
     * @return newly updated user object
     */
    @PutMapping(path = "/users/replace-user/email/{emailid}")
    public int replaceUserByEmail(@PathVariable String emailid, @RequestBody User request) {
        User user = userRepository.findUserByEmailId(emailid);
        if (user == null)
            return FAIL;
        userRepository.save(request);
        return SUCCESS;
    }

    /**
     * Updates the password of a user
     * @param emailid email of the user
     * @param password new password of the user
     * @return success/fail message
     */
    @PutMapping(path = "/users/update-password/email/{emailid}")
    public int updatePassword(@PathVariable String emailid, @RequestBody String password) {
        User user = userRepository.findUserByEmailId(emailid);
        if (user == null)
            return FAIL;
        user.setPassword(password);
        userRepository.save(user);
        return SUCCESS;
    }

    /**
     * Assigns a trip to a user
     * @param userId id of the user
     * @param tripId id of trip to assign
     * @return success/fail message
     */
    @PutMapping("/users/assign-trip/{userId}/trip/{tripId}")
    public int assignTripToUser(@PathVariable int userId,@PathVariable int tripId){
        User user = userRepository.findById(userId);
        Trip trip = tripRepository.findById(tripId);
        if(user == null || trip == null)
            return FAIL;
        trip.setUser(user);
        user.addTrip(trip);
        userRepository.save(user);
        return SUCCESS;
    }

    /**
     * Assigns an post to a user
     * @param userId id of the user
     * @param postId id of the post to assign
     * @return success/fail message
     */
    @PutMapping("/users/assign-post/{userId}/post/{postId}")
    public int assignPostToUser(@PathVariable int userId,@PathVariable int postId){
        User user = userRepository.findById(userId);
        Post post = postRepository.findById(postId);
        if(user == null || post == null)
            return FAIL;
        post.setEmailId(user);
        user.addPost(post);
        userRepository.save(user);
        return SUCCESS;
    }

    /**
     * Set the active status of a user by id
     * @param id id of the user
     * @param newActive new active status
     * @return success/fail message
     */
    @PutMapping(path = "/users/set-active/id/{id}")
    public int setActiveById(@PathVariable int id, @RequestBody boolean newActive) {
        User user = userRepository.findById(id);
        if (user == null)
            return FAIL;
        user.setIsActive(newActive);
        return SUCCESS;
    }

    /**
     * Set the active status of a user by email
     * @param emailId email of the user
     * @param newActive new active status
     * @return success/fail message
     */
    @PutMapping(path = "/users/set-active/email/{emailid}")
    public int setActiveByEmail(@PathVariable String emailId, @RequestBody boolean newActive) {
        User user = userRepository.findUserByEmailId(emailId);
        if (user == null)
            return FAIL;
        user.setIsActive(newActive);
        return SUCCESS;
    }

    /**
     * Deletes a user by email
     * @param emailid email of the user
     * @return success/fail message
     */
    @DeleteMapping(path = "/users/delete/email/{emailid}")
    public int deleteUserByEmail(@PathVariable String emailid) {
        User user = userRepository.findUserByEmailId(emailid);
        if (user == null)
            return FAIL;
        userRepository.delete(user);
        return SUCCESS;
    }

    /**
     * Deletes a user by id
     * @param id id of the user
     * @return success/fail message
     */
    @DeleteMapping(path = "/users/delete/id/{id}")
    public int deleteUserById(@PathVariable int id) {
        User user = userRepository.findById(id);
        if (user == null)
            return FAIL;
        userRepository.delete(user);
        return SUCCESS;
    }

//    @PutMapping("/users/{userId}/flights/{flightId}")
//    String assignFlightToUser(@PathVariable int userId,@PathVariable int flightId){
//        User user = userRepository.findById(userId);
//        Flight flight = flightRepository.findById(flightId);
//        if(user == null || flight == null)
//            return failure;
//        flight.setUser(user);
//        user.addFlight(flight);
//        userRepository.save(user);
//        return success;
//    } //NOT IMPLEMENTED YET

    /**
     * Deletes a trip
     * @param id id of the trip
     * @return success/fail message
     */
    @DeleteMapping(path = "/users/{id}")
    public int deleteTrip(@PathVariable int id){
        User user = userRepository.findById(id);
        if (user == null)
            return FAIL;
        userRepository.delete(user);
        return SUCCESS;
    }

}
