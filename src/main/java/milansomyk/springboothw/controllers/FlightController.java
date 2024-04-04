package milansomyk.springboothw.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import milansomyk.springboothw.dto.FlightDto;
import milansomyk.springboothw.dto.ResponseContainer;
import milansomyk.springboothw.enums.FlightStatus;
import milansomyk.springboothw.service.FlightService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/flight")
@Slf4j
public class FlightController {
    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }
    @PostMapping
    public ResponseEntity<ResponseContainer> createFlight(@RequestParam String planeSerialNum, @RequestBody @Valid FlightDto flightDto){
        ResponseContainer responseContainer = flightService.addFlight(planeSerialNum, flightDto);
        return ResponseEntity.status(responseContainer.getStatusCode()).body(responseContainer);
    }
    @PutMapping
    public ResponseEntity<ResponseContainer> updateStatus(@RequestParam Integer id, @RequestParam FlightStatus status){
        ResponseContainer responseContainer = flightService.updateStatus(id, status);
        return ResponseEntity.status(responseContainer.getStatusCode()).body(responseContainer);
    }
    @GetMapping
    public ResponseEntity<ResponseContainer> getCompanyFlightsByStatus(@RequestParam String companyName, @RequestParam FlightStatus status){
        ResponseContainer responseContainer = flightService.getCompanyFlightsByStatus(companyName,status);
        return ResponseEntity.status(responseContainer.getStatusCode()).body(responseContainer);
    }
    @GetMapping(params = "status")
    public ResponseEntity<ResponseContainer> getAllFlightsByStatus(@RequestParam FlightStatus status){
        ResponseContainer responseContainer = flightService.getAllFlightsByStatus(status);
        return ResponseEntity.status(responseContainer.getStatusCode()).body(responseContainer);
    }
    @GetMapping("/timeUnderrated")
    public ResponseEntity<ResponseContainer> getAllTimeUnderratedFlights(){
        ResponseContainer responseContainer = flightService.getCompletedAndTimeUnderratedFlights();
        return ResponseEntity.status(responseContainer.getStatusCode()).body(responseContainer);
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseContainer> handleValidationExceptions(MethodArgumentNotValidException ex) {
        ResponseContainer responseContainer = new ResponseContainer();
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        responseContainer.setResult(errors);
        responseContainer.setErrorMessage("Object validation failed!");
        log.error("Object validation failed! Error: {}",ex.getAllErrors());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(responseContainer);
    }
}
