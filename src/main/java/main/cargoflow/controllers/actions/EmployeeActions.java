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
import main.cargoflow.models.Employee;
import main.cargoflow.repositories.EmployeeRepository;

@RestController
@RequestMapping("/employees/actions")
public class EmployeeActions {

    @Autowired
    EmployeeRepository repo;

    @GetMapping
    public List<Employee> index() {
        return repo.findAll();
    }

    @GetMapping("{id}")
    public Employee show(@PathVariable Long id) {
        return repo.findById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
    }

    @PostMapping
    public Employee create(@Valid @RequestBody Employee employee) {
        repo.save(employee);

        return employee;
    }

    @PutMapping("{id}")
    public Employee update(@PathVariable Long id, @Valid @RequestBody Employee newEmployee) {
        Employee employee = repo.findById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );

        BeanUtils.copyProperties(newEmployee, employee, "id");

        repo.save(employee);

        return employee;
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Employee> destroy(@PathVariable Long id) {
        Employee employee = repo.findById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );

        repo.delete(employee);

        return ResponseEntity.noContent().build();
    }
}
