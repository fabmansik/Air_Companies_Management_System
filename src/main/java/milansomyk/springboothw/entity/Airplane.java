package milansomyk.springboothw.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
@Entity
public class Airplane {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Column(unique = true)
    private String factorySerialNumber;
    @ManyToOne
    @JoinColumn(referencedColumnName = "id", name = "air_company_id", foreignKey = @ForeignKey(name = "FK_Airplane_AirCompanyID_AirCompany_ID"))
    private AirCompany airCompanyId;
    private Integer numberOfFlights;
    private Integer flightDistance;
    private Integer fuelCapacity;
    private String type;
    private LocalDate createdAt;

    public Airplane(){}

    public Airplane(Integer id, String name, String factorySerialNumber, AirCompany airCompanyId, Integer numberOfFlights, Integer flightDistance, Integer fuelCapacity, String type, LocalDate createdAt) {
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
