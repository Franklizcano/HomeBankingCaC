package com.cac.homebanking.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "transfers")
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "origin_id")
    private Long originId;
    @Column(name = "target_id")
    private Long targetId;
    private LocalDateTime date = LocalDateTime.now();
    private BigDecimal amount;
}
