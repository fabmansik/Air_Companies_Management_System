package milansomyk.springboothw.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import milansomyk.springboothw.enums.FlightStatus;

import java.time.LocalDateTime;
@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    private FlightStatus flightStatus;
    @ManyToOne
    @JoinColumn(referencedColumnName = "id", name = "air_company_id", foreignKey = @ForeignKey(name = "FK_Flight_AirCompanyID_AirCompany_ID"))
    private AirCompany airCompanyId;
    @ManyToOne
    @JoinColumn(referencedColumnName = "id", name = "airplane_id", foreignKey = @ForeignKey(name = "FK_Flight_AirplaneID_Airplane_ID"))
    private Airplane airplaneId;
    private String departureCountry;
    private String destinationCountry;
    private Integer distance;
    private Integer estimatedFlightTime;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
    private LocalDateTime delayStartedAt;
    private LocalDateTime createdAt;
}
