package com.cac.homebanking.controller;

import com.cac.homebanking.client.DTO.USDResponse;
import com.cac.homebanking.client.DolarApiClient;
import com.cac.homebanking.exception.NotFoundException;
import com.cac.homebanking.model.DTO.AccountDTO;
import com.cac.homebanking.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1")
public class AccountController {
    private final AccountService accountService;
    private final DolarApiClient dolarApiClient;

    AccountController(final AccountService accountService, DolarApiClient dolarApiClient) {
        this.accountService = accountService;
        this.dolarApiClient = dolarApiClient;
    }

    @GetMapping(value = "/accounts")
    public ResponseEntity<List<AccountDTO>> getAllAccounts() {
        return ResponseEntity.ok().body(accountService.getAllAccounts());
    }

    @GetMapping(value = "/accounts/{accountId}")
    public ResponseEntity<AccountDTO> getAccount(@PathVariable Long accountId) throws NotFoundException {
        return ResponseEntity.ok().body(accountService.getAccountById(accountId));
    }

    @PostMapping(value = "/accounts")
    public ResponseEntity<AccountDTO> createAccount(@RequestBody AccountDTO accountDTO) throws NotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.createAccount(accountDTO));
    }

    @PutMapping(value = "/accounts/{accountId}")
    public ResponseEntity<AccountDTO> updateAccount(@PathVariable Long accountId, @RequestBody AccountDTO accountDTO) throws NotFoundException {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(accountService.update(accountId, accountDTO));
    }

    @DeleteMapping(value = "/accounts/{accountId}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long accountId) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(accountService.delete(accountId));
    }

    @GetMapping(value = "/accounts/usd/official")
    public ResponseEntity<USDResponse> getOfficialUSD() {
        return ResponseEntity.ok().body(dolarApiClient.getOfficialUSD());
    }
}