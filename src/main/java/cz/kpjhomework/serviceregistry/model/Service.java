package cz.kpjhomework.serviceregistry.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Random;

import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.NotFound;

@Getter
@Entity
public class Service {

    @Id
    private Integer id;
    private String name;
    private String port;
    private LocalDateTime registerTime;

    public Service(@NotNull Integer id, @NotNull String name, @NotNull String port, @NotNull LocalDateTime registerTime) {
        this.id = id;
        this.name = name;
        this.port = port;
        this.registerTime = registerTime;
    }

    public Service() {

    }

    public Service(String service) throws Exception {
        this.registerTime = LocalDateTime.now();
        this.id = new Random().nextInt();
        String[] parts = service.split(";");
        if (parts.length != 2) {
            throw new Exception("Attempt to register an invalid service name");
        }
        this.name = parts[0];
        this.port = parts[1];
    }

    public String toString() {
        return getName() + ";" + getPort();
    }

}
