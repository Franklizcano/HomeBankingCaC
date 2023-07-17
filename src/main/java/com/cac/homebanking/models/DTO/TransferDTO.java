package com.cac.homebanking.models.DTO;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
public class TransferDTO {
    private Long id;
    private Long originId;
    private Long targetId;
    private LocalDateTime date;
    private BigDecimal amount;
}
