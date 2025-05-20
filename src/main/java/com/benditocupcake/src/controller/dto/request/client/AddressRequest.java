package com.benditocupcake.src.controller.dto.request.client;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressRequest {
    @NotBlank(message = "Logradouro é obrigatório")
    private String street;
    private String number;
    private String complement;
    @NotBlank(message = "Bairro é obrigatório")
    private String neighborhood;
    @NotBlank(message = "Cidade é obrigatória")
    private String city;
    @NotBlank(message = "Estado é obrigatório")
    private String state;
    @NotBlank(message = "CEP é obrigatório")
    private String zipCode;
}
