package backend.Trips;

import java.util.List;

import backend.Flights.Flight;
import backend.Flights.FlightRepository;
import backend.Lodgings.Lodging;
import backend.Lodgings.LodgingRepository;
import backend.Users.User;
import backend.Users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Wyatt Duberstein and Asher Gust
 *
 */

@RestController
public class TripController {

    @Autowired
    TripRepository tripRepository;

    @Autowired
    FlightRepository flightRepository;

    @Autowired
    LodgingRepository lodgingRepository;

    @Autowired
    UserRepository userRepository;


    private final int FAIL = 0;
    private final int SUCCESS = 1;

    /**
     * Fetches a list of all trips and their attributes
     * @return list of trips
     */
    @GetMapping(path = "/trips")
    public List<Trip> getAllTrips(){
        return tripRepository.findAll();
    }

    /**
     * Fetches a trip by id
     * @param id id of the trip
     * @return trip object
     */
    @GetMapping(path = "/trips/{id}")
    public Trip getTripById(@PathVariable int id){
        return tripRepository.findById(id);
    }

    /**
     * Creates a new trip
     * @param trip trip object to be inserted into database
     * @return success/fail message
     */
    @PostMapping(path = "/trips")
    public Trip createTrip(@RequestBody Trip trip){
        if (trip == null)
            return null;
        tripRepository.save(trip);
        return trip;
    }

    /**
     * Creates a new trip and assigns it to a user
     * @param trip trip object to be inserted into database
     * @param userId user to assign the trip to
     * @return success/fail message
     */
    @PostMapping(path = "/trips/user/{userId}")
    public Trip createTripUser(@RequestBody Trip trip, @PathVariable int userId){
        if (trip == null)
            return null;
        User user = userRepository.findById(userId);
        if (user == null)
            return null;
        user.addTrip(trip);
        trip.setUser(user);
        tripRepository.save(trip);
        userRepository.save(user);
        return trip;
    }

    /**
     * Updates a trip with a new one
     * @param id id of trip
     * @param request new trip to replace old one
     * @return newly inserted trip
     */
    @PutMapping(path = "/trips/{id}")
    public Trip updateTrip(@PathVariable int id, @RequestBody Trip request){
        Trip trip = tripRepository.findById(id);
        if(trip == null)
            return null;
        request.setId(trip.getId());
        tripRepository.save(request);
        return tripRepository.findById(id);
    }

    /**
     * Assign a flight to a trip
     * @param tripsId id of trip
     * @param flightsId id of flight
     * @return success/fail message
     */
    @PutMapping(path = "trips/{tripsId}/flights/{flightsId}")
    public int assignFlightToTrip(@PathVariable int tripsId, @PathVariable int flightsId){
        Trip trip = tripRepository.findById(tripsId);
        Flight flight = flightRepository.findById(flightsId);
        if (trip == null || flight == null) return FAIL;
        trip.setFlight(flight);
//        flight.setTrip(trip);
        tripRepository.save(trip);
        return SUCCESS;
    }

    /**
     * Assign a lodging to a trip
     * @param tripsId id of trip
     * @param lodgingsId id of lodging
     * @return success/fail message
     */
    @PutMapping(path = "trips/{tripsId}/lodgings/{lodgingsId}")
    public int assignLodgingToTrip(@PathVariable int tripsId, @PathVariable int lodgingsId){
        Trip trip = tripRepository.findById(tripsId);
        Lodging lodging = lodgingRepository.findById(lodgingsId);
        if (trip == null || lodging == null) return FAIL;
        trip.setLodging(lodging);
//        lodging.setTrip(trip);
        tripRepository.save(trip);
        return SUCCESS;
    }

    /**
     * Delete a trip
     * @param id id of trip
     * @return success/fail message
     */
    @DeleteMapping(path = "/trips/{id}")
    public int deleteTrip(@PathVariable int id){
        Trip trip = tripRepository.findById(id);
        if (trip == null)
            return FAIL;
        tripRepository.delete(trip);
        return SUCCESS;
    }
}
