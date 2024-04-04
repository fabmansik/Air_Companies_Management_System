package milansomyk.springboothw.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import milansomyk.springboothw.dto.AirCompanyDto;
import milansomyk.springboothw.dto.AirplaneDto;
import milansomyk.springboothw.dto.ResponseContainer;
import milansomyk.springboothw.entity.Airplane;
import milansomyk.springboothw.mapper.AirplaneMapper;
import milansomyk.springboothw.repository.AirplaneRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
@Service
@Slf4j
public class AirplaneService {
    private final AirplaneRepository airplaneRepository;
    private final AirplaneMapper airplaneMapper;
    private final AirCompanyService airCompanyService;

    public ResponseContainer createPlane(String companyName, AirplaneDto airplaneDto) {
        ResponseContainer responseContainer = new ResponseContainer();
        if (airplaneDto == null || airplaneDto.anyRequiredIsNull()) {
            log.error("Given Airplane is NULL or has empty body!");
            return responseContainer.setErrorMessageAndStatusCode("Given Airplane is NULL or has empty body!", HttpStatus.BAD_REQUEST.value());
        }
        if (StringUtils.hasText(companyName)) {
            ResponseContainer airCompanyByNameResponse = airCompanyService.getAirCompanyByName(companyName);
            if(airCompanyByNameResponse.isError()) return airCompanyByNameResponse;
            AirCompanyDto foundAirCompany = (AirCompanyDto) airCompanyByNameResponse.getResult();
            airplaneDto.setAirCompanyId(foundAirCompany);
        }
        try {
            airplaneRepository.save(airplaneMapper.fromDto(airplaneDto));
        } catch (Exception e) {
            log.error("Exception error when trying to save AirCompany by name {}; Error:{}", companyName, e.getMessage());
            return responseContainer.setErrorMessageAndStatusCode(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return responseContainer.setCreatedResult("Airplane was created!");
    }

    public ResponseContainer movePlane(String companyName, String serialNum) {
        ResponseContainer responseContainer = new ResponseContainer();

        if (!StringUtils.hasText(companyName)) {
            log.error("Not given AirCompany name!");
            return responseContainer.setErrorMessageAndStatusCode("Not given AirCompany name!", HttpStatus.BAD_REQUEST.value());
        }
        if (!StringUtils.hasText(serialNum)) {
            log.error("Not given Airplane serial number!");
            return responseContainer.setErrorMessageAndStatusCode("Not given Airplane serial number!", HttpStatus.BAD_REQUEST.value());
        }

        ResponseContainer airCompanyByNameResponse = airCompanyService.getAirCompanyByName(companyName);
        if(airCompanyByNameResponse.isError()) return airCompanyByNameResponse;
        AirCompanyDto foundAirCompany = (AirCompanyDto) airCompanyByNameResponse.getResult();
        Integer updatedAirplanes;
        try {
            updatedAirplanes = airplaneRepository.updateByFactorySerialNumber(foundAirCompany.getId(), serialNum);
        } catch (Exception e) {
            log.error("Exception error when trying to update AirPlane by serial number {}; AirCompany id: {}; Error:{}", serialNum, foundAirCompany.getId(), e.getMessage());
            return responseContainer.setErrorMessageAndStatusCode(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        if(updatedAirplanes==0){
            log.error("Airplane with this serial number not found! Serial number: {}",serialNum);
            return responseContainer.setErrorMessageAndStatusCode("Airplane with this serial number not found!",HttpStatus.BAD_REQUEST.value());
        }
        return responseContainer.setSuccessResult("Airplane moved to another AirCompany successfully!");
    }

    public ResponseContainer getAirplaneDtoBySerialNumber(String serialNumber) {
        ResponseContainer responseContainer = new ResponseContainer();
        if(!StringUtils.hasText(serialNumber)){
            log.error("Given serial number is empty!");
            return responseContainer.setErrorMessageAndStatusCode("Given serial number is empty!",HttpStatus.BAD_REQUEST.value());
        }
        Airplane airplane;
        try{
            airplane = airplaneRepository.findByFactorySerialNumber(serialNumber).orElse(null);
        }catch (Exception e){
            log.error("Exception error when trying to find Airplane by serial number! Serial number: {}; Error: {}",serialNumber, e.getMessage());
            return responseContainer.setErrorMessageAndStatusCode(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        if(airplane==null){
            log.error("Airplane with this serial number not found! Serial number: {}",serialNumber);
            return responseContainer.setErrorMessageAndStatusCode("Airplane with this serial number not found!",HttpStatus.BAD_REQUEST.value());
        }
        return responseContainer.setSuccessResult(airplaneMapper.toDto(airplane));
    }

    public ResponseContainer deleteCompanyFromAirplane(Integer companyId) {
        ResponseContainer responseContainer = new ResponseContainer();
        if (companyId == null) {
            log.error("Given AirCompany id is NULL!");
            return responseContainer.setErrorMessageAndStatusCode("Given AirCompany id is NULL!", HttpStatus.BAD_REQUEST.value());
        }
        try {
            airplaneRepository.deleteCompanyIdFromAirplane(companyId);
        } catch (Exception e) {
            log.error("Exception error when trying to delete Company from airplanes; Company id: {}; Error:{}", companyId, e.getMessage());
            return responseContainer.setErrorMessageAndStatusCode(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return responseContainer;
    }

}
