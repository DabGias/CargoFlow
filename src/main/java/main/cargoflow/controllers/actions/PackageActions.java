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
import main.cargoflow.models.Package;
import main.cargoflow.repositories.PackageRepository;

@RestController
@RequestMapping("/packages/actions")
public class PackageActions {
    
    @Autowired
    PackageRepository repo;

    @GetMapping
    public List<Package> index() {
        return repo.findAll();
    }

    @GetMapping("{id}")
    public Package show(@PathVariable Long id) {
        return repo.findById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
    }

    @PostMapping
    public Package create(@Valid @RequestBody Package _package) {
        repo.save(_package);

        return _package;
    }

    @PutMapping("{id}")
    public Package update(@PathVariable Long id, @Valid @RequestBody Package newPackage) {
        Package _package = repo.findById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );

        BeanUtils.copyProperties(newPackage, _package, "id");

        repo.save(_package);

        return _package;
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Package> destroy(@PathVariable Long id) {
        Package _package = repo.findById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );

        repo.delete(_package);

        return ResponseEntity.noContent().build();
    }
}
