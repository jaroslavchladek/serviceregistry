package cz.kpjhomework.serviceregistry.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import cz.kpjhomework.serviceregistry.dao.ServiceDAO;
import cz.kpjhomework.serviceregistry.model.Service;
import cz.kpjhomework.serviceregistry.repository.ServiceRepository;
import cz.kpjhomework.serviceregistry.service.ServiceService;

@AutoConfigureMockMvc
@SpringBootTest
public class ServiceControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ServiceRepository repository;

    @Autowired
    ServiceDAO dao;

    @Autowired
    ServiceService service;

    @Test
    void registerSelf_ok() throws Exception {
        mockMvc.perform(post("/services/register")
                       .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        Assertions.assertDoesNotThrow(() -> service.getRegisteredServices());
        List<Service> services = dao.getRegisteredServices();
        Assertions.assertEquals(services.size(), 1);
    }

}
