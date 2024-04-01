package milansomyk.springboothw.service;

import milansomyk.springboothw.dto.FlightDto;
import milansomyk.springboothw.dto.ResponseContainer;
import milansomyk.springboothw.mapper.FlightMapper;
import milansomyk.springboothw.repository.FlightRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class FlightService {
    private final FlightRepository flightRepository;
    private final FlightMapper flightMapper;

    public FlightService(FlightRepository flightRepository, FlightMapper flightMapper) {
        this.flightRepository = flightRepository;
        this.flightMapper = flightMapper;
    }
    public ResponseContainer addFlight(FlightDto flightDto){
        ResponseContainer responseContainer = new ResponseContainer();
        if(flightDto.allNull()){
            return responseContainer.setErrorMessageAndStatusCode("not full information about flight", HttpStatus.BAD_REQUEST.value());
        }
        flightDto.getAirplaneId();
        try {
            flightRepository.save(flightMapper.fromDto(flightDto));
        } catch (Exception e){
            return responseContainer.setErrorMessageAndStatusCode(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return responseContainer.setSuccessResult(null);
    }
}
