package main.cargoflow.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "package_tb")
public class Package {
    
    @Id
    @GeneratedValue(
        generator = "package_seq",
        strategy = GenerationType.SEQUENCE
    )
    @SequenceGenerator(
        name = "package_seq",
        sequenceName = "package_seq",
        allocationSize = 1
    )
    @Column(name = "package_id")
    private Long id;

    @NotBlank
    @Column(name = "package_content")
    private String content;

    @NotNull
    @Column(name = "package_weigth")
    private double weight;

    @NotNull
    @Column(name = "package_width")
    private double width;

    @NotNull
    @Column(name = "package_height")
    private double height;

    @NotNull
    @Column(name = "package_length")
    private double length;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
        name = "shipment_id",
        referencedColumnName = "shipment_id",
        foreignKey = @ForeignKey(name = "package_shipment_fk")
    )
    private Shipment shipment;

    public Package() {}

    public Package(@NotBlank String content, @NotNull double weight, @NotNull double width, @NotNull double height, @NotNull double length) {
        this.content = content;
        this.weight = weight;
        this.width = width;
        this.height = height;
        this.length = length;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public Shipment getShipment() {
        return shipment;
    }

    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }

    @Override
    public String toString() {
        return "Package [id=" + id + ", content=" + content + ", weight=" + weight + ", width=" + width + ", height=" + height + ", length=" + length + "]";
    }
}
