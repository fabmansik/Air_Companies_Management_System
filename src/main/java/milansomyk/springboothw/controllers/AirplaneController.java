package milansomyk.springboothw.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import milansomyk.springboothw.dto.AirplaneDto;
import milansomyk.springboothw.dto.ResponseContainer;
import milansomyk.springboothw.service.AirplaneService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
@Slf4j
@RestController
@RequestMapping("/airplane")
public class AirplaneController {
    private final AirplaneService airplaneService;
    public AirplaneController(AirplaneService airplaneService) {
        this.airplaneService = airplaneService;
    }

    @PostMapping
    public ResponseEntity<ResponseContainer> createPlane(@RequestParam(required = false) String companyName, @Valid @RequestBody AirplaneDto airplaneDto){
        ResponseContainer responseContainer = airplaneService.createPlane(companyName, airplaneDto);
        return ResponseEntity.status(responseContainer.getStatusCode()).body(responseContainer);
    }
    @PutMapping
    public ResponseEntity<ResponseContainer> movePlane(@RequestParam String companyName, @Valid @RequestParam String serialNum){
        ResponseContainer responseContainer = airplaneService.movePlane(companyName, serialNum);
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
