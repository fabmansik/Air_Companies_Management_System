package milansomyk.springboothw.mapper;

import milansomyk.springboothw.dto.AirCompanyDto;
import milansomyk.springboothw.entity.AirCompany;
import org.springframework.stereotype.Component;

@Component
public class AirCompanyMapper {
    public AirCompany fromDto(AirCompanyDto airCompanyDto){
        return new AirCompany(airCompanyDto.getId(),airCompanyDto.getName(),airCompanyDto.getCompanyType(),airCompanyDto.getFoundedAt());
    }
    public AirCompanyDto toDto(AirCompany airCompany){
        return new AirCompanyDto(airCompany.getId(), airCompany.getName(), airCompany.getCompanyType(), airCompany.getFoundedAt());
    }
}
