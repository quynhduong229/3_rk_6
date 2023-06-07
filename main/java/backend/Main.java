package backend;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import backend.Trips.TripRepository;
import backend.Users.UserRepository;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title="Backend API", version ="1.0", description = "All Backend Operations for Airplanned"))
class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    // Create 3 users with their machines and phones
    @Bean
    CommandLineRunner initUser(UserRepository userRepository, TripRepository tripRepository) {
        return args -> {
            /*User user1 = new User("John", "john@somemail.com", "password", UserType.BASIC);
            User user2 = new User("Jane", "jane@somemail.com", "sports123", UserType.ADMIN);
            User user3 = new User("Justin", "justin@somemail.com", "I<3Jane", UserType.POSTER);
            Flight flight1 = new Flight("Southwest", 99.99, "12/31/2021", "Des Moines", "Minneapolis");
            Flight flight2 = new Flight("Singapore", 120.00, "10/18/2022", "Chicago", "Tokyo");
            Flight flight3 = new Flight("Emirates", 420.69, "02/02/2018", "New York City", "Mars");
            Lodging lodging1 = new Lodging("Eddy's BNB", 79.99, "12/31/2021", "01/03/2021", "Minneapolis", LodgingType.BNB);
            Lodging lodging2 = new Lodging("Comfort Suites", 69.99, "10/19/2022", "10/20/2022", "Tokyo", LodgingType.HOTEL);
            Lodging lodging3 = new Lodging("Martian Cabins", 7999.99, "02/07/2018", "05/03/2019", "Mars", LodgingType.CABIN);
            Trip trip1 = new Trip();
            Trip trip2 = new Trip();
            Trip trip3 = new Trip();
            for(int i=6; i<13; i++)
               phoneRepository.save(new Phone("Apple", (int)Math.pow(1.3, i), Math.pow(1.1, i)*1000, "iPhone "+i, (int)Math.pow(1.3, i)*100));
            trip1.setFlight(flight1);
            trip2.setFlight(flight2);
            trip3.setFlight(flight3);
            trip1.setLodging(lodging1);
            trip2.setLodging(lodging2);
            trip3.setLodging(lodging3);
            user1.addTrip(trip1);
            user2.addTrip(trip2);
            user3.addTrip(trip3);
            user1.addPhones(phoneRepository.findById(1));
            user1.addPhones(phoneRepository.findById(2));
            user1.addPhones(phoneRepository.findById(6));
            user2.addPhones(phoneRepository.findById(3));
            user2.addPhones(phoneRepository.findById(4));
            user3.addPhones(phoneRepository.findById(5));
            user3.addPhones(phoneRepository.findById(7));
            userRepository.save(user1);
            userRepository.save(user2);
            userRepository.save(user3);
        };*/
        };

    }

}
