package milansomyk.springboothw.mapper;

import milansomyk.springboothw.dto.AirplaneDto;
import milansomyk.springboothw.entity.Airplane;
import org.springframework.stereotype.Component;

@Component
public class AirplaneMapper {
    public Airplane fromDto(AirplaneDto airplaneDto){
        return new Airplane(airplaneDto.getId(),airplaneDto.getName(),airplaneDto.getFactorySerialNumber(),airplaneDto.getAirCompanyId(),airplaneDto.getNumberOfFlights(),airplaneDto.getFlightDistance(),airplaneDto.getFuelCapacity(),airplaneDto.getType(),airplaneDto.getCreatedAt());
    }
    public AirplaneDto toDto(Airplane airplane){
        return new AirplaneDto(airplane.getId(),airplane.getName(),airplane.getFactorySerialNumber(),airplane.getAirCompanyId(),airplane.getNumberOfFlights(),airplane.getFlightDistance(),airplane.getFuelCapacity(),airplane.getType(),airplane.getCreatedAt());
    }
}
