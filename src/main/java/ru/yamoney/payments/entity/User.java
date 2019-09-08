package ru.yamoney.payments.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="users")
@Data
public class User {
    @Id
    private Long id;
    private String name;
}
