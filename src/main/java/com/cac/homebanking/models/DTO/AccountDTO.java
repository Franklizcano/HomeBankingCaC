package com.cac.homebanking.models.DTO;

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
    private Integer number;
    private BigDecimal balance;
    @Column(name = "user_id")
    private Long userId;
}
