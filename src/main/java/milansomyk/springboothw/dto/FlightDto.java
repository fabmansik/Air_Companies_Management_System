package milansomyk.springboothw.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import milansomyk.springboothw.dto.AirCompanyDto;
import milansomyk.springboothw.dto.AirplaneDto;
import milansomyk.springboothw.enums.FlightStatus;

import java.lang.Integer;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.stream.Stream;

public class FlightDto {
    private Integer id;
    @Enumerated(EnumType.STRING)
    private FlightStatus flightStatus;
    private AirCompanyDto airCompanyId;
    private AirplaneDto airplaneId;
    private String departureCountry;
    private String destinationCountry;
    private Integer distance;
    private Integer estimatedFlightTime;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
    private LocalDateTime delayStartedAt;
    private LocalDateTime createdAt;
    public FlightDto() {
    }

    public FlightDto(Integer id, FlightStatus flightStatus, AirCompanyDto airCompanyId, AirplaneDto airplaneId, String departureCountry, String destinationCountry, Integer distance, Integer estimatedFlightTime, LocalDateTime startedAt, LocalDateTime endedAt, LocalDateTime delayStartedAt, LocalDateTime createdAt) {
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

    public FlightStatus getFlightStatus() {
        return flightStatus;
    }

    public void setFlightStatus(FlightStatus flightStatus) {
        this.flightStatus = flightStatus;
    }

    public AirCompanyDto getAirCompanyId() {
        return airCompanyId;
    }

    public void setAirCompanyId(AirCompanyDto airCompanyId) {
        this.airCompanyId = airCompanyId;
    }

    public AirplaneDto getAirplaneDtoId() {
        return airplaneId;
    }

    public void setAirplaneDtoId(AirplaneDto airplaneId) {
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

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public LocalDateTime getEndedAt() {
        return endedAt;
    }

    public void setEndedAt(LocalDateTime endedAt) {
        this.endedAt = endedAt;
    }

    public LocalDateTime getDelayStartedAt() {
        return delayStartedAt;
    }

    public void setDelayStartedAt(LocalDateTime delayStartedAt) {
        this.delayStartedAt = delayStartedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public boolean allNull() {
        return Stream.of(id, flightStatus, airCompanyId, airplaneId, departureCountry, destinationCountry, distance, estimatedFlightTime, startedAt, endedAt, delayStartedAt, createdAt)
                .allMatch(Objects::isNull);
    }

}
