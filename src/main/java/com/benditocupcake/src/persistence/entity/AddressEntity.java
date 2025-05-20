package com.benditocupcake.src.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "enderecos")
public class AddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "logradouro", nullable = false)
    private String street;

    @Column(name = "numero", nullable = false)
    private String number;

    @Column(name = "complemento")
    private String complement;

    @Column(name = "bairro", nullable = false)
    private String neighborhood;

    @Column(name = "cidade", nullable = false)
    private String city;

    @Column(name = "estado", nullable = false)
    private String state;

    @Column(name = "cep", nullable = false)
    private String zipCode;

    @Column(name = "data_criacao", nullable = false)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "data_atualizacao")
    private LocalDateTime updatedAt;

}