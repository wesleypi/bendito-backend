package com.benditocupcake.src.controller.dto.request.client;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientUpdateRequest {
    @Email(message = "Email deve ser válido")
    private String email;
    private String phone;
    private AddressRequest address;
    @Past(message = "Data de nascimento deve ser no passado")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthDate;
}