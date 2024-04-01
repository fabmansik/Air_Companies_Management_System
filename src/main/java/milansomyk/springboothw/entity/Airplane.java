package milansomyk.springboothw.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Airplane {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String factorySerialNumber;
    @OneToOne
    @JoinColumn(referencedColumnName = "id")
    private AirCompany airCompanyId;
    private Integer numberOfFlights;
    private Integer flightDistance;
    private Integer fuelCapacity;
    private String type;
    private Date createdAt;
}
