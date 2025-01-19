package com.cac.homebanking.model.DTO;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class TransferDTO {
    private Long id;
    private Long originId;
    private Long targetId;
    private ZonedDateTime date;
    private BigDecimal amount;

    public TransferDTO(Long originId, Long targetId, ZonedDateTime date, BigDecimal amount) {
        this.originId = originId;
        this.targetId = targetId;
        this.date = date;
        this.amount = amount;
    }
}
