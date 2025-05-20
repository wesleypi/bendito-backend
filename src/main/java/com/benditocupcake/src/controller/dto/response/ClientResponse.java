package com.benditocupcake.src.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientResponse {
    private String id;
    private String name;
    private String email;
    private String taxId;
    private String phone;
    private AddressResponse address;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}