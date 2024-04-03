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
import org.springframework.util.ObjectUtils;
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
        if (airplaneDto.anyRequiredIsNull()) {
            log.error("Given not enough parameters to create Airplane!");
            return responseContainer.setErrorMessageAndStatusCode("Given not enough parameters to create Airplane!", HttpStatus.BAD_REQUEST.value());
        }
        if (StringUtils.hasText(companyName)) {
            AirCompanyDto foundAirCompany;
            try {
                foundAirCompany = airCompanyService.getAirCompanyByName(companyName);
            } catch (IllegalArgumentException e){
                log.error(e.getMessage());
                return responseContainer.setErrorMessageAndStatusCode(e.getMessage(),HttpStatus.BAD_REQUEST.value());
            }catch (Exception e){
                log.error(e.getMessage());
                return responseContainer.setErrorMessageAndStatusCode(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR.value());
            }
            airplaneDto.setAirCompanyId(foundAirCompany);
        }
        try {
            airplaneRepository.save(airplaneMapper.fromDto(airplaneDto));
        } catch (Exception e) {
            log.error(e.getMessage());
            return responseContainer.setErrorMessageAndStatusCode(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return responseContainer.setCreatedResult("Airplane was created!");
    }
    public ResponseContainer movePlane(String companyName, String serialNum) {
        ResponseContainer responseContainer = new ResponseContainer();
        AirCompanyDto foundAirCompany;
        if(!StringUtils.hasText(companyName)){
            log.error("Not given AirCompany name!");
            return responseContainer.setErrorMessageAndStatusCode("Not given AirCompany name!",HttpStatus.BAD_REQUEST.value());
        }
        if(!StringUtils.hasText(serialNum)){
            log.error("Not given Airplane serial number!");
            return responseContainer.setErrorMessageAndStatusCode("Not given Airplane serial number!",HttpStatus.BAD_REQUEST.value());
        }
        try{
            foundAirCompany = airCompanyService.getAirCompanyByName(companyName);
        }catch (IllegalArgumentException e){
            log.error(e.getMessage());
            return responseContainer.setErrorMessageAndStatusCode(e.getMessage(),HttpStatus.BAD_REQUEST.value());
        }catch (Exception e){
            log.error(e.getMessage());
            return responseContainer.setErrorMessageAndStatusCode(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        try{
            airplaneRepository.updateByFactorySerialNumber(foundAirCompany.getId(), serialNum);
        }catch (Exception e){
            return responseContainer.setErrorMessageAndStatusCode(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return responseContainer.setSuccessResult("Airplane moved to another AirCompany successfully!");
    }
    public AirplaneDto getAirplaneDtoBySerialNumber(String serialNumber){
        if(!StringUtils.hasText(serialNumber)) throw new IllegalArgumentException("Not given Airplane serial number!");
        Airplane airplane = airplaneRepository.findByFactorySerialNumber(serialNumber).orElse(null);
        if(ObjectUtils.isEmpty(airplane)) throw new IllegalArgumentException("Airplane by this serial number was not found!");
        return airplaneMapper.toDto(airplane);
    }
    public void deleteCompanyFromAirplane(int companyId){
        airplaneRepository.deleteCompanyIdFromAirplane(companyId);
    }
}
