package backend.Trips;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Vivek Bengre
 *
 */

public interface TripRepository extends JpaRepository<Trip, Long> {
    Trip findById(int id);

    @Transactional
    void deleteById(int id);
    void delete(Trip trip);
}
