package com.benditocupcake.src.persistence.entity;

import com.benditocupcake.src.persistence.converter.JsonConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "produtos")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String name;

    @Column(name = "descricao", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "preco", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "quantidade_estoque", nullable = false)
    private Integer stockQuantity;

    @Column(name = "url_imagem")
    private String imageUrl;

    @Column(name = "categoria", nullable = false)
    private String category;

    @Column(name = "ingredientes", columnDefinition = "TEXT")
    @Convert(converter = JsonConverter.class)
    private List<Map<String, Object>> ingredients;

    @Column(name = "ativo", nullable = false)
    @Builder.Default
    private Boolean active = true;

    @Column(name = "data_criacao", nullable = false, updatable = false)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "data_atualizacao")
    private LocalDateTime updatedAt;

    public void deactivate() {
        if(!active) return;
        this.active = false;
        this.updatedAt = LocalDateTime.now();
    }
}