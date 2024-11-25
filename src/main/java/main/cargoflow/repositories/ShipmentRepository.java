package main.cargoflow.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import main.cargoflow.models.Shipment;

public interface ShipmentRepository extends JpaRepository<Shipment, Long> {}
