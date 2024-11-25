package main.cargoflow.controllers.actions;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import main.cargoflow.models.Shipment;
import main.cargoflow.repositories.PackageRepository;
import main.cargoflow.repositories.ShipmentRepository;

@RestController
@RequestMapping("/shipments/actions")
public class ShipmentActions {
    
    @Autowired
    ShipmentRepository repo;

    @Autowired
    PackageRepository packageRepo;

    @GetMapping
    public List<Shipment> index() {
        return repo.findAll();
    }

    @GetMapping("{id}")
    public Shipment show(@PathVariable Long id) {
        return repo.findById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
    }

    @PostMapping
    public Shipment create(@Valid @RequestBody Shipment shipment) {
        repo.save(shipment);

        return shipment;
    }

    @PutMapping("{id}")
    public Shipment update(@PathVariable Long id, @Valid @RequestBody Shipment newShipment) {
        Shipment shipment = repo.findById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );

        BeanUtils.copyProperties(newShipment, shipment, "id");

        repo.save(shipment);

        return shipment;
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Shipment> destroy(@PathVariable Long id) {
        Shipment shipment = repo.findById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );

        repo.delete(shipment);

        return ResponseEntity.noContent().build();
    }
}
