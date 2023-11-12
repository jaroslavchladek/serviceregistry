package cz.kpjhomework.serviceregistry.service;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

import cz.kpjhomework.serviceregistry.model.Service;
import cz.kpjhomework.serviceregistry.repository.ServiceRepository;

@Slf4j
@SpringBootTest
public class ServiceServiceTest {

    @Autowired
    ServiceRepository repository;

    @Autowired
    ServiceService service;

    @Test
    void sendAndReceiveRabbitMQMessages_ok() {
        try {
            Assertions.assertDoesNotThrow(() -> {
                service.sendServiceInformationFanout("kpj:dummy1;1234");
                service.sendServiceInformationFanout("kpj:dummy2;2345");
                service.sendServiceInformationFanout("kpj:dummy3;3456");
            });
        }
        catch (Exception e) {
            System.out.println("[ERROR] " + e.getMessage());
            log.info("[ERROR] " + e.getMessage());
        }
        List<Service> services = service.getRegisteredServices();
        Assertions.assertEquals(services.size(), 3 + 1);
    }

}
