package milansomyk.springboothw.controllers;

import milansomyk.springboothw.dto.AirplaneDto;
import milansomyk.springboothw.dto.ResponseContainer;
import milansomyk.springboothw.service.AirplaneService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/airplane")
public class AirplaneController {
    private final AirplaneService airplaneService;
    public AirplaneController(AirplaneService airplaneService) {
        this.airplaneService = airplaneService;
    }

    @PostMapping
    public ResponseEntity<ResponseContainer> createPlane(@RequestParam(required = false) String companyName, @RequestBody AirplaneDto airplaneDto){
        ResponseContainer responseContainer = airplaneService.createPlane(companyName, airplaneDto);
        return ResponseEntity.status(responseContainer.getStatusCode()).body(responseContainer);
    }
    @PutMapping
    public ResponseEntity<ResponseContainer> movePlane(@RequestParam String companyName, @RequestParam String serialNum){
        ResponseContainer responseContainer = airplaneService.movePlane(companyName, serialNum);
        return ResponseEntity.status(responseContainer.getStatusCode()).body(responseContainer);
    }
}
