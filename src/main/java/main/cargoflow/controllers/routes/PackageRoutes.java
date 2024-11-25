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
import main.cargoflow.models.Package;
import main.cargoflow.repositories.PackageRepository;
import main.cargoflow.repositories.ShipmentRepository;

@Controller
@RequestMapping("/packages")
public class PackageRoutes {
    
    @Autowired
    PackageRepository repo;

    @Autowired
    ShipmentRepository shipmentRepo;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("packages", repo.findAll());

        return "packages/index";
    }

    @GetMapping("/create")
    public String createForm(Package _package) {
        return "packages/form";
    }

    @PostMapping("/create")
    public String create(@Valid Package _package) {
        repo.save(_package);

        return "redirect:/packages";
    }

    @GetMapping("/add")
    public String addForm(Package _package) {
        return "packages/add-form";
    }

    @PostMapping("/add")
    public String add(@Valid Package _package) {
        repo.save(_package);

        return "redirect:/shipments/create";
    }

    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Long id, Model model) {
        Package _package = repo.findById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );

        model.addAttribute("package", _package);
        
        return "packages/update-form";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id, @Valid Package newPackage) {
        Package _package = repo.findById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );

        newPackage.setShipment(_package.getShipment());

        BeanUtils.copyProperties(newPackage, _package, "id");

        repo.save(_package);
        
        return "redirect:/packages";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        repo.deleteById(id);

        return "redirect:/packages";
    }
}
