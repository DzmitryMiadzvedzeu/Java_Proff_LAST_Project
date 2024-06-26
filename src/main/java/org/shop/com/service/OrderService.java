package org.shop.com.service;

import org.shop.com.dto.OrderStatusDto;
import org.shop.com.entity.OrderEntity;

import java.util.List;

public interface OrderService {

    OrderEntity create(OrderEntity entity);

    List<OrderEntity> getAll();

    OrderStatusDto getStatus(long id);

    OrderEntity delete(long id);
}