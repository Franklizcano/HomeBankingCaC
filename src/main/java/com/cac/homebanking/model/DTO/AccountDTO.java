package com.cac.homebanking.model.DTO;

import com.cac.homebanking.model.Currency;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class AccountDTO {
    private Long id;
    private Long number;
    private BigDecimal balance;
    @Column(name = "user_id")
    private Long userId;
    @Enumerated(EnumType.STRING)
    private Currency currency;
}