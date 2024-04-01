package milansomyk.springboothw.dto;

import java.time.LocalDate;
import java.util.Objects;
import java.util.stream.Stream;

public class AirCompanyDto {
    private Integer id;
    private String name;
    private String companyType;
    private LocalDate foundedAt;
    public AirCompanyDto() {
    }

    public AirCompanyDto(Integer id, String name, String companyType, LocalDate foundedAt) {
        this.id = id;
        this.name = name;
        this.companyType = companyType;
        this.foundedAt = foundedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public LocalDate getFoundedAt() {
        return foundedAt;
    }

    public void setFoundedAt(LocalDate foundedAt) {
        this.foundedAt = foundedAt;
    }

    public boolean allNull() {
        return Stream.of(id, companyType,foundedAt,name)
                .allMatch(Objects::isNull);
    }
}
