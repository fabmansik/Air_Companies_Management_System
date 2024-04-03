package milansomyk.springboothw.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import milansomyk.springboothw.enums.FlightStatus;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.stream.Stream;
@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class FlightDto {
    private Integer id;
    @Enumerated(EnumType.STRING)
    private FlightStatus flightStatus;
    @NotNull
    private AirCompanyDto airCompanyId;
    @NotNull
    private AirplaneDto airplaneId;
    @Min(2)
    private String departureCountry;
    @Min(2)
    private String destinationCountry;
    @PositiveOrZero
    private Integer distance;
    @Positive
    private Integer estimatedFlightTime;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
    private LocalDateTime delayStartedAt;
    @PastOrPresent
    private LocalDateTime createdAt;

    public boolean anyRequiredIsNull(){
        return Stream.of(departureCountry,destinationCountry,distance,estimatedFlightTime)
                .anyMatch(Objects::isNull);
    }

}
