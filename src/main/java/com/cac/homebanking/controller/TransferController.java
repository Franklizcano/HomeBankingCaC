package com.cac.homebanking.controller;

import com.cac.homebanking.exception.NotFoundException;
import com.cac.homebanking.model.DTO.TransferDTO;
import com.cac.homebanking.service.TransferService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class TransferController {

    private final TransferService transferService;

    TransferController(final TransferService transferService) {
        this.transferService = transferService;
    }

    @GetMapping(value = "/transfers")
    public ResponseEntity<List<TransferDTO>> getAllTransfers() {
        return ResponseEntity.ok().body(transferService.getTransfers());
    }

    @GetMapping(value = "/transfers/{transferId}")
    public ResponseEntity<TransferDTO> getTransfer(@PathVariable Long transferId) throws NotFoundException {
        return ResponseEntity.ok().body(transferService.getTransferById(transferId));
    }

    @PostMapping(value = "/transfers")
    public ResponseEntity<TransferDTO> performTransfer(@RequestBody TransferDTO transferDTO) throws NotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(transferService.performTransfer(transferDTO));
    }
}
