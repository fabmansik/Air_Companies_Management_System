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
import org.springframework.util.StringUtils;

@Service
public class AirplaneService {
    private final AirplaneRepository airplaneRepository;
    private final AirplaneMapper airplaneMapper;
    private final AirCompanyService airCompanyService;

    public AirplaneService(AirplaneRepository airplaneRepository, AirplaneMapper airplaneMapper, AirCompanyService airCompanyService) {
        this.airplaneRepository = airplaneRepository;
        this.airplaneMapper = airplaneMapper;
        this.airCompanyService = airCompanyService;
    }

    public ResponseContainer createPlane(String companyName, AirplaneDto airplaneDto) {
        ResponseContainer responseContainer = new ResponseContainer();
        if (airplaneDto.allNull()) {
            return responseContainer.setErrorMessageAndStatusCode("airplane is empty", HttpStatus.BAD_REQUEST.value());
        }
        if (StringUtils.hasText(companyName)) {
            responseContainer = airCompanyService.getByName(companyName);
            if (responseContainer.isError()) return responseContainer;
            airplaneDto.setAirCompanyId((AirCompanyDto) responseContainer.getResult());
        }
        Airplane saved;
        try {
            saved = airplaneRepository.save(airplaneMapper.fromDto(airplaneDto));
        } catch (Exception e) {
            responseContainer.setResult(null);
            return responseContainer.setErrorMessageAndStatusCode(e.getCause().getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return responseContainer.setSuccessResult(airplaneMapper.toDto(saved));
    }

    public ResponseContainer getBySerialNumber(String serialNumber) {
        ResponseContainer responseContainer = new ResponseContainer();
        Airplane airplane;
        try {
            airplane = airplaneRepository.findByFactorySerialNumber(serialNumber).orElse(null);
        } catch (Exception e) {
            return responseContainer.setErrorMessageAndStatusCode(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        if (ObjectUtils.isEmpty(airplane)) {
            return responseContainer.setErrorMessageAndStatusCode("plane not found by serial number", HttpStatus.BAD_REQUEST.value());
        }
        return responseContainer.setSuccessResult(airplaneMapper.toDto(airplane));
    }

    public ResponseContainer movePlane(String companyName, String serialNum) {
        ResponseContainer responseContainer = new ResponseContainer();
        ResponseContainer byName = airCompanyService.getByName(companyName);
        if (byName.isError()) return byName;
        AirCompanyDto companyDto = (AirCompanyDto) byName.getResult();
        int result;
        try{
            result = airplaneRepository.updateByFactorySerialNumber(companyDto.getId(), serialNum);
        }catch (Exception e){
            return responseContainer.setErrorMessageAndStatusCode(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        if(result==0)return responseContainer.setErrorMessageAndStatusCode("airplane not found!", HttpStatus.BAD_REQUEST.value());
        return responseContainer.setSuccessResult("airplane moved!");
    }
}
