package ru.yamoney.payments.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yamoney.payments.config.ShardingIDConfig;
import ru.yamoney.payments.dto.AmountOfExpensesDto;
import ru.yamoney.payments.dto.PaymentCreateDto;
import ru.yamoney.payments.entity.Payment;
import ru.yamoney.payments.entity.User;
import ru.yamoney.payments.exception.NotFoundException;
import ru.yamoney.payments.repository.PaymentsRepository;
import ru.yamoney.payments.repository.UserRepository;
import ru.yamoney.payments.service.PaymentsService;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentsServiceImpl implements PaymentsService {
    private final PaymentsRepository paymentsRepository;
    private final UserRepository userRepository;

    @Override
    public Payment addPayment(PaymentCreateDto paymentDto) {
        User senderUser = userRepository.findById(paymentDto.getSenderUserId())
                .orElseThrow(() -> new NotFoundException("user sender not found"));
        User recipientUser = userRepository.findById(paymentDto.getRecipientUserId())
                .orElseThrow(() -> new NotFoundException("user recipient not found"));
        Payment payment = new Payment();
        payment.setAmount(new BigDecimal(paymentDto.getAmount()));
        payment.setSenderUser(senderUser);
        payment.setRecipientUser(recipientUser);
        return save(payment);
    }

    @Override
    public List<Payment> findAll() {
        return paymentsRepository.findAll();
    }

    @Override
    public AmountOfExpensesDto getAmountOfExpenses(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("user by id=" + userId + " not found"));
        return paymentsRepository.getAmountOfExpenses(userId);
    }

    @Override
    public Payment save(@NotNull Payment payment) {
        if (payment.getId() == null) {
            payment.setId(new ShardingIDConfig().generateKey().longValue());
        }
        return paymentsRepository.save(payment);
    }
}
