package cz.kpjhomework.serviceregistry.dao;

import cz.kpjhomework.serviceregistry.model.Service;
import cz.kpjhomework.serviceregistry.repository.ServiceRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Repository
public class ServiceDAO {

    private final ServiceRepository serviceRepository;
    @Getter
    private final Service currentService;
    private final List<Service> registeredServices;

    public final static String SERVICE_NAME = "kpj.jaroslavchladek";
    // An anti-collision random generated port
    private final static String SERVICE_PORT = "25695";

    @Autowired
    public ServiceDAO(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
        registeredServices = new ArrayList<>();
        currentService = new Service(new Random().nextInt(), SERVICE_NAME, SERVICE_PORT, LocalDateTime.now());
        this.serviceRepository.save(currentService);
        registeredServices.add(currentService);
    }

    public void addService(Service service) {
        registeredServices.add(service);
        this.serviceRepository.save(service);
    }

    public List<Service> getRegisteredServices() {
        return registeredServices;
    }

}
