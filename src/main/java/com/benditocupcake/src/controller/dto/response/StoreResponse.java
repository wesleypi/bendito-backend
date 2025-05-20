package com.benditocupcake.src.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StoreResponse {
    private Long id;
    private String name;
    private String cnpj;
    private String phone;
    private String email;
    private Map<String, String> businessHours;
    private AddressResponse address;
}