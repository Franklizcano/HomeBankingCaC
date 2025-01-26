package com.cac.homebanking.controller;

import com.cac.homebanking.exception.NotFoundException;
import com.cac.homebanking.model.DTO.TransferDTO;
import com.cac.homebanking.service.TransferService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
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
    public ResponseEntity performTransfer(@RequestBody TransferDTO transferDTO) {
        transferService.publish(transferDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}