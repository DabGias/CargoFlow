package main.cargoflow.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import main.cargoflow.models.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {}
