package milansomyk.springboothw.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.Objects;
import java.util.stream.Stream;
@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class AirCompanyDto {
    @JsonIgnore
    private Integer id;
    @Min(2)
    private String name;
    @Min(2)
    private String companyType;
    @PastOrPresent
    private LocalDate foundedAt;

    public boolean allNull() {
        return Stream.of(id, companyType,foundedAt,name)
                .allMatch(Objects::isNull);
    }
    public boolean anyNull(){
        return Stream.of(companyType,foundedAt,name)
                .anyMatch(Objects::isNull);
    }
}
