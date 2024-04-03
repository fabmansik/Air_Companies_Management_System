package milansomyk.springboothw.repository;

import milansomyk.springboothw.entity.AirCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
public interface AirCompanyRepository extends JpaRepository<AirCompany,Integer> {
    Optional<AirCompany> findByName(String name);
    @Transactional
    @Modifying
//    @Query(nativeQuery = true, value = "DELETE FROM air_company WHERE air_company.name = :name")
    void deleteByName(String name);
}
