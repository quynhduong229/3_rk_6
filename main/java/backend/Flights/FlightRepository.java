package backend.Flights;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightRepository extends JpaRepository<Flight, Long> {
    Flight findById(int id);
    void deleteById(int id);
    void delete(Flight flight);
}
