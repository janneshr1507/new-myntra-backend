package com.jannesh.util.mapper;

import com.jannesh.dto.payment.PaymentDTO;
import com.jannesh.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    @Mapping(source = "orderId", target = "orderId")
    PaymentDTO toDTO(Payment payment);
}
