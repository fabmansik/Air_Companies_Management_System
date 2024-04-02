package milansomyk.springboothw.repository;

import milansomyk.springboothw.entity.Flight;
import milansomyk.springboothw.enums.FlightStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Integer> {
    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE flight SET flight_status = :status WHERE id = :id")
    Integer updateStatusById(Integer id, String status);

    @Query(nativeQuery = true, value = "SELECT flight.*, air_company.name FROM flight INNER JOIN air_company ON air_company.id = flight.air_company_id WHERE flight.flight_status = :flightStatus AND air_company.name= :companyName")
    List<Flight> getFlightsByCompanyNameAndFlightStatus(String companyName, String flightStatus);
    List<Flight> getFlightsByFlightStatus(FlightStatus flightStatus);
}
