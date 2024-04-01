package milansomyk.springboothw.service;

import milansomyk.springboothw.dto.ResponseContainer;
import milansomyk.springboothw.entity.AirCompany;
import milansomyk.springboothw.repository.AirCompanyRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class AirCompanyService {
    private final AirCompanyRepository airCompanyRepository;
    public AirCompanyService(AirCompanyRepository airCompanyRepository) {
        this.airCompanyRepository = airCompanyRepository;
    }
    public ResponseContainer getAll(){
        ResponseContainer responseContainer = new ResponseContainer();
        List<AirCompany> companies;
        try {
            companies = airCompanyRepository.findAll();
        } catch (Exception e){
            return responseContainer.setErrorMessageAndStatusCode(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return responseContainer.setSuccessResult(companies);
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
        return responseContainer.setSuccessResult(foundCompany);
    }
    public ResponseContainer createAirCompany(AirCompany airCompany){
        ResponseContainer responseContainer = new ResponseContainer();
        if(ObjectUtils.isEmpty(airCompany)){
            return responseContainer.setErrorMessageAndStatusCode("company is empty",HttpStatus.BAD_REQUEST.value());
        }
        AirCompany found;
        try {
            found = airCompanyRepository.save(airCompany);
        } catch (Exception e){
            return responseContainer.setErrorMessageAndStatusCode(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return responseContainer.setSuccessResult(found);
    }
}
