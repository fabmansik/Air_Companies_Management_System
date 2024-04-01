package milansomyk.springboothw.dto;

import milansomyk.springboothw.entity.AirCompany;

import java.time.LocalDate;
import java.util.Objects;
import java.util.stream.Stream;

public class AirplaneDto {
    private Integer id;
    private String name;
    private String factorySerialNumber;
    private AirCompany airCompanyId;
    private Integer numberOfFlights;
    private Integer flightDistance;
    private Integer fuelCapacity;
    private String type;
    private LocalDate createdAt;

    public AirplaneDto(Integer id, String name, String factorySerialNumber, AirCompany airCompanyId, Integer numberOfFlights, Integer flightDistance, Integer fuelCapacity, String type, LocalDate createdAt) {
        this.id = id;
        this.name = name;
        this.factorySerialNumber = factorySerialNumber;
        this.airCompanyId = airCompanyId;
        this.numberOfFlights = numberOfFlights;
        this.flightDistance = flightDistance;
        this.fuelCapacity = fuelCapacity;
        this.type = type;
        this.createdAt = createdAt;
    }
    public boolean allNull() {
        return Stream.of(id, name, factorySerialNumber, airCompanyId, numberOfFlights, flightDistance, fuelCapacity, type, createdAt)
                .allMatch(Objects::isNull);
    }
    public AirplaneDto() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFactorySerialNumber() {
        return factorySerialNumber;
    }

    public void setFactorySerialNumber(String factorySerialNumber) {
        this.factorySerialNumber = factorySerialNumber;
    }

    public AirCompany getAirCompanyId() {
        return airCompanyId;
    }

    public void setAirCompanyId(AirCompany airCompanyId) {
        this.airCompanyId = airCompanyId;
    }

    public Integer getNumberOfFlights() {
        return numberOfFlights;
    }

    public void setNumberOfFlights(Integer numberOfFlights) {
        this.numberOfFlights = numberOfFlights;
    }

    public Integer getFlightDistance() {
        return flightDistance;
    }

    public void setFlightDistance(Integer flightDistance) {
        this.flightDistance = flightDistance;
    }

    public Integer getFuelCapacity() {
        return fuelCapacity;
    }

    public void setFuelCapacity(Integer fuelCapacity) {
        this.fuelCapacity = fuelCapacity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }


}
