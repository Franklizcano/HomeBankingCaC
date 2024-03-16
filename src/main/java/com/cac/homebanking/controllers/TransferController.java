package com.cac.homebanking.controllers;

import com.cac.homebanking.exception.NotFoundException;
import com.cac.homebanking.models.DTO.TransferDTO;
import com.cac.homebanking.services.TransferService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class TransferController {

    private final TransferService transferService;

    TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @GetMapping(value = "/transfers")
    public ResponseEntity<List<TransferDTO>> getAllTransfers() {
        return ResponseEntity.ok().body(transferService.getTransfers());
    }

    @GetMapping(value = "/transfers/{transfer_id}")
    public ResponseEntity<TransferDTO> getTransfer(@PathVariable Long transfer_id) throws NotFoundException {
        return ResponseEntity.ok().body(transferService.getTransferById(transfer_id));
    }

    @PostMapping(value = "/transfers")
    public ResponseEntity<TransferDTO> performTransfer(@RequestBody TransferDTO transferDTO) throws NotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(transferService.performTransfer(transferDTO));
    }
}
