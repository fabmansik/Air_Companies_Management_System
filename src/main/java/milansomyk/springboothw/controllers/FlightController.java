package milansomyk.springboothw.controllers;

import milansomyk.springboothw.dto.FlightDto;
import milansomyk.springboothw.dto.ResponseContainer;
import milansomyk.springboothw.service.FlightService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/flight")
public class FlightController {
    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }
    @PostMapping
    public ResponseEntity<ResponseContainer> createFlight(@RequestBody FlightDto flightDto){
        ResponseContainer responseContainer = flightService.addFlight(flightDto);
        return ResponseEntity.status(responseContainer.getStatusCode()).body(responseContainer);
    }
}
