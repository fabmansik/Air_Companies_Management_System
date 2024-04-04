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
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Collections;
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

    public ResponseContainer getAllAirCompanies() {
        ResponseContainer responseContainer = new ResponseContainer();
        List<AirCompany> companies;
        try {
            companies = airCompanyRepository.findAll();
        } catch (Exception e) {
            log.error("Exception error when trying get all AirCompanies; Error: {}", e.getMessage());
            return responseContainer.setErrorMessageAndStatusCode(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        List<AirCompanyDto> mappedList =
                CollectionUtils.isEmpty(companies) ?
                        Collections.emptyList() :
                        companies.stream().map(airCompanyMapper::toDto).toList();
        return responseContainer.setSuccessResult(mappedList);
    }

    public ResponseContainer getCompanyByName(String name) {
        ResponseContainer responseContainer = new ResponseContainer();
        AirCompany airCompany;
        if (!StringUtils.hasText(name)) {
            log.error("Empty AirCompany name parameter!");
            return responseContainer.setErrorMessageAndStatusCode("Empty AirCompany name parameter!", HttpStatus.BAD_REQUEST.value());
        }
        try {
            airCompany = airCompanyRepository.findByName(name).orElse(null);
        } catch (Exception e) {
            log.error("Exception error when trying get AirCompany by name: {}; Error: {}", name, e.getMessage());
            return responseContainer.setErrorMessageAndStatusCode(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        if (ObjectUtils.isEmpty(airCompany)) {
            log.error("Exception error when trying get AirCompany by name: {}", name);
            return responseContainer.setErrorMessageAndStatusCode("AirCompany by this name was not found!", HttpStatus.BAD_REQUEST.value());
        }
        return responseContainer.setSuccessResult(airCompanyMapper.toDto(airCompany));
    }

    public ResponseContainer createAirCompany(AirCompanyDto airCompanyDto) {
        ResponseContainer responseContainer = new ResponseContainer();
        if (airCompanyDto == null || airCompanyDto.anyNull()) {
            log.error("Not given enough AirCompany parameters! AirCompanyDto: {}", airCompanyDto);
            return responseContainer.setErrorMessageAndStatusCode("Not given enough AirCompany parameters!", HttpStatus.BAD_REQUEST.value());
        }
        AirCompanyDto airCompany = getAirCompany(airCompanyDto.getName());
        if (airCompany == null) {
            try {
                airCompanyRepository.save(airCompanyMapper.fromDto(airCompanyDto));
            } catch (Exception e) {
                log.error("Exception error when trying to create air company; Error: {}", e.getMessage());
                return responseContainer.setErrorMessageAndStatusCode(e.getCause().getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
            }
            return responseContainer.setCreatedResult("AirCompany created!");
        } else
            return responseContainer.setErrorMessageAndStatusCode("AirCompany is already exists!", HttpStatus.BAD_REQUEST.value());
    }

    public ResponseContainer updateAirCompany(String companyName, AirCompanyDto airCompanyDto) {
        ResponseContainer responseContainer = new ResponseContainer();
        if (airCompanyDto == null || airCompanyDto.anyNull()) {
            log.error("AirCompany is null or AirCompany has empty body!; Air Company: {}", airCompanyDto);
            return responseContainer.setErrorMessageAndStatusCode("AirCompany is null or some fields of object are null!", HttpStatus.BAD_REQUEST.value());
        }
        if (companyName == null) {
            log.error("Given AirCompany name parameter is NULL!");
            return responseContainer.setErrorMessageAndStatusCode("Given AirCompany name parameter is NULL!", HttpStatus.BAD_REQUEST.value());
        }
        AirCompany foundCompany;
        try {
            foundCompany = airCompanyRepository.findByName(companyName).orElse(null);
        } catch (Exception e) {
            log.error("Exception error when trying to find AirCompany by name: {}; Error: {}", companyName, e.getMessage());
            return responseContainer.setErrorMessageAndStatusCode(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        if (ObjectUtils.isEmpty(foundCompany)) {
            log.error("Found AirCompany is NULL!");
            return responseContainer.setErrorMessageAndStatusCode("Found AirCompany is NULL!", HttpStatus.BAD_REQUEST.value());
        }

        foundCompany.setName(airCompanyDto.getName());
        foundCompany.setCompanyType(airCompanyDto.getCompanyType());
        foundCompany.setFoundedAt(airCompanyDto.getFoundedAt());

        try {
            airCompanyRepository.save(foundCompany);
        } catch (Exception e) {
            log.error("Exception error when trying to update AirCompany: {}; Error: {}",foundCompany,e.getMessage());
            return responseContainer.setErrorMessageAndStatusCode(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return responseContainer.setSuccessResult("AirCompany updated successfully!");
    }

    public ResponseContainer deleteByCompanyName(String companyName) {
        ResponseContainer responseContainer = new ResponseContainer();
        if (!StringUtils.hasText(companyName)) {
            log.error("Given AirCompany name is NULL!");
            return responseContainer.setErrorMessageAndStatusCode("Given AirCompany name is NULL!", HttpStatus.BAD_REQUEST.value());
        }
        AirCompany foundCompany;
        try {
            foundCompany = airCompanyRepository.findByName(companyName).orElse(null);
        } catch (Exception e) {
            log.error("Exception error when trying to find AirCompany by name: {}; Error: {}",companyName,e.getMessage());
            return responseContainer.setErrorMessageAndStatusCode(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        if (ObjectUtils.isEmpty(foundCompany)) {
            log.error("AirCompany by this name was not found!");
            return responseContainer.setErrorMessageAndStatusCode("AirCompany by this name was not found!", HttpStatus.BAD_REQUEST.value());
        }
        ResponseContainer deleteCompanyFromAirplaneResponse;
        try {
            deleteCompanyFromAirplaneResponse = airplaneService.deleteCompanyFromAirplane(foundCompany.getId());
        } catch (Exception e) {
            log.error("Exception error when trying to delete company for airplane; Company ID: {}; Error: {}", foundCompany.getId(), e.getMessage());
            return responseContainer.setErrorMessageAndStatusCode(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        if(deleteCompanyFromAirplaneResponse.isError()) return deleteCompanyFromAirplaneResponse;

        ResponseContainer deleteCompanyFromFlightResponse;
        try {
            deleteCompanyFromFlightResponse = flightService.deleteCompanyFromFlight(foundCompany.getId());
        } catch (Exception e) {
            log.error("Exception error when trying to delete company from flight; Company ID: {}; Error: {}", foundCompany.getId(), e.getMessage());
            return responseContainer.setErrorMessageAndStatusCode(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        if (deleteCompanyFromAirplaneResponse.isError()) return deleteCompanyFromFlightResponse;

        try {
            airCompanyRepository.deleteByName(companyName);
        } catch (Exception e) {
            log.error("Exception error when trying to delete company; Company Name: {}; Error: {}", companyName, e.getMessage());
            return responseContainer.setErrorMessageAndStatusCode(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return responseContainer.setSuccessResult("AirCompany deleted successfully!");
    }

    public ResponseContainer getAirCompanyByName(String name) {
        ResponseContainer responseContainer = new ResponseContainer();
        if (!StringUtils.hasText(name)){
            log.error("Given AirCompany name is NULL!");
            return responseContainer.setErrorMessageAndStatusCode("Given AirCompany name is NULL!",HttpStatus.BAD_REQUEST.value());
        }
        AirCompany airCompany;
        try {
            airCompany = airCompanyRepository.findByName(name).orElse(null);
        }catch (Exception e){
            log.error("Exception error when trying to find AirCompany by name: {}; Error: {}",name,e.getMessage());
            return responseContainer.setErrorMessageAndStatusCode(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        if(airCompany==null){
            log.error("AirCompany with given name not found! Name: {}",name);
            return responseContainer.setErrorMessageAndStatusCode("AirCompany with given name not found!",HttpStatus.BAD_REQUEST.value());
        }
        return responseContainer.setSuccessResult(airCompanyMapper.toDto(airCompany));
    }

    private AirCompany findAirCompany(String name) {
        try {
            return airCompanyRepository.findByName(name).orElse(null);
        } catch (Exception ex) {
            log.error("Exception error when trying to get air company by name: {}; Error: {}", name, ex.getMessage());
            return null;
        }
    }

    /**
     * Logic to get requested air company by nemt
     *
     * @param name air company to make request for
     * @return container with air company
     */
    public AirCompanyDto getAirCompany(String name) {
        if (!StringUtils.hasText(name))
            return null;
        AirCompany airCompany = findAirCompany(name);
        if (airCompany != null)
            return airCompanyMapper.toDto(airCompany);
        return null;
    }
}
