package milansomyk.springboothw.controllers;

import milansomyk.springboothw.dto.AirCompanyDto;
import milansomyk.springboothw.dto.ResponseContainer;
import milansomyk.springboothw.service.AirCompanyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping(params = "name")
    public ResponseEntity<ResponseContainer> getAirCompanyById(@RequestParam String name){
        ResponseContainer responseContainer = airCompanyService.getByName(name);
        return ResponseEntity.status(responseContainer.getStatusCode()).body(responseContainer);
    }

    @PostMapping
    public ResponseEntity<ResponseContainer> createAirCompany(@RequestBody AirCompanyDto airCompanyDto){
        ResponseContainer responseContainer = airCompanyService.createAirCompany(airCompanyDto);
        return ResponseEntity.status(responseContainer.getStatusCode()).body(responseContainer);
    }
    @PutMapping
    public ResponseEntity<ResponseContainer> updateAirCompany(@RequestParam int id, @RequestBody AirCompanyDto airCompanyDto){
        ResponseContainer responseContainer = airCompanyService.updateAirCompany(id, airCompanyDto);
        return ResponseEntity.status(responseContainer.getStatusCode()).body(responseContainer);
    }
    @DeleteMapping
    public ResponseEntity<ResponseContainer> deleteAirCompany(@RequestParam int id){
        ResponseContainer responseContainer = airCompanyService.deleteById(id);
        return ResponseEntity.status(responseContainer.getStatusCode()).body(responseContainer);
    }
}
