package com.benditocupcake.src.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.benditocupcake.src.controller.dto.request.auth.ChangePasswordRequest;
import com.benditocupcake.src.controller.dto.request.auth.CreateTokenRequest;
import com.benditocupcake.src.controller.dto.request.auth.RecoverPasswordRequest;
import com.benditocupcake.src.controller.dto.request.auth.RefreshTokenRequest;
import com.benditocupcake.src.controller.dto.response.auth.CreateTokenResponse;
import com.benditocupcake.src.persistence.entity.PasswordRecoveryEntity;
import com.benditocupcake.src.persistence.entity.UserEntity;
import com.benditocupcake.src.persistence.repository.PasswordRecoveryRepository;
import com.benditocupcake.src.persistence.repository.UserRepository;
import com.benditocupcake.src.service.exception.BusinessException;
import com.benditocupcake.src.service.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PasswordRecoveryRepository passwordRecoveryRepository;
    
    @Value("${jwt.secret}")
    private String jwtSecret;
    
    @Value("${jwt.expiration}")
    private Long jwtExpiration;


    @Transactional
    public CreateTokenResponse createToken(CreateTokenRequest request) {
        UserEntity user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BusinessException("Credenciais inválidas"));
        
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BusinessException("Credenciais inválidas");
        }
        
        return generateTokenResponse(user);
    }
    
    @Transactional
    public CreateTokenResponse refreshToken(RefreshTokenRequest request) {
        try {
            var algorithm = Algorithm.HMAC256(jwtSecret);
            var verifier = JWT.require(algorithm).build();
            var decodedJWT = verifier.verify(request.getRefreshToken());
            
            String userEmail = decodedJWT.getSubject();
            
            UserEntity user = userRepository.findByEmail(userEmail)
                    .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
            
            return generateTokenResponse(user);
            
        } catch (Exception e) {
            throw new BusinessException("Refresh token inválido ou expirado");
        }
    }
    
    public String recoverPassword(RecoverPasswordRequest request) {

        final var user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
        final var recovery = passwordRecoveryRepository.findByUser(user);

        // Se já existe um código de recuperação, verifica se ele ainda é válido
        if (recovery.isPresent()) {
            if (recovery.get().getExpirationDate().isAfter(LocalDateTime.now())) {
                return recovery.get().getCode();
            }
            passwordRecoveryRepository.delete(recovery.get());
        }

        final var recoveryEntity = PasswordRecoveryEntity.builder()
                    .user(user)
                    .code(generateRandomCode())
                    .expirationDate(LocalDateTime.now().plusMinutes(15))
                    .build();

        passwordRecoveryRepository.save(recoveryEntity);

        return recoveryEntity.getCode();
    }


    
    @Transactional
    public void changePassword(ChangePasswordRequest request) {
        var recovery = passwordRecoveryRepository.findByCode(request.getCode())
                .orElseThrow(() -> new ResourceNotFoundException("código não encontrado"));

        if (recovery.getExpirationDate().isBefore(LocalDateTime.now())) {
            throw new BusinessException("Código expirado");
        }

        var user = recovery.getUser();

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        userRepository.save(user);
        passwordRecoveryRepository.delete(recovery);
    }

    private String generateRandomCode() {
        return String.format("%06d", new SecureRandom().nextInt(1_000_000));
    }

    private CreateTokenResponse generateTokenResponse(UserEntity user) {
        Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
        
        // Data de expiração do access token
        Date expiresAt = new Date(System.currentTimeMillis() + jwtExpiration);
        
        // Gerar access token
        String accessToken = JWT.create()
                .withSubject(user.getEmail())
                .withExpiresAt(expiresAt)
                .withClaim("roles", List.of(user.getRole().getName()))
                .withIssuedAt(new Date())
                .sign(algorithm);
        
        // Data de expiração do refresh token
        Date refreshExpiresAt = new Date(System.currentTimeMillis() + jwtExpiration / 12);
        
        // Gerar refresh token
        String refreshToken = JWT.create()
                .withSubject(user.getEmail())
                .withExpiresAt(refreshExpiresAt)
                .withIssuedAt(new Date())
                .sign(algorithm);
        
        return CreateTokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .expiresIn(jwtExpiration / 1000) // Converter para segundos
                .build();
    }
}