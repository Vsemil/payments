package ru.yamoney.payments.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yamoney.payments.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
