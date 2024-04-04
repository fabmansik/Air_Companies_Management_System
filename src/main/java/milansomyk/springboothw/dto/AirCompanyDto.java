package milansomyk.springboothw.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import milansomyk.springboothw.enums.CompanyType;

import java.time.LocalDate;
import java.util.Objects;
import java.util.stream.Stream;
@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class AirCompanyDto {
    @JsonIgnore
    private Integer id;
    @Size(min=2)
    private String name;
    private CompanyType companyType;
    @PastOrPresent
    @NotNull
    private LocalDate foundedAt;

    public boolean anyNull(){
        return Stream.of(companyType,foundedAt,name)
                .anyMatch(Objects::isNull);
    }


}
