package com.benditocupcake.src.service;

import com.benditocupcake.src.controller.dto.request.client.ClientCreateRequest;
import com.benditocupcake.src.controller.dto.request.client.ClientUpdateRequest;
import com.benditocupcake.src.controller.dto.response.ClientPaginatedResponse;
import com.benditocupcake.src.controller.dto.response.ClientResponse;
import com.benditocupcake.src.persistence.entity.ClientEntity;
import com.benditocupcake.src.persistence.entity.UserEntity;
import com.benditocupcake.src.persistence.repository.ClientRepository;
import com.benditocupcake.src.persistence.repository.UserRepository;
import com.benditocupcake.src.persistence.repository.RoleRepository;
import com.benditocupcake.src.security.SecurityUtils;
import com.benditocupcake.src.service.exception.BusinessException;
import com.benditocupcake.src.service.exception.ResourceConflictException;
import com.benditocupcake.src.service.exception.ResourceNotFoundException;
import com.benditocupcake.src.service.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Objects;

import static com.benditocupcake.src.service.helper.mapper.ClientMapper.CLIENT_MAPPER;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final SecurityUtils securityUtils;

    @Transactional
    public ClientResponse createClient(ClientCreateRequest request) {
        if (clientRepository.existsByEmail(request.getEmail())) {
            throw new ResourceConflictException("Email já cadastrado");
        }

        if (clientRepository.existsByTaxId(request.getTaxId())) {
            throw new ResourceConflictException("CPF já cadastrado");
        }

        var user = UserEntity.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getTaxId())) // Senha inicial é o CPF
                .role(roleRepository.findById(2L)
                      .orElseThrow(() -> new ResourceNotFoundException("Perfil de cliente não encontrado")))
                .build();

        user = userRepository.save(user); // gera o ID do usuário
        var clientEntity = CLIENT_MAPPER.toEntity(request, user);

        var savedClient = clientRepository.save(clientEntity);
        return CLIENT_MAPPER.toResponse(savedClient);
    }

    public ClientResponse getClientById(Long id) {
        var client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));
        
        var currentUserEmail = securityUtils.getCurrentUserEmail();
        var isAdmin = securityUtils.hasRole("ADMIN");
        
        if (!isAdmin && !client.getEmail().equals(currentUserEmail)) {
            throw new UnauthorizedException();
        }
        
        return CLIENT_MAPPER.toResponse(client);
    }

    public ClientPaginatedResponse getAllClients(Integer page, Integer pageSize) {
        var pageable = PageRequest.of(page, pageSize, Sort.by("createdAt").ascending());

        var clients = clientRepository.findAll(pageable);

        return CLIENT_MAPPER.toPaginatedResponse(clients);
    }


    @Transactional
    public ClientResponse updateClient(Long id, ClientUpdateRequest request) {

        var client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));

        var currentUserEmail = securityUtils.getCurrentUserEmail();

        var isAdmin = securityUtils.hasRole("ADMIN");

        if (!isAdmin && !client.getEmail().equals(currentUserEmail)) {
            throw new UnauthorizedException();
        }

        if (Objects.nonNull(request.getEmail()) && !request.getEmail().equals(client.getEmail())
                && (clientRepository.existsByEmail(request.getEmail()))) {
            throw new BusinessException("Email já cadastrado");
        }

        var updatedClient = CLIENT_MAPPER.updateEntity(client, request);
        updatedClient = clientRepository.save(updatedClient);

        return CLIENT_MAPPER.toResponse(updatedClient);
    }

    public void deactivateClient(Long id) {
        clientRepository.deleteById(id);
    }
}