package ru.yamoney.payments.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.yamoney.payments.dto.AmountOfExpensesDto;
import ru.yamoney.payments.entity.Payment;

public interface PaymentsRepository extends JpaRepository<Payment, Long> {

    @Query("select new ru.yamoney.payments.dto.AmountOfExpensesDto(p.senderUser.id, sum(p.amount)) " +
            "from Payment p where p.senderUser.id = :userId group by p.senderUser")
    AmountOfExpensesDto getAmountOfExpenses(@Param("userId") Long userId);
}
