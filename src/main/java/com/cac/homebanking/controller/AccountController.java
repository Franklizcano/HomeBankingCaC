package com.cac.homebanking.controller;

import com.cac.homebanking.client.dto.USDResponse;
import com.cac.homebanking.client.DollarApiClient;
import com.cac.homebanking.exception.NotFoundException;
import com.cac.homebanking.model.dto.AccountDto;
import com.cac.homebanking.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final DollarApiClient dollarApiClient;

    @GetMapping(value = "/accounts")
    public ResponseEntity<List<AccountDto>> getAllAccounts() {
        return ResponseEntity.ok().body(accountService.getAllAccounts());
    }

    @GetMapping(value = "/accounts/{accountId}")
    public ResponseEntity<AccountDto> getAccount(@PathVariable String accountId) throws NotFoundException {
        return ResponseEntity.ok().body(accountService.getAccountById(accountId));
    }

    @PostMapping(value = "/accounts")
    public ResponseEntity<AccountDto> createAccount(@RequestBody AccountDto accountDTO) throws NotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.createAccount(accountDTO));
    }

    @PutMapping(value = "/accounts/{accountId}")
    public ResponseEntity<AccountDto> updateAccount(@PathVariable String accountId, @RequestBody AccountDto accountDTO) throws NotFoundException {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(accountService.update(accountId, accountDTO));
    }

    @DeleteMapping(value = "/accounts/{accountId}")
    public ResponseEntity<String> deleteAccount(@PathVariable String accountId) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(accountService.delete(accountId));
    }

    @GetMapping(value = "/accounts/usd/official")
    public ResponseEntity<USDResponse> getOfficialUSD() {
        return ResponseEntity.ok().body(dollarApiClient.getOfficialUSD());
    }
}