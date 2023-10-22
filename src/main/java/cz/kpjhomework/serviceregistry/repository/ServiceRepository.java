package cz.kpjhomework.serviceregistry.repository;

import cz.kpjhomework.serviceregistry.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<Service, Integer> {
}
