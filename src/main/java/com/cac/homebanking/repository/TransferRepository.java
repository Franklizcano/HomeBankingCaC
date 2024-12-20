package com.cac.homebanking.repository;

import com.cac.homebanking.model.DTO.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferRepository extends JpaRepository<Transfer, Long> {
}
