package milansomyk.springboothw.service;

import milansomyk.springboothw.dto.AirplaneDto;
import milansomyk.springboothw.dto.ResponseContainer;
import milansomyk.springboothw.entity.Airplane;
import milansomyk.springboothw.mapper.AirplaneMapper;
import milansomyk.springboothw.repository.AirCompanyRepository;
import milansomyk.springboothw.repository.AirplaneRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class AirplaneService {
    private final AirplaneRepository airplaneRepository;
    private final AirplaneMapper airplaneMapper;
    public AirplaneService(AirplaneRepository airplaneRepository, AirplaneMapper airplaneMapper){
        this.airplaneRepository = airplaneRepository;
        this.airplaneMapper = airplaneMapper;
    }
    //in progress...
    public ResponseContainer createPlane(int id, AirplaneDto airplaneDto){
        ResponseContainer responseContainer = new ResponseContainer();
        if(airplaneDto.allNull()){
            return responseContainer.setErrorMessageAndStatusCode("airplane is empty",HttpStatus.BAD_REQUEST.value());
        }
        if(!ObjectUtils.isEmpty(id)){
//            AirCompany airCompany = airCompanyRepository.findById(id).orElse(null);
//            airplaneDto.setAirCompanyId(airCompany);
            airplaneRepository.saveWithCompany(id, airplaneDto.getName(), airplaneDto.getFactorySerialNumber(), airplaneDto.getNumberOfFlights(), airplaneDto.getFlightDistance(), airplaneDto.getFuelCapacity(), airplaneDto.getType(), airplaneDto.getCreatedAt());
        }
        Airplane saved;
        try {
            saved = airplaneRepository.save(airplaneMapper.fromDto(airplaneDto));
        } catch (Exception e){
            return responseContainer.setErrorMessageAndStatusCode(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return responseContainer.setSuccessResult(airplaneMapper.toDto(saved));
    }
}
