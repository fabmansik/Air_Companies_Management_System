package milansomyk.springboothw.repository;

import milansomyk.springboothw.entity.AirCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

public interface AirCompanyRepository extends JpaRepository<AirCompany,Integer> {
    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "update air_company set name = ifNull(:name, name), company_type = ifNull(:companyType, company_type), founded_at = ifNull(:foundedAt,founded_at) where id = :id")
    Integer updateAirCompanyById(int id, String name, String companyType, LocalDate foundedAt);
}
