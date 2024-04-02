package milansomyk.springboothw.mapper;

import milansomyk.springboothw.dto.FlightDto;
import milansomyk.springboothw.entity.Flight;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class FlightMapper {
    private final AirCompanyMapper airCompanyMapper;
    private final AirplaneMapper airplaneMapper;

    public FlightMapper(AirCompanyMapper airCompanyMapper, AirplaneMapper airplaneMapper) {
        this.airCompanyMapper = airCompanyMapper;
        this.airplaneMapper = airplaneMapper;
    }

    public Flight fromDto(FlightDto flightDto){
        return new Flight(flightDto.getId(), flightDto.getFlightStatus(), airCompanyMapper.fromDto(flightDto.getAirCompanyId()),airplaneMapper.fromDto(flightDto.getAirplaneDtoId()),flightDto.getDepartureCountry(),flightDto.getDestinationCountry(),flightDto.getDistance(),flightDto.getEstimatedFlightTime(),flightDto.getStartedAt(),flightDto.getEndedAt(),flightDto.getDelayStartedAt(),flightDto.getCreatedAt());
    }
    public FlightDto toDto(Flight flight){
        return new FlightDto(flight.getId(), flight.getFlightStatus(), airCompanyMapper.toDto(flight.getAirCompanyId()),airplaneMapper.toDto(flight.getAirplaneId()),flight.getDepartureCountry(), flight.getDestinationCountry(), flight.getDistance(), flight.getEstimatedFlightTime(), flight.getStartedAt(),flight.getEndedAt(),flight.getDelayStartedAt(),flight.getCreatedAt());
    }
}
