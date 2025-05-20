package com.benditocupcake.src.controller.dto.request.product;

import jakarta.validation.constraints.DecimalMin;
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
public class ProductUpdateRequest {

    private String imageUrl;
    private String name;
    private String description;
    
    @DecimalMin(value = "0.01", message = "Preço deve ser maior que zero")
    private BigDecimal price;
    
    @DecimalMin(value = "0", message = "Quantidade em estoque não pode ser negativa")
    private Integer stockQuantity;
    
    private String category;
    private Boolean active;
    private List<Map<String, Object>> ingredients;
}
