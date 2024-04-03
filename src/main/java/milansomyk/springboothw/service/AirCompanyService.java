package milansomyk.springboothw.service;

import lombok.extern.slf4j.Slf4j;
import milansomyk.springboothw.dto.AirCompanyDto;
import milansomyk.springboothw.dto.ResponseContainer;
import milansomyk.springboothw.entity.AirCompany;
import milansomyk.springboothw.mapper.AirCompanyMapper;
import milansomyk.springboothw.repository.AirCompanyRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@Slf4j
public class AirCompanyService {
    private final AirCompanyRepository airCompanyRepository;
    private final AirCompanyMapper airCompanyMapper;
    private final AirplaneService airplaneService;
    private final FlightService flightService;

    public AirCompanyService(AirCompanyRepository airCompanyRepository, AirCompanyMapper airCompanyMapper, @Lazy AirplaneService airplaneService, @Lazy FlightService flightService) {
        this.airCompanyRepository = airCompanyRepository;
        this.airCompanyMapper = airCompanyMapper;
        this.airplaneService = airplaneService;
        this.flightService = flightService;
    }

    public ResponseContainer getAll(){
        ResponseContainer responseContainer = new ResponseContainer();
        List<AirCompany> companies;
        try {
            companies = airCompanyRepository.findAll();
        } catch (Exception e){
            log.error(e.getMessage());
            return responseContainer.setErrorMessageAndStatusCode(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        List<AirCompanyDto> mappedList = companies.stream().map(airCompanyMapper::toDto).toList();
        return responseContainer.setSuccessResult(mappedList);
    }
    public ResponseContainer getByName(String name){
        ResponseContainer responseContainer = new ResponseContainer();
        AirCompany airCompany;
        if(!StringUtils.hasText(name)){
            log.error("AirCompany name was not given!");
            return responseContainer.setErrorMessageAndStatusCode("AirCompany name was not given!",HttpStatus.BAD_REQUEST.value());
        }
        try{
            airCompany = airCompanyRepository.findByName(name).orElse(null);
        } catch (Exception e){
            log.error(e.getMessage());
            return responseContainer.setErrorMessageAndStatusCode(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        if(ObjectUtils.isEmpty(airCompany)){
            log.error("AirCompany by this name was not found!");
            return responseContainer.setErrorMessageAndStatusCode("AirCompany by this name was not found!", HttpStatus.BAD_REQUEST.value());
        }
        return responseContainer.setSuccessResult(airCompanyMapper.toDto(airCompany));
    }
    public ResponseContainer createAirCompany(AirCompanyDto airCompanyDto){
        ResponseContainer responseContainer = new ResponseContainer();
        if(airCompanyDto.anyNull()){
            log.error("Not given enough AirCompany parameters!");
            return responseContainer.setErrorMessageAndStatusCode("Not given enough AirCompany parameters!",HttpStatus.BAD_REQUEST.value());
        }
        try {
            airCompanyRepository.save(airCompanyMapper.fromDto(airCompanyDto));
        } catch (Exception e){
            log.error(e.getMessage());
            return responseContainer.setErrorMessageAndStatusCode(e.getCause().getCause().getMessage(),HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return responseContainer.setCreatedResult("AirCompany created!");
    }
    public ResponseContainer updateAirCompany(String companyName, AirCompanyDto airCompanyDto){
        ResponseContainer responseContainer = new ResponseContainer();
        if(airCompanyDto.allNull()){
            log.error("Not given any AirCompany parameters!");
            return responseContainer.setErrorMessageAndStatusCode("Not given any AirCompany parameters!", HttpStatus.BAD_REQUEST.value());
        }
        if(ObjectUtils.isEmpty(companyName)){
            log.error("Not given searched AirCompany name!");
            return responseContainer.setErrorMessageAndStatusCode("Not given searched AirCompany name!",HttpStatus.BAD_REQUEST.value());
        }
        AirCompany foundCompany;
        try{
           foundCompany = airCompanyRepository.findByName(companyName).orElse(null);
        }catch (Exception e){
            log.error(e.getMessage());
            return responseContainer.setErrorMessageAndStatusCode(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        if(ObjectUtils.isEmpty(foundCompany)){
            log.error("AirCompany not found!");
            return responseContainer.setErrorMessageAndStatusCode("AirCompany not found!",HttpStatus.BAD_REQUEST.value());
        }
        if(StringUtils.hasText(airCompanyDto.getName())){
            foundCompany.setName(airCompanyDto.getName());
        }
        if(StringUtils.hasText(airCompanyDto.getCompanyType())){
            foundCompany.setCompanyType(airCompanyDto.getCompanyType());
        }
        if(!ObjectUtils.isEmpty(airCompanyDto.getFoundedAt())){
            foundCompany.setFoundedAt(airCompanyDto.getFoundedAt());
        }
        try {
           airCompanyRepository.save(foundCompany);
        }catch (Exception e){
            log.error(e.getMessage());
            return responseContainer.setErrorMessageAndStatusCode(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return responseContainer.setSuccessResult("AirCompany updated successfully!");
    }
    public ResponseContainer deleteByCompanyName(String companyName){
        ResponseContainer responseContainer = new ResponseContainer();
        if(!StringUtils.hasText(companyName)){
            log.error("Not given AirCompany name!");
            return responseContainer.setErrorMessageAndStatusCode("Not given AirCompany name!", HttpStatus.BAD_REQUEST.value());
        }
        AirCompany foundCompany;
        try{
            foundCompany = airCompanyRepository.findByName(companyName).orElse(null);
        }catch (Exception e){
            log.error(e.getMessage());
            return responseContainer.setErrorMessageAndStatusCode(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        if(ObjectUtils.isEmpty(foundCompany)){
            log.error("AirCompany by this name was not found!");
            return responseContainer.setErrorMessageAndStatusCode("AirCompany by this name was not found!", HttpStatus.BAD_REQUEST.value());
        }
        try{
            airplaneService.deleteCompanyFromAirplane(foundCompany.getId());
        }catch (Exception e){
            log.error(e.getMessage());
            return responseContainer.setErrorMessageAndStatusCode(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        try{
            flightService.deleteCompanyFromFlight(foundCompany.getId());
        }catch (Exception e){
            log.error(e.getMessage());
            return responseContainer.setErrorMessageAndStatusCode(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        try {
            airCompanyRepository.deleteByName(companyName);
        } catch (Exception e){
            log.error(e.getMessage());
            return responseContainer.setErrorMessageAndStatusCode(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return responseContainer.setSuccessResult("AirCompany deleted successfully!");
    }
    public AirCompanyDto getAirCompanyByName(String name) {
        if(!StringUtils.hasText(name)) throw new IllegalArgumentException("Not given AirCompany name!");
        AirCompany airCompany = airCompanyRepository.findByName(name).orElse(null);
        if(ObjectUtils.isEmpty(airCompany)) throw new IllegalArgumentException("AirCompany by this name not found!");
        return airCompanyMapper.toDto(airCompany);
    }
}
