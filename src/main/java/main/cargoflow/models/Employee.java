package main.cargoflow.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(
    name = "employee_tb",
    uniqueConstraints = @UniqueConstraint(
        name = "employee_cpf_uk",
        columnNames = "employee_cpf"
    )
)
public class Employee {
    
    @Id
    @GeneratedValue(
        generator = "employee_seq",
        strategy = GenerationType.SEQUENCE
    )
    @SequenceGenerator(
        name = "employee_seq",
        sequenceName = "employee_seq",
        allocationSize = 1
    )
    @Column(name = "employee_id")
    private Long id;

    @NotBlank
    @Column(name = "employee_name")
    private String name;

    @NotBlank
    @Pattern(regexp = "^\\d{3}\\.?\\d{3}\\.?\\d{3}\\-?\\d{2}$")
    @Column(name = "employee_cpf")
    private String cpf;

    public Employee() {}

    public Employee(@NotBlank String name, @NotBlank @Pattern(regexp = "^\\d{3}\\.?\\d{3}\\.?\\d{3}\\-?\\d{2}$") String cpf) {
        this.name = name;
        this.cpf = cpf;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Override
    public String toString() {
        return "Employee [id=" + id + ", name=" + name + ", cpf=" + cpf + "]";
    }
}
