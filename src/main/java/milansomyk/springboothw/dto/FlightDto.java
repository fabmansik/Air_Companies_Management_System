package milansomyk.springboothw.dto;

import milansomyk.springboothw.entity.AirCompany;
import milansomyk.springboothw.entity.Airplane;

import java.util.Date;
import java.util.Objects;
import java.util.stream.Stream;

public class FlightDto {
    private Integer id;
    private String flightStatus;
    private AirCompany airCompanyId;
    private Airplane airplaneId;
    private String departureCountry;
    private String destinationCountry;
    private Integer distance;
    private Integer estimatedFlightTime;
    private Date startedAt;
    private Date endedAt;
    private Date delayStartedAt;
    private Date createdAt;
    public FlightDto() {
    }

    public FlightDto(Integer id, String flightStatus, AirCompany airCompanyId, Airplane airplaneId, String departureCountry, String destinationCountry, Integer distance, Integer estimatedFlightTime, Date startedAt, Date endedAt, Date delayStartedAt, Date createdAt) {
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
    public boolean allNull() {
        return Stream.of(id, flightStatus, airCompanyId, airplaneId, departureCountry, destinationCountry, distance, estimatedFlightTime, startedAt, endedAt, delayStartedAt, createdAt)
                .allMatch(Objects::isNull);
    }

}
