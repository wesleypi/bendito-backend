package com.benditocupcake.src.controller.dto.request.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordRequest {
    @NotBlank
    @Pattern(regexp = "\\d{6}", message = "o codigo precisa ter 6 digitos")
    private String code;

    @NotBlank
    private String newPassword;
}
