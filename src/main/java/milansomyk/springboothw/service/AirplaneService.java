package milansomyk.springboothw.service;

import milansomyk.springboothw.dto.AirCompanyDto;
import milansomyk.springboothw.dto.AirplaneDto;
import milansomyk.springboothw.dto.ResponseContainer;
import milansomyk.springboothw.entity.AirCompany;
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
    private final AirCompanyRepository airCompanyRepository;
    public AirplaneService(AirplaneRepository airplaneRepository, AirplaneMapper airplaneMapper, AirCompanyRepository airCompanyRepository){
        this.airplaneRepository = airplaneRepository;
        this.airplaneMapper = airplaneMapper;
        this.airCompanyRepository = airCompanyRepository;
    }
    //in progress...
    public ResponseContainer createPlane(Integer id, AirplaneDto airplaneDto){
        ResponseContainer responseContainer = new ResponseContainer();
        if(airplaneDto.allNull()){
            return responseContainer.setErrorMessageAndStatusCode("airplane is empty",HttpStatus.BAD_REQUEST.value());
        }
        if(!ObjectUtils.isEmpty(id)){
            AirCompany found;
            try{
                found = airCompanyRepository.findById(id).orElse(null);
            } catch (Exception e){
                return responseContainer.setErrorMessageAndStatusCode(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR.value());
            }
            if(ObjectUtils.isEmpty(found)) return responseContainer.setErrorMessageAndStatusCode("company not found",HttpStatus.BAD_REQUEST.value());
            airplaneDto.setAirCompanyId(found);
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
