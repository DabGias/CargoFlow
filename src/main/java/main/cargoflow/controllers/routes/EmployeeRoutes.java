package main.cargoflow.controllers.routes;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import main.cargoflow.models.Employee;
import main.cargoflow.repositories.EmployeeRepository;
import main.cargoflow.repositories.ShipmentRepository;

@Controller
@RequestMapping("/employees")
public class EmployeeRoutes {
    
    @Autowired
    EmployeeRepository repo;

    @Autowired
    ShipmentRepository shipmentRepo;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("employees", repo.findAll());

        return "employees/index";
    }

    @GetMapping("/create")
    public String createForm(Employee employee) {
        return "employees/form";
    }

    @PostMapping("/create")
    public String create(@Valid Employee employee) {
        repo.save(employee);

        return "redirect:/employees";
    }

    @GetMapping("/add")
    public String addForm(Employee employee) {
        return "employees/add-form";
    }

    @PostMapping("/add")
    public String add(Employee employee) {
        repo.save(employee);

        return "redirect:/shipments/create";
    }

    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Long id, Model model) {
        Employee employee = repo.findById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );

        model.addAttribute("employee", employee);

        return "employees/update-form";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id, @Valid Employee newEmployee) {
        Employee employee = repo.findById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );

        BeanUtils.copyProperties(newEmployee, employee, "id");

        repo.save(employee);
        
        return "redirect:/employees";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        repo.deleteById(id);

        return "redirect:/employees";
    }
}
