package milansomyk.springboothw.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class AirCompany {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String name;
    private String companyType;
    private LocalDate foundedAt;
}
