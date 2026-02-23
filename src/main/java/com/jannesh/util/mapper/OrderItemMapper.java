package com.jannesh.util.mapper;

import com.jannesh.dto.orderItem.OrderItemDTO;
import com.jannesh.entity.OrderItem;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {OrderMapper.class})
public interface OrderItemMapper {
    OrderItemDTO toDTO(OrderItem orderItem);
    List<OrderItemDTO> toDTO(List<OrderItem> orderItemList);

}
