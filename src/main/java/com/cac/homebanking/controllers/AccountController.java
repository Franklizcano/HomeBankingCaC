package com.cac.homebanking.controllers;

import com.cac.homebanking.models.DTO.AccountDTO;
import com.cac.homebanking.services.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(value = "/accounts")
    public ResponseEntity<List<AccountDTO>> getAllAccounts() {
        return ResponseEntity.ok().body(accountService.getAllAccounts());
    }

    @GetMapping(value = "/accounts/{account_id}")
    public ResponseEntity<AccountDTO> getAccount(@PathVariable Long account_id) {
        return ResponseEntity.ok().body(accountService.getAccountById(account_id));
    }

    @PostMapping(value = "/accounts")
    public ResponseEntity<AccountDTO> createAccount(@RequestBody AccountDTO accountDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.createAccount(accountDTO));
    }

    @PutMapping(value = "/accounts/{account_id}")
    public ResponseEntity<AccountDTO> updateAccount(@PathVariable Long account_id, @RequestBody AccountDTO accountDTO) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(accountService.update(account_id, accountDTO));
    }

    @DeleteMapping(value = "/accounts/{account_id}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long account_id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(accountService.delete(account_id));
    }
}
