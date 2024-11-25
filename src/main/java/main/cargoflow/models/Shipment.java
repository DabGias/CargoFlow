package main.cargoflow.models;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "shipment_tb")
public class Shipment {

    @Id
    @GeneratedValue(
        generator = "shipment_seq",
        strategy = GenerationType.SEQUENCE
    )
    @SequenceGenerator(
        name = "shipment_seq",
        sequenceName = "shipment_seq",
        allocationSize = 1
    )
    @Column(name = "shipment_id")
    private Long id;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(
            name = "cep",
            column = @Column(name = "shipment_cep")
        ),
        @AttributeOverride(
            name = "street",
            column = @Column(name = "shipment_street")
        ),
        @AttributeOverride(
            name = "state",
            column = @Column(name = "shipment_state")
        ),
        @AttributeOverride(
            name = "number",
            column = @Column(name = "shipment_number")
        ),
        @AttributeOverride(
            name = "additionalInfo",
            column = @Column(name = "shipment_additional_info")
        )
    })
    private Address address;

    @NotNull
    @Column(name = "shipment_receiver")
    private String receiver;

    @NotNull
    @Column(name = "shipment_arrival_dt")
    private LocalDate arrivalDate;

    @NotNull
    @Column(name = "shipment_arrived")
    private boolean arrived;

    @NotNull
    @ManyToOne(
        fetch = FetchType.EAGER, 
        cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.DETACH
        }
    )
    @JoinColumn(
        name = "employee_id",
        referencedColumnName = "employee_id",
        foreignKey = @ForeignKey(name = "employee_fk")
    )
    private Employee employee;

    @NotNull
    @OneToMany(
        mappedBy = "shipment",
        fetch = FetchType.EAGER,
        cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.REMOVE
        }
    )
    private Set<Package> packages = new LinkedHashSet<>();

    public Shipment() {
        this.address = new Address();
        this.employee = new Employee();
    }

    public Shipment(Address address, @NotNull String receiver, @NotNull LocalDate arrivalDate, @NotNull Employee employee, @NotNull Set<Package> packages) {
        this.address = address;
        this.receiver = receiver;
        this.arrivalDate = arrivalDate;
        this.arrived = false;
        this.employee = employee;
        this.packages = packages;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public LocalDate getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(LocalDate arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public boolean isArrived() {
        return arrived;
    }

    public void setArrived(boolean arrived) {
        this.arrived = arrived;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Set<Package> getPackages() {
        return packages;
    }

    public void setPackages(Set<Package> packages) {
        this.packages = packages;
    }

    @Override
    public String toString() {
        return "Shipment [id=" + id + ", address=" + address + ", receiver=" + receiver + ", arrivalDate=" + arrivalDate + ", arrived=" + arrived + ", employee=" + employee + ", packages=" + packages + "]";
    }
}
