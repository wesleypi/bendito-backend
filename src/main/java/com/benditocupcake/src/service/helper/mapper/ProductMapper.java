package com.benditocupcake.src.service.helper.mapper;

import com.benditocupcake.src.controller.dto.request.product.ProductCreateRequest;
import com.benditocupcake.src.controller.dto.request.product.ProductUpdateRequest;
import com.benditocupcake.src.controller.dto.response.ProductPaginatedResponse;
import com.benditocupcake.src.controller.dto.response.ProductResponse;
import com.benditocupcake.src.persistence.entity.ProductEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;


import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductMapper {

    ProductMapper PRODUCT_MAPPER = Mappers.getMapper(ProductMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", constant = "true")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    ProductEntity toEntity(ProductCreateRequest request);

    ProductResponse toResponse(ProductEntity entity);

    default ProductPaginatedResponse toPaginatedResponse(Page<ProductEntity> page) {
        return toPaginatedResponse(page, page.getContent());
    }

    default ProductPaginatedResponse toPaginatedResponse(Page<ProductEntity> page, List<ProductEntity> content) {
        List<ProductResponse> products = content.stream()
                .map(this::toResponse)
                .toList();

        Pagination pagination = Pagination.builder()
                .page(page.getNumber())
                .pageSize(page.getSize())
                .total(page.getTotalElements())
                .build();

        ProductPaginatedResponse response = new ProductPaginatedResponse();
        response.setPagination(pagination);
        response.setProducts(products);

        return response;
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromRequest(ProductUpdateRequest request, @MappingTarget ProductEntity entity);
}