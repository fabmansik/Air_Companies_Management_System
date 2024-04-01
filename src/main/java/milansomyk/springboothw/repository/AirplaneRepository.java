package milansomyk.springboothw.repository;

import milansomyk.springboothw.entity.Airplane;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

public interface AirplaneRepository extends JpaRepository<Airplane, Integer> {
}
