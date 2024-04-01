package milansomyk.springboothw.repository;

import milansomyk.springboothw.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightRepository extends JpaRepository<Flight, Integer> {
}
