package main.cargoflow.controllers.routes;

import java.util.ArrayList;
import java.util.List;

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
import main.cargoflow.models.Package;
import main.cargoflow.models.Shipment;
import main.cargoflow.repositories.EmployeeRepository;
import main.cargoflow.repositories.PackageRepository;
import main.cargoflow.repositories.ShipmentRepository;

@Controller
@RequestMapping("/shipments")
public class ShipmentRoutes {
    
    @Autowired
    ShipmentRepository repo;

    @Autowired
    PackageRepository packageRepo;

    @Autowired
    EmployeeRepository employeeRepo;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("shipments", repo.findAll());

        return "shipments/index";
    }

    @GetMapping("/create")
    public String createForm(Shipment shipment, Model model) {
        List<Package> packages = new ArrayList<>();
        
        packageRepo.findAll().forEach(_package -> {
            if (_package.getShipment() == null) {
                packages.add(_package);
            }
        });

        model.addAttribute("employees", employeeRepo.findAll());
        model.addAttribute("packages", packages);

        return "shipments/form";
    }

    @PostMapping("/create")
    public String create(@Valid Shipment shipment) { 
        shipment.getPackages().forEach(_package -> {
            _package.setShipment(shipment);
        });

        repo.save(shipment);

        return "redirect:/shipments";
    }

    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Long id, Model model) {
        Shipment shipment = repo.findById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
        List<Package> packages = new ArrayList<>();

        shipment.getPackages().forEach(_package -> {
           packages.add(_package); 
        });
        
        packageRepo.findAll().forEach(_package -> {
            if (_package.getShipment() == null) {
                packages.add(_package);
            }
        });

        model.addAttribute("employees", employeeRepo.findAll());
        model.addAttribute("packages", packages);
        model.addAttribute("shipment", shipment);

        return "shipments/update-form";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id, @Valid Shipment newShipment) {
        Shipment shipment = repo.findById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );

        shipment.getPackages().forEach(_package -> {
            _package.setShipment(null);
        });

        newShipment.getPackages().forEach(_package -> {
            _package.setShipment(shipment);
        });

        BeanUtils.copyProperties(newShipment, shipment, "id");

        repo.save(shipment);
        
        return "redirect:/shipments";
    }

    @GetMapping("/set/arrived/{id}")
    public String setArrived(@PathVariable Long id) {
        Shipment shipment = repo.findById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );

        shipment.setArrived(true);

        repo.save(shipment);

        return "redirect:/shipments";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        repo.deleteById(id);

        return "redirect:/shipments";
    }
}
