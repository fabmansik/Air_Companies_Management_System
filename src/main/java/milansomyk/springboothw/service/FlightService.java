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
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
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
        if (flightDto == null || flightDto.anyRequiredIsNull()) {
            log.error("Not enough info about Flight!");
            return responseContainer.setErrorMessageAndStatusCode("Not enough info about Flight!", HttpStatus.BAD_REQUEST.value());
        }
        if (!StringUtils.hasText(planeSerialNum)) {
            log.error("Given Airplane serial number is null!");
            return responseContainer.setErrorMessageAndStatusCode("Given Airplane serial number is null!", HttpStatus.BAD_REQUEST.value());
        }

        ResponseContainer airplaneDtoBySerialNumberResponse = airplaneService.getAirplaneDtoBySerialNumber(planeSerialNum);
        if(airplaneDtoBySerialNumberResponse.isError()) return airplaneDtoBySerialNumberResponse;
        AirplaneDto foundAirplane = (AirplaneDto) airplaneDtoBySerialNumberResponse.getResult();

        AirCompanyDto foundCompanyDto = foundAirplane.getAirCompanyId();
        if (ObjectUtils.isEmpty(foundCompanyDto)) {
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
            log.error("Exception error when trying to save FlightDto: {}; Error:{}", flightDto, e.getMessage());
            return responseContainer.setErrorMessageAndStatusCode(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return responseContainer.setSuccessResult("Flight with this parameters was successfully created!");
    }

    public ResponseContainer updateStatus(Integer id, FlightStatus status) {
        ResponseContainer responseContainer = new ResponseContainer();
        if (ObjectUtils.isEmpty(id)) {
            log.error("Flight id was not given!");
            return responseContainer.setErrorMessageAndStatusCode("Flight id was not given!", HttpStatus.BAD_REQUEST.value());
        }
        Flight foundFlight;
        try {
            foundFlight = flightRepository.findById(id).orElse(null);
        } catch (Exception e) {
            log.error("Exception error when trying to find Flight by id {}; Error:{}", id, e.getMessage());
            return responseContainer.setErrorMessageAndStatusCode(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        if (ObjectUtils.isEmpty(foundFlight)) {
            log.error("Flight with this id was not found!");
            return responseContainer.setErrorMessageAndStatusCode("Flight with this id was not found!", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        if (foundFlight.getFlightStatus() == status) {
            log.error("This Flight status already in use!");
            return responseContainer.setErrorMessageAndStatusCode("This Flight status already in use!", HttpStatus.BAD_REQUEST.value());
        }
        if (foundFlight.getFlightStatus() == FlightStatus.PENDING) {
            switch (status) {
                case ACTIVE -> {
                    foundFlight.setStartedAt(LocalDateTime.now());
                    foundFlight.setFlightStatus(FlightStatus.ACTIVE);
                }
                case DELAYED -> {
                    foundFlight.setDelayStartedAt(LocalDateTime.now());
                    foundFlight.setFlightStatus(FlightStatus.DELAYED);
                }
                case COMPLETED -> {
                    log.error("You can`t change PENDING status to COMPLETED!");
                    return responseContainer.setErrorMessageAndStatusCode("You can`t change PENDING status to COMPLETED!", HttpStatus.BAD_REQUEST.value());
                }
            }
        } else if (foundFlight.getFlightStatus() == FlightStatus.DELAYED) {
            if (status == FlightStatus.ACTIVE) {
                foundFlight.setStartedAt(LocalDateTime.now());
                foundFlight.setFlightStatus(FlightStatus.ACTIVE);
            } else {
                log.error("You can change DELAYED status to ACTIVE only!");
                return responseContainer.setErrorMessageAndStatusCode("You can change DELAYED status to ACTIVE only!", HttpStatus.BAD_REQUEST.value());
            }
        } else if (foundFlight.getFlightStatus() == FlightStatus.ACTIVE) {
            if (status == FlightStatus.COMPLETED) {
                foundFlight.setEndedAt(LocalDateTime.now());
                foundFlight.setFlightStatus(FlightStatus.COMPLETED);
            } else {
                log.error("You can change ACTIVE status to COMPLETED only!");
                return responseContainer.setErrorMessageAndStatusCode("You can change ACTIVE status to COMPLETED only!", HttpStatus.BAD_REQUEST.value());
            }
        } else {
            log.error("COMPLETED flight status can`t be changed!");
            return responseContainer.setErrorMessageAndStatusCode("COMPLETED flight status can`t be changed!", HttpStatus.BAD_REQUEST.value());
        }

        try {
            flightRepository.save(foundFlight);
        } catch (Exception e) {
            log.error("Exception error when trying to save Flight {}; Error:{}", foundFlight, e.getMessage());
            return responseContainer.setErrorMessageAndStatusCode(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return responseContainer.setSuccessResult("Flight status updated successfully!");
    }

    public ResponseContainer getCompanyFlightsByStatus(String companyName, FlightStatus flightStatus) {
        ResponseContainer responseContainer = new ResponseContainer();
        if (!StringUtils.hasText(companyName)) {
            log.error("AirCompany name was not given!");
            return responseContainer.setErrorMessageAndStatusCode("AirCompany name was not given!", HttpStatus.BAD_REQUEST.value());
        }
        try {
            airCompanyService.getAirCompanyByName(companyName);
        } catch (IllegalArgumentException e) {
            log.error("Exception error when trying to get AirCompany by company name {}; Error:{}", companyName, e.getMessage());
            return responseContainer.setErrorMessageAndStatusCode(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        } catch (Exception e) {
            log.error("Exception error when trying to get AirCompany by company name {}; Error:{}", companyName, e.getMessage());
            return responseContainer.setErrorMessageAndStatusCode(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        List<Flight> flightList;
        try {
            flightList = flightRepository.getFlightsByCompanyNameAndFlightStatus(companyName, flightStatus.toString());
        } catch (Exception e) {
            log.error("Exception error when trying to get Flights by company name and status; Company name: {}; Flight status: {}; Error:{}",
                    companyName, flightStatus, e.getMessage());
            return responseContainer.setErrorMessageAndStatusCode(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return responseContainer.setSuccessResult(
                CollectionUtils.isEmpty(flightList) ?
                        Collections.emptyList() :
                        flightList.stream().map(flightMapper::toDto).toList());
    }

    public ResponseContainer getAllFlightsByStatus(FlightStatus status) {
        ResponseContainer responseContainer = new ResponseContainer();
        List<Flight> flightsList;
        try {
            flightsList = flightRepository.getFlightsByFlightStatus(status);
        } catch (Exception e) {
            log.error("Exception error when trying to get Flights by status: {}; Error:{}", status, e.getMessage());
            return responseContainer.setErrorMessageAndStatusCode(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return responseContainer.setSuccessResult(
                CollectionUtils.isEmpty(flightsList) ?
                        Collections.emptyList() :
                        flightsList.stream().map(flightMapper::toDto).toList());
    }

    public ResponseContainer getCompletedAndTimeUnderratedFlights() {
        ResponseContainer responseContainer = new ResponseContainer();
        List<Flight> completedFlights;
        try {
            completedFlights = flightRepository.getCompletedFlights();
        } catch (Exception e) {
            log.error("Exception error when trying to get COMPLETED Flights; Error:{}", e.getMessage());
            return responseContainer.setErrorMessageAndStatusCode(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        List<FlightDto> list =
                CollectionUtils.isEmpty(completedFlights) ?
                        Collections.emptyList() :
                        completedFlights.stream().filter(f -> f.getEstimatedFlightTime() < Duration.between(f.getCreatedAt(), f.getEndedAt()).toSeconds()).map(flightMapper::toDto)
                                .toList();
        return responseContainer.setSuccessResult(list);
    }

    public ResponseContainer deleteCompanyFromFlight(Integer companyId) {
        ResponseContainer responseContainer = new ResponseContainer();
        if(companyId==null) {
            log.error("Given AirCompany id is NULL!");
            return responseContainer.setErrorMessageAndStatusCode("Given AirCompany id is NULL!",HttpStatus.BAD_REQUEST.value());
        }
        try{
            flightRepository.deleteCompanyIdFromFlight(companyId);
        }catch (Exception e){
            log.error("Exception error when trying to delete Company by id: {}; Error:{}",companyId, e.getMessage());
            return responseContainer.setErrorMessageAndStatusCode(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return responseContainer;
    }
}
