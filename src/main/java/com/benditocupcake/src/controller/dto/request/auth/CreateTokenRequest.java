package com.benditocupcake.src.controller.dto.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateTokenRequest {
    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;
}
