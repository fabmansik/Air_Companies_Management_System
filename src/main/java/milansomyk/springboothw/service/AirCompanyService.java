package milansomyk.springboothw.service;

import milansomyk.springboothw.dto.AirCompanyDto;
import milansomyk.springboothw.dto.ResponseContainer;
import milansomyk.springboothw.entity.AirCompany;
import milansomyk.springboothw.mapper.AirCompanyMapper;
import milansomyk.springboothw.repository.AirCompanyRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class AirCompanyService {
    private final AirCompanyRepository airCompanyRepository;
    private final AirCompanyMapper airCompanyMapper;
    public AirCompanyService(AirCompanyRepository airCompanyRepository, AirCompanyMapper airCompanyMapper) {
        this.airCompanyRepository = airCompanyRepository;
        this.airCompanyMapper = airCompanyMapper;
    }
    public ResponseContainer getAll(){
        ResponseContainer responseContainer = new ResponseContainer();
        List<AirCompany> companies;
        try {
            companies = airCompanyRepository.findAll();
        } catch (Exception e){
            return responseContainer.setErrorMessageAndStatusCode(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        List<AirCompanyDto> mappedList = companies.stream().map(airCompanyMapper::toDto).toList();
        return responseContainer.setSuccessResult(mappedList);
    }
    public ResponseContainer getById(int id){
        ResponseContainer responseContainer = new ResponseContainer();
        AirCompany foundCompany;
        try {
            foundCompany = airCompanyRepository.findById(id).orElse(null);
        } catch (Exception e){
            return responseContainer.setErrorMessageAndStatusCode(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        if(ObjectUtils.isEmpty(foundCompany)){
            return responseContainer.setErrorMessageAndStatusCode("company not found",HttpStatus.BAD_REQUEST.value());
        }
        return responseContainer.setSuccessResult(airCompanyMapper.toDto(foundCompany));
    }
    public ResponseContainer createAirCompany(AirCompanyDto airCompanyDto){
        ResponseContainer responseContainer = new ResponseContainer();
        if(airCompanyDto.allNull()){
            return responseContainer.setErrorMessageAndStatusCode("company is empty",HttpStatus.BAD_REQUEST.value());
        }
        AirCompany found;
        try {
            found = airCompanyRepository.save(airCompanyMapper.fromDto(airCompanyDto));
        } catch (Exception e){
            return responseContainer.setErrorMessageAndStatusCode(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return responseContainer.setSuccessResult(airCompanyMapper.toDto(found));
    }
    public ResponseContainer updateAirCompany(int id, AirCompanyDto airCompanyDto){
        ResponseContainer responseContainer = new ResponseContainer();
        if(airCompanyDto.allNull()){
            return responseContainer.setErrorMessageAndStatusCode("airCompany param is empty", HttpStatus.BAD_REQUEST.value());
        }
        if(ObjectUtils.isEmpty(id)){
            return responseContainer.setErrorMessageAndStatusCode("id is empty",HttpStatus.BAD_REQUEST.value());
        }
        int rowsUpdated;
        try {
           rowsUpdated = airCompanyRepository.updateAirCompanyById(id, airCompanyDto.getName(),airCompanyDto.getCompanyType(),airCompanyDto.getFoundedAt());
        }catch (Exception e){
            return responseContainer.setErrorMessageAndStatusCode(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        if(rowsUpdated==0){
            return responseContainer.setErrorMessageAndStatusCode("company not found",HttpStatus.BAD_REQUEST.value());
        }
        return responseContainer.setSuccessResult("updated");
    }
    public ResponseContainer deleteById(int id){
        ResponseContainer responseContainer = new ResponseContainer();
        AirCompany found;
        try{
            found = airCompanyRepository.findById(id).orElse(null);
        } catch (Exception e){
            return responseContainer.setErrorMessageAndStatusCode(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        if(ObjectUtils.isEmpty(found)){
            return responseContainer.setErrorMessageAndStatusCode("company not found",HttpStatus.BAD_REQUEST.value());
        }
        try {
            airCompanyRepository.deleteById(id);
        } catch (Exception e){
            return responseContainer.setErrorMessageAndStatusCode(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return responseContainer.setSuccessResult("company deleted");
    }
}
