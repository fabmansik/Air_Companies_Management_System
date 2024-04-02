package milansomyk.springboothw.service;

import milansomyk.springboothw.dto.AirCompanyDto;
import milansomyk.springboothw.dto.AirplaneDto;
import milansomyk.springboothw.dto.FlightDto;
import milansomyk.springboothw.dto.ResponseContainer;
import milansomyk.springboothw.entity.AirCompany;
import milansomyk.springboothw.entity.Airplane;
import milansomyk.springboothw.entity.Flight;
import milansomyk.springboothw.enums.FlightStatus;
import milansomyk.springboothw.mapper.FlightMapper;
import milansomyk.springboothw.repository.FlightRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class FlightService {
    private final FlightRepository flightRepository;
    private final FlightMapper flightMapper;
    private final AirplaneService airplaneService;
    private final AirCompanyService airCompanyService;

    public FlightService(FlightRepository flightRepository, FlightMapper flightMapper, AirplaneService airplaneService, AirCompanyService airCompanyService) {
        this.flightRepository = flightRepository;
        this.flightMapper = flightMapper;
        this.airplaneService = airplaneService;
        this.airCompanyService = airCompanyService;
    }

    public ResponseContainer addFlight(String companyName, String planeSerialNum, FlightDto flightDto) {
        ResponseContainer responseContainer = new ResponseContainer();
        if (flightDto.allNull()) {
            return responseContainer.setErrorMessageAndStatusCode("not full information about flight", HttpStatus.BAD_REQUEST.value());
        }
        if (!StringUtils.hasText(companyName)) {
            return responseContainer.setErrorMessageAndStatusCode("company name is null!", HttpStatus.BAD_REQUEST.value());
        }
        if (!StringUtils.hasText(planeSerialNum)) {
            return responseContainer.setErrorMessageAndStatusCode("plane serial number is null!", HttpStatus.BAD_REQUEST.value());
        }
        ResponseContainer bySerialNumber = airplaneService.getBySerialNumber(planeSerialNum);
        if (bySerialNumber.isError()) return bySerialNumber;
        ResponseContainer byName = airCompanyService.getByName(companyName);
        if (byName.isError()) return byName;

        flightDto.setAirCompanyId((AirCompanyDto) byName.getResult());
        flightDto.setAirplaneDtoId((AirplaneDto) bySerialNumber.getResult());
        flightDto.setCreatedAt(LocalDateTime.now());
        flightDto.setFlightStatus(FlightStatus.PENDING);
        flightDto.setStartedAt(null);
        Flight saved;
        try {
            saved = flightRepository.save(flightMapper.fromDto(flightDto));
        } catch (Exception e) {
            return responseContainer.setErrorMessageAndStatusCode(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return responseContainer.setSuccessResult(flightMapper.toDto(saved));
    }

    public ResponseContainer updateStatus(Integer id, String status) {
        ResponseContainer responseContainer = new ResponseContainer();
        FlightStatus newFlightStatus;
        if (!Arrays.stream(FlightStatus.values()).map(Enum::toString).toList().contains(status)) {
            return responseContainer.setErrorMessageAndStatusCode("wrong status used!", HttpStatus.BAD_REQUEST.value());
        }else{
            newFlightStatus=FlightStatus.valueOf(status);
        }
        Flight found;
        try{
            found = flightRepository.findById(id).orElse(null);
        }catch (Exception e){
            return responseContainer.setErrorMessageAndStatusCode(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        if(ObjectUtils.isEmpty(found)){
            return responseContainer.setErrorMessageAndStatusCode("flight not found!", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        if(found.getFlightStatus()==newFlightStatus)
            return responseContainer.setErrorMessageAndStatusCode("this status already used!", HttpStatus.BAD_REQUEST.value());
        if(found.getFlightStatus()==FlightStatus.PENDING){
            switch (newFlightStatus){
                case ACTIVE -> {
                    found.setStartedAt(LocalDateTime.now());
                    found.setFlightStatus(FlightStatus.ACTIVE);
                }
                case DELAYED -> {
                    found.setDelayStartedAt(LocalDateTime.now());
                    found.setFlightStatus(FlightStatus.DELAYED);
                }
                case COMPLETED -> {
                    return responseContainer.setErrorMessageAndStatusCode("you can`t change PENDING status to COMPLETED",HttpStatus.BAD_REQUEST.value());
                }
            }
        }
        else if(found.getFlightStatus()==FlightStatus.DELAYED){
            if (newFlightStatus == FlightStatus.ACTIVE) {
                found.setStartedAt(LocalDateTime.now());
                found.setFlightStatus(FlightStatus.ACTIVE);
            }
            else {
                return responseContainer.setErrorMessageAndStatusCode("you can change DELAYED status to ACTIVE only", HttpStatus.BAD_REQUEST.value());
            }
        }
        else if (found.getFlightStatus()==FlightStatus.ACTIVE){
            if (newFlightStatus == FlightStatus.COMPLETED) {
                found.setEndedAt(LocalDateTime.now());
                found.setFlightStatus(FlightStatus.COMPLETED);
            }
            else {
                return responseContainer.setErrorMessageAndStatusCode("you can change ACTIVE status to COMPLETED only",HttpStatus.BAD_REQUEST.value());
            }
        }
        else return responseContainer.setErrorMessageAndStatusCode("COMPLETED flight status can`t be changed", HttpStatus.BAD_REQUEST.value());

        try {
            flightRepository.save(found);
        } catch (Exception e) {
            return responseContainer.setErrorMessageAndStatusCode(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return responseContainer.setSuccessResult("flight status updated!");
    }
    public ResponseContainer getCompanyFlightsByStatus(String companyName, String flightStatus) {
        ResponseContainer responseContainer = new ResponseContainer();
        List<Flight> flightList;
        try {
            flightList = flightRepository.getFlightsByCompanyNameAndFlightStatus(companyName, flightStatus);
        }catch (Exception e){
            return responseContainer.setErrorMessageAndStatusCode(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        List<FlightDto> list = flightList.stream().map(flightMapper::toDto).toList();
        return responseContainer.setSuccessResult(list);
    }
    public ResponseContainer getAllFlightsByStatus(FlightStatus status){
        ResponseContainer responseContainer = new ResponseContainer();
        List<Flight> flightsList;
        try{
            flightsList= flightRepository.getFlightsByFlightStatus(status);
        }catch (Exception e){
            return responseContainer.setErrorMessageAndStatusCode(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        List<FlightDto> list = flightsList.stream().map(flightMapper::toDto).toList();
        return responseContainer.setSuccessResult(list);
    }
}
