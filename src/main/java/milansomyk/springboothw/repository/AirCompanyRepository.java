package milansomyk.springboothw.repository;

import milansomyk.springboothw.entity.AirCompany;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
public interface AirCompanyRepository extends JpaRepository<AirCompany,Integer> {
    Optional<AirCompany> findByName(String name);
    void deleteByName(String name);
}
