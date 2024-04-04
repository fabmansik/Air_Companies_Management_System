package milansomyk.springboothw.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import milansomyk.springboothw.enums.AirplaneType;

import java.time.LocalDate;
import java.util.Objects;
import java.util.stream.Stream;
@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class AirplaneDto {
    @JsonIgnore
    private Integer id;
    @Size(min=2)
    private String name;
    @Size(min=2)
    private String factorySerialNumber;
    private AirCompanyDto airCompanyId;
    @PositiveOrZero
    private Integer numberOfFlights;
    @PositiveOrZero
    private Integer flightDistance;
    @Positive
    private Integer fuelCapacity;
    private AirplaneType type;
    @PastOrPresent
    private LocalDate createdAt;

    public boolean anyRequiredIsNull(){
        return Stream.of(name,factorySerialNumber,fuelCapacity,type,createdAt)
                .anyMatch(Objects::isNull);
    }
}
