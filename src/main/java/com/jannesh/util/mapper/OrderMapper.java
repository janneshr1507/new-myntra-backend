package com.jannesh.util.mapper;

import com.jannesh.dto.order.OrderDTO;
import com.jannesh.entity.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDTO toDTO(Order order);
}
