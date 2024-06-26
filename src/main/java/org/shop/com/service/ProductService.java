package org.shop.com.service;

import org.shop.com.dto.ProductCreateDto;
import org.shop.com.entity.ProductEntity;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    ProductEntity create(ProductCreateDto productCreateDto);

    List<ProductEntity> getAll(String category, BigDecimal minPrice, BigDecimal maxPrice,
                               BigDecimal discountPrice, String sort);

    void delete(long id);

    ProductEntity findById(long id);

    ProductEntity update (ProductEntity productEntity);
}