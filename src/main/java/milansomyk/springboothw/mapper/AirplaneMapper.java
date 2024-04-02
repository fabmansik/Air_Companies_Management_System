package milansomyk.springboothw.mapper;

import milansomyk.springboothw.dto.AirplaneDto;
import milansomyk.springboothw.entity.Airplane;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class AirplaneMapper {
    private final AirCompanyMapper airCompanyMapper;

    public AirplaneMapper(AirCompanyMapper airCompanyMapper) {
        this.airCompanyMapper = airCompanyMapper;
    }

    public Airplane fromDto(AirplaneDto airplaneDto){
        if(ObjectUtils.isEmpty(airplaneDto.getAirCompanyId())){
            return new Airplane(airplaneDto.getId(),airplaneDto.getName(),airplaneDto.getFactorySerialNumber(),null,airplaneDto.getNumberOfFlights(),airplaneDto.getFlightDistance(),airplaneDto.getFuelCapacity(),airplaneDto.getType(),airplaneDto.getCreatedAt());
        }
        return new Airplane(airplaneDto.getId(),airplaneDto.getName(),airplaneDto.getFactorySerialNumber(),airCompanyMapper.fromDto(airplaneDto.getAirCompanyId()),airplaneDto.getNumberOfFlights(),airplaneDto.getFlightDistance(),airplaneDto.getFuelCapacity(),airplaneDto.getType(),airplaneDto.getCreatedAt());
    }
    public AirplaneDto toDto(Airplane airplane){
        if(ObjectUtils.isEmpty(airplane.getAirCompanyId())){
            return new AirplaneDto(airplane.getId(),airplane.getName(),airplane.getFactorySerialNumber(),null,airplane.getNumberOfFlights(),airplane.getFlightDistance(),airplane.getFuelCapacity(),airplane.getType(),airplane.getCreatedAt());
        }
        return new AirplaneDto(airplane.getId(),airplane.getName(),airplane.getFactorySerialNumber(),airCompanyMapper.toDto(airplane.getAirCompanyId()),airplane.getNumberOfFlights(),airplane.getFlightDistance(),airplane.getFuelCapacity(),airplane.getType(),airplane.getCreatedAt());
    }
}
