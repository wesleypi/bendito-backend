package com.benditocupcake.src.controller;

import com.benditocupcake.src.controller.dto.request.auth.ChangePasswordRequest;
import com.benditocupcake.src.controller.dto.request.auth.CreateTokenRequest;
import com.benditocupcake.src.controller.dto.request.auth.RecoverPasswordRequest;
import com.benditocupcake.src.controller.dto.request.auth.RefreshTokenRequest;
import com.benditocupcake.src.controller.dto.response.auth.CreateTokenResponse;
import com.benditocupcake.src.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/token")
    public CreateTokenResponse createToken(@RequestBody CreateTokenRequest request) {
        return authService.createToken(request);
    }

    @PostMapping("/refresh")
    public CreateTokenResponse refreshToken(@RequestBody RefreshTokenRequest request) {
        return authService.refreshToken(request);
    }

    @PostMapping("/recover")
    public ResponseEntity<String> recoverPassword(@RequestBody RecoverPasswordRequest request) {
        String code = authService.recoverPassword(request);
        return ResponseEntity.ok(code);
    }

    @PutMapping("/password")
    public ResponseEntity<Void> changePassword(@RequestBody ChangePasswordRequest request) {
        authService.changePassword(request);
        return ResponseEntity.noContent().build();
    }
}