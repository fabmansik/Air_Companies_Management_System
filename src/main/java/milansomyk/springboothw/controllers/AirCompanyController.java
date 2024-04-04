package milansomyk.springboothw.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import milansomyk.springboothw.dto.AirCompanyDto;
import milansomyk.springboothw.dto.ResponseContainer;
import milansomyk.springboothw.service.AirCompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/airCompany")
@Slf4j
public class AirCompanyController {
    private final AirCompanyService airCompanyService;

    public AirCompanyController(AirCompanyService airCompanyService) {
        this.airCompanyService = airCompanyService;
    }

    @GetMapping
    public ResponseEntity<ResponseContainer> getAllAirCompanies() {
        ResponseContainer responseContainer = airCompanyService.getAllAirCompanies();
        return ResponseEntity.status(responseContainer.getStatusCode()).body(responseContainer);
    }

    @GetMapping(params = "name")
    public ResponseEntity<ResponseContainer> getAirCompanyByName(@RequestParam String companyName) {
        ResponseContainer responseContainer = airCompanyService.getCompanyByName(companyName);
        return ResponseEntity.status(responseContainer.getStatusCode()).body(responseContainer);
    }

    @PostMapping
    public ResponseEntity<ResponseContainer> createAirCompany(@RequestBody @Valid AirCompanyDto airCompanyDto) {
        ResponseContainer responseContainer = airCompanyService.createAirCompany(airCompanyDto);
        return ResponseEntity.status(responseContainer.getStatusCode()).body(responseContainer);
    }

    @PutMapping
    public ResponseEntity<ResponseContainer> updateAirCompany(@RequestParam String companyName, @RequestBody @Valid AirCompanyDto airCompanyDto) {
        ResponseContainer responseContainer = airCompanyService.updateAirCompany(companyName, airCompanyDto);
        return ResponseEntity.status(responseContainer.getStatusCode()).body(responseContainer);
    }

    @DeleteMapping
    public ResponseEntity<ResponseContainer> deleteAirCompany(@RequestParam String companyName) {
        ResponseContainer responseContainer = airCompanyService.deleteByCompanyName(companyName);
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
        log.error("Object validation failed! Error: {}", ex.getAllErrors());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(responseContainer);
    }
}
