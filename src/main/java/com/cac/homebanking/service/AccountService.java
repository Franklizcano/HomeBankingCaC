package com.cac.homebanking.service;

import com.cac.homebanking.exception.BusinessException;
import com.cac.homebanking.exception.InsufficientFundsException;
import com.cac.homebanking.exception.NotFoundException;
import com.cac.homebanking.mapper.AccountMapper;
import com.cac.homebanking.model.Account;
import com.cac.homebanking.model.DTO.AccountDTO;
import com.cac.homebanking.repository.AccountRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final UserService userService;

    public AccountService(final AccountRepository accountRepository,
                          final UserService userService) {
        this.accountRepository = accountRepository;
        this.userService = userService;
    }

    public List<AccountDTO> getAllAccounts() {
        return accountRepository.findAll()
                .stream()
                .map(AccountMapper::accountEntityToDTO)
                .toList();
    }

    public AccountDTO getAccountById(Long id) throws NotFoundException {
        Account account = accountRepository.findById(id).orElseThrow(() -> new NotFoundException("The account is not found with id: " + id));
        return AccountMapper.accountEntityToDTO(account);
    }

    public AccountDTO createAccount(AccountDTO accountDTO) throws NotFoundException {
        if (!userService.existsById(accountDTO.getUserId())) {
            throw new NotFoundException("This user isn't already existing");
        }

        boolean accountExists = accountRepository.countByUserIdAndCurrency(accountDTO.getUserId(), accountDTO.getCurrency()) > 0;

        if (accountExists) {
            throw new BusinessException("The user already has an account of this type", HttpStatus.BAD_REQUEST);
        }

        Account savedAccount = accountRepository.save(AccountMapper.accountDTOToEntity(accountDTO));
        return AccountMapper.accountEntityToDTO(savedAccount);
    }

    public AccountDTO withdraw(BigDecimal amount, AccountDTO account) throws InsufficientFundsException {
        if(account.getBalance().compareTo(amount) >= 0) {
            account.setBalance(account.getBalance().subtract(amount));
            accountRepository.save(AccountMapper.accountDTOToEntity(account));
        } else {
            throw new InsufficientFundsException("The account has not enough money to withdraw");
        }
        return account;
    }

    public AccountDTO deposit(BigDecimal amount, AccountDTO account) {
        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(AccountMapper.accountDTOToEntity(account));
        return account;
    }

    public AccountDTO update(Long id, AccountDTO accountDTO) throws NotFoundException {
        Optional<Account> accountCreated = accountRepository.findById(id);

        if  (accountCreated.isPresent()) {
            Account entity = accountCreated.get();
            Account accountUpdated = AccountMapper.accountDTOToEntity(accountDTO);
            accountUpdated.setId(entity.getId());
            Account saved = accountRepository.save(accountUpdated);
            return AccountMapper.accountEntityToDTO(saved);
        } else {
            throw new NotFoundException("Account is not found with the id " + id);
        }
    }

    public String delete(Long id) {
        if (accountRepository.existsById(id)) {
            accountRepository.deleteById(id);
            return "The account has been deleted.";
        } else {
            return "The account has not been deleted";
        }
    }
}