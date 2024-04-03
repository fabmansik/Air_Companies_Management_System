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
public class Airplane {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Column(unique = true, name = "factory_serial_number")
    private String factorySerialNumber;
    @ManyToOne
    @JoinColumn(referencedColumnName = "id", name = "air_company_id", foreignKey = @ForeignKey(name = "FK_Airplane_AirCompanyID_AirCompany_ID"))
    private AirCompany airCompanyId;
    private Integer numberOfFlights;
    private Integer flightDistance;
    private Integer fuelCapacity;
    private String type;
    private LocalDate createdAt;
}
