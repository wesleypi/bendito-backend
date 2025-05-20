package com.benditocupcake.src.controller.dto.request.product;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreateRequest {
    
    @NotBlank(message = "Nome é obrigatório")
    private String name;
    
    @NotBlank(message = "Descrição é obrigatória")
    private String description;
    
    @NotNull(message = "Preço é obrigatório")
    @DecimalMin(value = "0.01", message = "Preço deve ser maior que zero")
    private BigDecimal price;
    
    @NotNull(message = "Quantidade em estoque é obrigatória")
    @DecimalMin(value = "0", inclusive = true, message = "Quantidade em estoque não pode ser negativa")
    private Integer stockQuantity;
    
    private String imageUrl;
    
    @NotBlank(message = "Categoria é obrigatória")
    private String category;
    
    private List<Map<String, Object>> ingredients;
}
