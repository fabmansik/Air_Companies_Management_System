package milansomyk.springboothw.mapper;

import milansomyk.springboothw.dto.FlightDto;
import milansomyk.springboothw.entity.Flight;
import org.springframework.stereotype.Component;

@Component
public class FlightMapper {
    public Flight fromDto(FlightDto flightDto){
        return new Flight(flightDto.getId(), flightDto.getFlightStatus(), flightDto.getAirCompanyId(),flightDto.getAirplaneId(),flightDto.getDepartureCountry(),flightDto.getDestinationCountry(),flightDto.getDistance(),flightDto.getEstimatedFlightTime(),flightDto.getStartedAt(),flightDto.getEndedAt(),flightDto.getDelayStartedAt(),flightDto.getCreatedAt());
    }
    public FlightDto toDto(Flight flight){
        return new FlightDto(flight.getId(), flight.getFlightStatus(), flight.getAirCompanyId(),flight.getAirplaneId(),flight.getDepartureCountry(), flight.getDestinationCountry(), flight.getDistance(), flight.getEstimatedFlightTime(), flight.getStartedAt(),flight.getEndedAt(),flight.getDelayStartedAt(),flight.getCreatedAt());
    }
}
