package milansomyk.springboothw.controllers;

import milansomyk.springboothw.dto.ResponseContainer;
import milansomyk.springboothw.service.AirCompanyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/airCompany")
public class AirCompanyController {
    private final AirCompanyService airCompanyService;

    public AirCompanyController(AirCompanyService airCompanyService) {
        this.airCompanyService = airCompanyService;
    }

    @GetMapping
    public ResponseEntity<ResponseContainer> getAllAirCompanies(){
        ResponseContainer responseContainer = airCompanyService.getAll();
        return ResponseEntity.status(responseContainer.getStatusCode()).body(responseContainer);
    }
    @GetMapping
    public ResponseEntity<ResponseContainer> getAirCompanyById(@RequestParam int id){
        ResponseContainer responseContainer = airCompanyService.getById(id);
        return ResponseEntity.status(responseContainer.getStatusCode()).body(responseContainer);
    }
}
