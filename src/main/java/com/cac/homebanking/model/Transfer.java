package com.cac.homebanking.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "transfers")
public class Transfer {
    @Id
    @UuidGenerator
    private UUID id;
    @Column(name = "origin_id")
    private UUID originId;
    @Column(name = "target_id")
    private UUID targetId;
    @Column(name = "identifier_type")
    private IdentifierType identifierType;
    private ZonedDateTime date = ZonedDateTime.now();
    private BigDecimal amount;
    @Enumerated
    private TransferStatus status;
}
