package com.benditocupcake.src.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductPaginatedResponse {
    private Pagination pagination;
    private List<ProductResponse> products = new ArrayList<>();
}