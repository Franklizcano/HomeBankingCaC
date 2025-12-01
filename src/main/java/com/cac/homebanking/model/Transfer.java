package com.cac.homebanking.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Setter
@Getter
@Entity
@Table(name = "transfers")
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "CHAR(36)")
    private String id;
    @Column(name = "origin_id")
    private String originId;
    @Column(name = "target_id")
    private String targetId;
    @Column(name = "identifier_type")
    private IdentifierType identifierType;
    private ZonedDateTime date = ZonedDateTime.now();
    private BigDecimal amount;
    @Enumerated
    private TransferStatus status;
}
