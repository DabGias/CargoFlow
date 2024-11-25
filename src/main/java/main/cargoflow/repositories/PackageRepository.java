package main.cargoflow.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import main.cargoflow.models.Package;
import main.cargoflow.models.Shipment;

public interface PackageRepository extends JpaRepository<Package, Long> {
    List<Package> findByShipment(Shipment shipment);
}
