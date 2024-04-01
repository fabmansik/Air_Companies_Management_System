package milansomyk.springboothw.repository;

import milansomyk.springboothw.entity.AirCompany;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirCompanyRepository extends JpaRepository<AirCompany,Integer> {
}
