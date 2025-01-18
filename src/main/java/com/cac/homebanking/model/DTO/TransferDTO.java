package com.cac.homebanking.model.DTO;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TransferDTO {
    private Long id;
    private Long originId;
    private Long targetId;
    private ZonedDateTime date;
    private BigDecimal amount;
}
