package backend.Lodgings;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LodgingRepository extends JpaRepository<Lodging, Long> {
    Lodging findById(int id);
    void deleteById(int id);
    void delete(Lodging lodging);
}
