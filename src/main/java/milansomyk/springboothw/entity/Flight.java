package milansomyk.springboothw.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String flightStatus;
    @OneToOne
    @JoinColumn(referencedColumnName = "id")
    private AirCompany airCompanyId;
    @OneToOne
    @JoinColumn(referencedColumnName = "id")
    private Airplane airplaneId;
    private String departureCountry;
    private String destinationCountry;
    private Integer distance;
    private Integer estimatedFlightTime;
    private Date startedAt;
    private Date endedAt;
    private Date delayStartedAt;
    private Date createdAt;
}
