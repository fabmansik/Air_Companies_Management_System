package milansomyk.springboothw.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.Objects;
import java.util.stream.Stream;
@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class AirplaneDto {
    @JsonIgnore
    private Integer id;
    @Min(2)
    private String name;
    @Min(2)
    private String factorySerialNumber;
    private AirCompanyDto airCompanyId;
    @PositiveOrZero
    private Integer numberOfFlights;
    @PositiveOrZero
    private Integer flightDistance;
    @Positive
    private Integer fuelCapacity;
    @Min(2)
    private String type;
    @PastOrPresent
    private LocalDate createdAt;

    public boolean anyRequiredIsNull(){
        return Stream.of(name,factorySerialNumber,fuelCapacity,type,createdAt)
                .anyMatch(Objects::isNull);
    }
}
