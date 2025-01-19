package com.cac.homebanking.model.DTO;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
public class AccountDTO {
    private Long id;
    private Long number;
    private BigDecimal balance;
    @Column(name = "user_id")
    private Long userId;

    public AccountDTO(Long number, BigDecimal balance, Long userId) {
        this.number = number;
        this.balance = balance;
        this.userId = userId;
    }
}