package ru.yamoney.payments.mappers;

import org.mapstruct.Mapper;
import ru.yamoney.payments.dto.PaymentDto;
import ru.yamoney.payments.entity.Payment;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    PaymentDto entityToDto(Payment payment);
}
