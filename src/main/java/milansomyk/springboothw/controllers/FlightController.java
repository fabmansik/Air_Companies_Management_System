package milansomyk.springboothw.controllers;

import milansomyk.springboothw.dto.FlightDto;
import milansomyk.springboothw.dto.ResponseContainer;
import milansomyk.springboothw.enums.FlightStatus;
import milansomyk.springboothw.service.FlightService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/flight")
public class FlightController {
    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }
    @PostMapping
    public ResponseEntity<ResponseContainer> createFlight(@RequestParam String companyName, @RequestParam String planeSerialNum, @RequestBody FlightDto flightDto){
        ResponseContainer responseContainer = flightService.addFlight(companyName, planeSerialNum, flightDto);
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
}
