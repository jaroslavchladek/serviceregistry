package cz.kpjhomework.serviceregistry.service;

import com.rabbitmq.client.*;
import cz.kpjhomework.serviceregistry.dao.ServiceDAO;
import cz.kpjhomework.serviceregistry.model.Service;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

@org.springframework.stereotype.Service
@Slf4j
public class ServiceService {

    private final ServiceDAO serviceDAO;
    private static final String EXCHANGE_NAME = "kpj";

    @Autowired
    public ServiceService(ServiceDAO serviceDAO) {
        this.serviceDAO = serviceDAO;
        try {
            sendServiceInformationFanout(serviceDAO.getCurrentService().toString());
            receiveMessages();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void receiveMessages() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        //factory.setPort(Integer.parseInt(serviceDAO.getCurrentService().getPort()));
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EXCHANGE_NAME, "");

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        log.info(" [*] Waiting for messages. ");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
            log.info(" [x] Received '" + message + "'");
            addUniqueService(message);
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });
    }

    private void addUniqueService(String message) {
        Optional<Service> foundService = serviceDAO.getRegisteredServices()
                .stream()
                .filter(service -> service.toString().equals(message))
                .findFirst();
        if (foundService.isPresent())
            return;
        else {
            try {
                serviceDAO.addService(new Service(message));
            }
            catch (Exception e) {
                System.out.println("[ERROR] Attempt to register an invalid service name");
                log.error("[ERROR] Attempt to register an invalid service name");
            }
        }
    }

    public void sendServiceInformationFanout(String message) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        //factory.setPort(Integer.parseInt(serviceDAO.getCurrentService().getPort()));

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

            channel.basicPublish(EXCHANGE_NAME, "", null,
                                    message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + message + "'");
            log.info(" [x] Sent '" + message + "'");
        }
    }

    public List<Service> getRegisteredServices() {
        return serviceDAO.getRegisteredServices();
    }

    public Service getCurrentService() {
        return serviceDAO.getCurrentService();
    }

    public Optional<Service> getServiceByName(String name) {
        List<Service> services = serviceDAO.getRegisteredServices();
        return services.stream()
                .filter(service -> Objects.equals(service.getName(), name))
                .findFirst();
    }

    public void registerNewService(Service service) {
        serviceDAO.addService(service);
    }

}
