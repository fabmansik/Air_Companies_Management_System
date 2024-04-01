package milansomyk.springboothw.entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.UUID;

@Entity
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String flightStatus;
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
    private Date startedAt;
    private Date endedAt;
    private Date delayStartedAt;
    private Date createdAt;
    public Flight() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFlightStatus() {
        return flightStatus;
    }

    public void setFlightStatus(String flightStatus) {
        this.flightStatus = flightStatus;
    }

    public AirCompany getAirCompanyId() {
        return airCompanyId;
    }

    public void setAirCompanyId(AirCompany airCompanyId) {
        this.airCompanyId = airCompanyId;
    }

    public Airplane getAirplaneId() {
        return airplaneId;
    }

    public void setAirplaneId(Airplane airplaneId) {
        this.airplaneId = airplaneId;
    }

    public String getDepartureCountry() {
        return departureCountry;
    }

    public void setDepartureCountry(String departureCountry) {
        this.departureCountry = departureCountry;
    }

    public String getDestinationCountry() {
        return destinationCountry;
    }

    public void setDestinationCountry(String destinationCountry) {
        this.destinationCountry = destinationCountry;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public Integer getEstimatedFlightTime() {
        return estimatedFlightTime;
    }

    public void setEstimatedFlightTime(Integer estimatedFlightTime) {
        this.estimatedFlightTime = estimatedFlightTime;
    }

    public Date getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(Date startedAt) {
        this.startedAt = startedAt;
    }

    public Date getEndedAt() {
        return endedAt;
    }

    public void setEndedAt(Date endedAt) {
        this.endedAt = endedAt;
    }

    public Date getDelayStartedAt() {
        return delayStartedAt;
    }

    public void setDelayStartedAt(Date delayStartedAt) {
        this.delayStartedAt = delayStartedAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Flight(Integer id, String flightStatus, AirCompany airCompanyId, Airplane airplaneId, String departureCountry, String destinationCountry, Integer distance, Integer estimatedFlightTime, Date startedAt, Date endedAt, Date delayStartedAt, Date createdAt) {
        this.id = id;
        this.flightStatus = flightStatus;
        this.airCompanyId = airCompanyId;
        this.airplaneId = airplaneId;
        this.departureCountry = departureCountry;
        this.destinationCountry = destinationCountry;
        this.distance = distance;
        this.estimatedFlightTime = estimatedFlightTime;
        this.startedAt = startedAt;
        this.endedAt = endedAt;
        this.delayStartedAt = delayStartedAt;
        this.createdAt = createdAt;
    }


}
