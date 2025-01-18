package com.cac.homebanking.service;

import com.cac.homebanking.exception.NotFoundException;
import com.cac.homebanking.mapper.AccountMapper;
import com.cac.homebanking.model.Account;
import com.cac.homebanking.model.DTO.AccountDTO;
import com.cac.homebanking.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

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
        Account account = accountRepository.findById(id).orElseThrow(() -> new NotFoundException("" + id));
        return AccountMapper.accountEntityToDTO(account);
    }

    public AccountDTO createAccount(AccountDTO accountDTO) throws NotFoundException {
        if (userService.existsById(accountDTO.getUserId())) {
            return AccountMapper.accountEntityToDTO(accountRepository.save(AccountMapper.accountDTOToEntity(accountDTO)));
        } else {
            throw new NotFoundException("This account isn't already existing");
        }
    }

    public void withdraw(BigDecimal amount, Long idOrigin) throws NotFoundException {
        Account account = accountRepository.findById(idOrigin).orElseThrow(() -> new NotFoundException("The account is not found with id: " + idOrigin));

        if(account.getBalance().compareTo(amount) > 0) {
            account.setBalance(account.getBalance().subtract(amount));
            accountRepository.save(account);
        }

        AccountMapper.accountEntityToDTO(account);
    }

    public void deposit(BigDecimal amount, Long idTarget) throws NotFoundException {
        Account account = accountRepository.findById(idTarget).orElseThrow(() -> new NotFoundException("The account is not found with id: " + idTarget));

        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account);

        AccountMapper.accountEntityToDTO(account);
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
