package cz.kpjhomework.serviceregistry.controller;

import cz.kpjhomework.serviceregistry.model.Service;
import cz.kpjhomework.serviceregistry.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
public class ServiceController {

    private final ServiceService serviceService;

    @Autowired
    public ServiceController(ServiceService service) {
        this.serviceService = service;
    }

    @GetMapping("/services/")
    public List<Service> getRegistredServices() {
        return serviceService.getRegisteredServices();
    }

    @GetMapping("/services/current")
    public Service getCurrentService() {
        return serviceService.getCurrentService();
    }

    @GetMapping("/services/{name}")
    public Optional<Service> getServiceByName(@PathVariable("name") String name) {
        Optional<Service> service = serviceService.getServiceByName(name);
        if (service.isPresent())
            return service;
        else
            throw new ResponseStatusException(NOT_FOUND, "The service name you've searched for does not exist");
    }

    @PostMapping("/services/register")
    public void registerSelf() {
        try {
            serviceService.sendServiceInformationFanout();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
