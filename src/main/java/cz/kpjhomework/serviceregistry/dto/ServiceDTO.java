package cz.kpjhomework.serviceregistry.dto;

import java.time.LocalDateTime;

public record ServiceDTO(Integer id, String name, String port, LocalDateTime registerTime) {
}
