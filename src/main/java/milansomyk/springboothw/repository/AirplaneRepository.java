package milansomyk.springboothw.repository;

import milansomyk.springboothw.entity.Airplane;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface AirplaneRepository extends JpaRepository<Airplane, Integer> {
    @Query(nativeQuery = true,value = "insert into airplane values (id = ifNull(:id,id), :name, :factorySerialNumber, :companyId, :numberOfFlights, :flightDistance, :fuelCapacity, :type, :createdAt)")
    Airplane saveWithCompany(int companyId, String name, String factorySerialNumber, Integer numberOfFlights, Integer flightDistance, Integer fuelCapacity, String type, LocalDate createdAt);
}
