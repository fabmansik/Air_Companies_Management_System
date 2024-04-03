package milansomyk.springboothw.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import milansomyk.springboothw.dto.AirCompanyDto;
import milansomyk.springboothw.dto.AirplaneDto;
import milansomyk.springboothw.dto.FlightDto;
import milansomyk.springboothw.dto.ResponseContainer;
import milansomyk.springboothw.entity.Flight;
import milansomyk.springboothw.enums.FlightStatus;
import milansomyk.springboothw.mapper.FlightMapper;
import milansomyk.springboothw.repository.FlightRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
@RequiredArgsConstructor
@Service
@Slf4j
public class FlightService {

    private final FlightRepository flightRepository;
    private final FlightMapper flightMapper;
    private final AirplaneService airplaneService;
    private final AirCompanyService airCompanyService;

    public ResponseContainer addFlight(String planeSerialNum, FlightDto flightDto) {
        ResponseContainer responseContainer = new ResponseContainer();
        if (flightDto.anyRequiredIsNull()) {
            log.error("Not enough info about Flight!");
            return responseContainer.setErrorMessageAndStatusCode("Not enough info about Flight!", HttpStatus.BAD_REQUEST.value());
        }
        if (!StringUtils.hasText(planeSerialNum)) {
            log.error("Give Airplane serial number is null!");
            return responseContainer.setErrorMessageAndStatusCode("Give Airplane serial number is null!", HttpStatus.BAD_REQUEST.value());
        }

        AirplaneDto foundAirplane;
        try {
            foundAirplane = airplaneService.getAirplaneDtoBySerialNumber(planeSerialNum);
        } catch (IllegalArgumentException e){
            log.error(e.getMessage());
            return responseContainer.setErrorMessageAndStatusCode(e.getMessage(),HttpStatus.BAD_REQUEST.value());
        } catch (Exception e){
            log.error(e.getMessage());
            return responseContainer.setErrorMessageAndStatusCode(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

        AirCompanyDto foundCompanyDto = foundAirplane.getAirCompanyId();
        if(ObjectUtils.isEmpty(foundCompanyDto)){
            log.error("Airplane without AirCompany can`t be used in Flight!");
            return responseContainer.setErrorMessageAndStatusCode("Airplane without AirCompany can`t be used in Flight!", HttpStatus.BAD_REQUEST.value());
        }
        flightDto.setAirCompanyId(foundCompanyDto);
        flightDto.setAirplaneId(foundAirplane);
        flightDto.setCreatedAt(LocalDateTime.now());
        flightDto.setFlightStatus(FlightStatus.PENDING);
        flightDto.setStartedAt(null);
        flightDto.setEndedAt(null);
        flightDto.setDelayStartedAt(null);
        try {
            flightRepository.save(flightMapper.fromDto(flightDto));
        } catch (Exception e) {
            log.error(e.getMessage());
            return responseContainer.setErrorMessageAndStatusCode(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return responseContainer.setSuccessResult("Flight with this parameters was successfully created!");
    }
    public ResponseContainer updateStatus(Integer id, FlightStatus status) {
        ResponseContainer responseContainer = new ResponseContainer();
        if(ObjectUtils.isEmpty(id)){
            log.error("Flight id was not given!");
            return responseContainer.setErrorMessageAndStatusCode("Flight id was not given!",HttpStatus.BAD_REQUEST.value());
        }
        Flight found;
        try{
            found = flightRepository.findById(id).orElse(null);
        }catch (Exception e){
            log.error(e.getMessage());
            return responseContainer.setErrorMessageAndStatusCode(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        if(ObjectUtils.isEmpty(found)){
            log.error("Flight with this id was not found!");
            return responseContainer.setErrorMessageAndStatusCode("Flight with this id was not found!", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        if(found.getFlightStatus()==status){
            log.error("This Flight status already in use!");
            return responseContainer.setErrorMessageAndStatusCode("This Flight status already in use!", HttpStatus.BAD_REQUEST.value());
        }
        if(found.getFlightStatus()==FlightStatus.PENDING){
            switch (status){
                case ACTIVE -> {
                    found.setStartedAt(LocalDateTime.now());
                    found.setFlightStatus(FlightStatus.ACTIVE);
                }
                case DELAYED -> {
                    found.setDelayStartedAt(LocalDateTime.now());
                    found.setFlightStatus(FlightStatus.DELAYED);
                }
                case COMPLETED -> {
                    log.error("You can`t change PENDING status to COMPLETED!");
                    return responseContainer.setErrorMessageAndStatusCode("You can`t change PENDING status to COMPLETED!",HttpStatus.BAD_REQUEST.value());
                }
            }
        }
        else if(found.getFlightStatus()==FlightStatus.DELAYED){
            if (status == FlightStatus.ACTIVE) {
                found.setStartedAt(LocalDateTime.now());
                found.setFlightStatus(FlightStatus.ACTIVE);
            }
            else {
                log.error("You can change DELAYED status to ACTIVE only!");
                return responseContainer.setErrorMessageAndStatusCode("You can change DELAYED status to ACTIVE only!", HttpStatus.BAD_REQUEST.value());
            }
        }
        else if (found.getFlightStatus()==FlightStatus.ACTIVE){
            if (status == FlightStatus.COMPLETED) {
                found.setEndedAt(LocalDateTime.now());
                found.setFlightStatus(FlightStatus.COMPLETED);
            }
            else {
                log.error("You can change ACTIVE status to COMPLETED only!");
                return responseContainer.setErrorMessageAndStatusCode("You can change ACTIVE status to COMPLETED only!",HttpStatus.BAD_REQUEST.value());
            }
        }
        else{
            log.error("COMPLETED flight status can`t be changed!");
            return responseContainer.setErrorMessageAndStatusCode("COMPLETED flight status can`t be changed!", HttpStatus.BAD_REQUEST.value());
        }

        try {
            flightRepository.save(found);
        } catch (Exception e) {
            log.error(e.getMessage());
            return responseContainer.setErrorMessageAndStatusCode(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return responseContainer.setSuccessResult("Flight status updated successfully!");
    }
    public ResponseContainer getCompanyFlightsByStatus(String companyName, FlightStatus flightStatus) {
        ResponseContainer responseContainer = new ResponseContainer();
        if(!StringUtils.hasText(companyName)){
            log.error("AirCompany name was not given!");
            return responseContainer.setErrorMessageAndStatusCode("AirCompany name was not given!", HttpStatus.BAD_REQUEST.value());
        }
        try{
            airCompanyService.getAirCompanyByName(companyName);
        }catch (IllegalArgumentException e){
            log.error(e.getMessage());
            return responseContainer.setErrorMessageAndStatusCode(e.getMessage(),HttpStatus.BAD_REQUEST.value());
        }catch (Exception e){
            log.error(e.getMessage());
            return responseContainer.setErrorMessageAndStatusCode(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        List<Flight> flightList;
        try {
            flightList = flightRepository.getFlightsByCompanyNameAndFlightStatus(companyName, flightStatus.toString());
        }catch (Exception e){
            log.error(e.getMessage());
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
            log.error(e.getMessage());
            return responseContainer.setErrorMessageAndStatusCode(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        List<FlightDto> list = flightsList.stream().map(flightMapper::toDto).toList();
        return responseContainer.setSuccessResult(list);
    }
    public ResponseContainer getCompletedAndTimeUnderratedFlights(){
        ResponseContainer responseContainer = new ResponseContainer();
        List<Flight> completedFlights;
        try {
            completedFlights = flightRepository.getCompletedFlights();
        } catch (Exception e){
            log.error(e.getMessage());
            return responseContainer.setErrorMessageAndStatusCode(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        List<FlightDto> list = completedFlights.stream()
                .filter(f -> f.getEstimatedFlightTime() < Duration.between(f.getCreatedAt(), f.getEndedAt()).toSeconds()).map(flightMapper::toDto)
                .toList();
        return responseContainer.setSuccessResult(list);
    }
    public void deleteCompanyFromFlight(int companyId){
        flightRepository.deleteCompanyIdFromFlight(companyId);
    }
}
