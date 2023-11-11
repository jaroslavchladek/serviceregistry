package cz.kpjhomework.serviceregistry.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import cz.kpjhomework.serviceregistry.dto.ServiceDTO;
import cz.kpjhomework.serviceregistry.model.Service;

@Component
@Mapper(componentModel = "spring")
public interface ServiceMapper {

    ServiceDTO serviceToServiceDTO(Service service);

    @InheritInverseConfiguration
    Service serviceDTOToService(ServiceDTO dto);

}
