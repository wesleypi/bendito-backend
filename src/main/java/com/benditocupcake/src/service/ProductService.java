package com.benditocupcake.src.service;

import com.benditocupcake.src.controller.dto.request.product.ProductCreateRequest;
import com.benditocupcake.src.controller.dto.request.product.ProductUpdateRequest;
import com.benditocupcake.src.controller.dto.response.ProductPaginatedResponse;
import com.benditocupcake.src.controller.dto.response.ProductResponse;
import com.benditocupcake.src.persistence.entity.ProductEntity;
import com.benditocupcake.src.persistence.repository.ProductRepository;
import com.benditocupcake.src.security.SecurityUtils;
import com.benditocupcake.src.service.exception.BusinessException;
import com.benditocupcake.src.service.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static com.benditocupcake.src.service.helper.mapper.ProductMapper.PRODUCT_MAPPER;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final SecurityUtils securityUtils;

    @Transactional
    public ProductResponse createProduct(ProductCreateRequest request) {
        // Verificar se já existe um produto com o mesmo nome
        if (productRepository.existsByName(request.getName())) {
            throw new BusinessException("Já existe um produto ativo com este nome");
        }

        var productEntity = PRODUCT_MAPPER.toEntity(request);
        var savedProduct = productRepository.save(productEntity);

        return PRODUCT_MAPPER.toResponse(savedProduct);
    }

    public ProductResponse getProductById(Long id) {
        var product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));

        return PRODUCT_MAPPER.toResponse(product);
    }

    public ProductPaginatedResponse getAllProducts(Integer page, Integer pageSize, String category) {
        var pageable = PageRequest.of(page, pageSize, Sort.by("name").ascending());
        
        boolean isAdmin = securityUtils.hasRole("ADMIN");
        
        var productsPage = productRepository.findAllByCategory(category, pageable);

        if (!isAdmin) {
            var activeProductsPage = productsPage.filter(ProductEntity::getActive).toList();

            return PRODUCT_MAPPER.toPaginatedResponse(productsPage, activeProductsPage);
        }

        return PRODUCT_MAPPER.toPaginatedResponse(productsPage);
    }

    @Transactional
    public ProductResponse updateProduct(Long id, ProductUpdateRequest request) {
        var product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));

        // Verificar se já existe outro produto com o mesmo nome
        if (Objects.nonNull(request.getName()) && !request.getName().equals(product.getName()) &&
            productRepository.existsByName(request.getName())) {
            throw new BusinessException("Já existe um produto ativo com este nome");
        }

        PRODUCT_MAPPER.updateEntityFromRequest(request, product);
        
        var updatedProduct = productRepository.save(product);
        return PRODUCT_MAPPER.toResponse(updatedProduct);
    }

    @Transactional
    public void deactivateProduct(Long id) {
        var product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));

        product.deactivate();
        productRepository.save(product);
    }
}