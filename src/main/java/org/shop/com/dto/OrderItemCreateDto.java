package org.shop.com.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemCreateDto {

    private Long productId;

    private Integer quantity;

    private BigDecimal priceAtPurchase;

}
