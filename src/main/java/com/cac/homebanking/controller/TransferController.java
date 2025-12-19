package com.cac.homebanking.controller;

import com.cac.homebanking.exception.NotFoundException;
import com.cac.homebanking.model.dto.TransferDto;
import com.cac.homebanking.service.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class TransferController {

    private final TransferService transferService;

    @GetMapping(value = "/transfers")
    public ResponseEntity<List<TransferDto>> getAllTransfers() {
        return ResponseEntity.ok().body(transferService.getTransfers());
    }

    @GetMapping(value = "/transfers/{transferId}")
    public ResponseEntity<TransferDto> getTransfer(@PathVariable String transferId) throws NotFoundException {
        return ResponseEntity.ok().body(transferService.getTransferById(transferId));
    }

    @PostMapping(value = "/transfers")
    public ResponseEntity performTransfer(@RequestBody TransferDto transferDTO) {
        transferService.publish(transferDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}