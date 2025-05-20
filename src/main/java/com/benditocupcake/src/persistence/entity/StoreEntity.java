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
@Table(name = "lojas")
public class StoreEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String name;

    @Column(name = "cnpj", nullable = false, unique = true)
    private String cnpj;

    @Column(name = "telefone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "horario_funcionamento")
    private String businessHours;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_endereco", referencedColumnName = "id")
    private AddressEntity address;

    @Column(name = "ativo", nullable = false)
    @Builder.Default
    private Boolean active = true;

    @Column(name = "data_criacao", nullable = false)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "data_atualizacao")
    private LocalDateTime updatedAt;

}