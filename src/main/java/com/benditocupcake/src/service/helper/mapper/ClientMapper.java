package com.benditocupcake.src.service.helper.mapper;

import com.benditocupcake.src.controller.dto.request.client.ClientCreateRequest;
import com.benditocupcake.src.controller.dto.request.client.ClientUpdateRequest;
import com.benditocupcake.src.controller.dto.response.ClientPaginatedResponse;
import com.benditocupcake.src.controller.dto.response.ClientResponse;
import com.benditocupcake.src.persistence.entity.ClientEntity;
import com.benditocupcake.src.persistence.entity.UserEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring", 
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ClientMapper {

    ClientMapper CLIENT_MAPPER = Mappers.getMapper(ClientMapper.class);

    ClientResponse toResponse(ClientEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "request.name")
    @Mapping(target = "email", source = "request.email")
    @Mapping(target = "taxId", source = "request.taxId")
    @Mapping(target = "phone", source = "request.phone")
    @Mapping(target = "address", source = "request.address")
    @Mapping(target = "birthDate", source = "request.birthDate")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", expression = "java(localDateTimeNow())")
    @Mapping(target = "user", source = "user")
    ClientEntity toEntity(ClientCreateRequest request, UserEntity user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "address", source = "request.address")
    @Mapping(target = "email", source = "request.email")
    @Mapping(target = "phone", source = "request.phone")
    @Mapping(target = "birthDate", source = "request.birthDate")
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "taxId", source = "entity.taxId")
    @Mapping(target = "user", source = "entity.user")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "updatedAt", expression = "java(localDateTimeNow())")
    ClientEntity updateEntity(ClientEntity entity, ClientUpdateRequest request);

    default LocalDateTime localDateTimeNow(){
        return LocalDateTime.now();
    }

    default ClientPaginatedResponse toPaginatedResponse(Page<ClientEntity> page) {
        return toPaginatedResponse(page, page.getContent());
    }

    default ClientPaginatedResponse toPaginatedResponse(Page<ClientEntity> page, List<ClientEntity> content) {
        List<ClientResponse> clients = content.stream()
                .map(this::toResponse)
                .toList();

        Pagination pagination = Pagination.builder()
                .page(page.getNumber())
                .pageSize(page.getSize())
                .total(page.getTotalElements())
                .build();

        ClientPaginatedResponse response = new ClientPaginatedResponse();
        response.setPagination(pagination);
        response.setClients(clients);

        return response;
    }
    
}
