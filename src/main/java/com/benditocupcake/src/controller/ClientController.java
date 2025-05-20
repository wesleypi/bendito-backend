package com.benditocupcake.src.controller;

import com.benditocupcake.src.controller.dto.request.client.ClientCreateRequest;
import com.benditocupcake.src.controller.dto.request.client.ClientUpdateRequest;
import com.benditocupcake.src.controller.dto.response.ClientPaginatedResponse;
import com.benditocupcake.src.controller.dto.response.ClientResponse;
import com.benditocupcake.src.service.ClientService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("permitAll()")
    public ClientResponse createClient(@Valid @RequestBody ClientCreateRequest request) {
        return clientService.createClient(request);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ClientResponse getClientById(@PathVariable Long id) {
        return clientService.getClientById(id);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ClientPaginatedResponse getAllClients(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "20") Integer pageSize) {
        return clientService.getAllClients(page, pageSize);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ClientResponse updateClient(
            @PathVariable Long id,
            @Valid @RequestBody ClientUpdateRequest request) {
        return clientService.updateClient(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deactivateClient(
            @PathVariable Long id) {
        clientService.deactivateClient(id);
        return ResponseEntity.noContent().build();
    }
}
