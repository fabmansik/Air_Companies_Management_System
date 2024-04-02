package milansomyk.springboothw.repository;

import milansomyk.springboothw.entity.Airplane;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

public interface AirplaneRepository extends JpaRepository<Airplane, Integer> {
    Optional<Airplane> findByFactorySerialNumber(String serialNumber);
    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE airplane SET air_company_id = :companyId WHERE factory_serial_number = :factorySerialNumber")
    Integer updateByFactorySerialNumber(Integer companyId, String factorySerialNumber);
}
