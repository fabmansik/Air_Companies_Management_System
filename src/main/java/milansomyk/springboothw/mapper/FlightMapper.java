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
        if(ObjectUtils.isEmpty(flightDto.getAirCompanyId())){
            return new Flight(flightDto.getId(), flightDto.getFlightStatus(), null,airplaneMapper.fromDto(flightDto.getAirplaneId()),flightDto.getDepartureCountry(),flightDto.getDestinationCountry(),flightDto.getDistance(),flightDto.getEstimatedFlightTime(),flightDto.getStartedAt(),flightDto.getEndedAt(),flightDto.getDelayStartedAt(),flightDto.getCreatedAt());
        }
        return new Flight(flightDto.getId(), flightDto.getFlightStatus(), airCompanyMapper.fromDto(flightDto.getAirCompanyId()),airplaneMapper.fromDto(flightDto.getAirplaneId()),flightDto.getDepartureCountry(),flightDto.getDestinationCountry(),flightDto.getDistance(),flightDto.getEstimatedFlightTime(),flightDto.getStartedAt(),flightDto.getEndedAt(),flightDto.getDelayStartedAt(),flightDto.getCreatedAt());
    }
    public FlightDto toDto(Flight flight){
        if(ObjectUtils.isEmpty(flight.getAirCompanyId())){
            return new FlightDto(flight.getId(), flight.getFlightStatus(), null,airplaneMapper.toDto(flight.getAirplaneId()),flight.getDepartureCountry(), flight.getDestinationCountry(), flight.getDistance(), flight.getEstimatedFlightTime(), flight.getStartedAt(),flight.getEndedAt(),flight.getDelayStartedAt(),flight.getCreatedAt());
        }
        return new FlightDto(flight.getId(), flight.getFlightStatus(), airCompanyMapper.toDto(flight.getAirCompanyId()),airplaneMapper.toDto(flight.getAirplaneId()),flight.getDepartureCountry(), flight.getDestinationCountry(), flight.getDistance(), flight.getEstimatedFlightTime(), flight.getStartedAt(),flight.getEndedAt(),flight.getDelayStartedAt(),flight.getCreatedAt());
    }
}
