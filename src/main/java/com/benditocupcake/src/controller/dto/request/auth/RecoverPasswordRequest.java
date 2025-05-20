package com.benditocupcake.src.controller.dto.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecoverPasswordRequest {
    @NotBlank
    @Email
    private String email;
}
