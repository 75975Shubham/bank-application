package net.javaguides.banking.service.impl;

import net.javaguides.banking.dto.AccountDto;
import net.javaguides.banking.entity.Account;
import net.javaguides.banking.mapper.AccountMapper;
import net.javaguides.banking.repository.AccountRepository;
import net.javaguides.banking.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class AccountServiceImpl implements AccountService {
    private AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        Account account = AccountMapper.mapTOAccount(accountDto);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapTOAccountDto(savedAccount);

 }

    @Override
    public AccountDto getAccountById(Long id) {
        Account account= accountRepository.findById(id).
                orElseThrow(()->new RuntimeException("Account does not exist: "+id));
        return AccountMapper.mapTOAccountDto(account);

    }

    @Override
    public AccountDto deposit(Long id, double amount) {
        Account account= accountRepository.findById(id).
                orElseThrow(()-> new RuntimeException("Account does not exist: "+id));
        double currentBalance = account.getBalance();
        double updatedBalance= currentBalance+amount;
        account.setBalance(updatedBalance);
        Account updatedAccount = accountRepository.save(account);
        return AccountMapper.mapTOAccountDto(updatedAccount);
    }

    @Override
    public AccountDto withdraw(Long id, double amount) {
        Account account= accountRepository.findById(id).
                orElseThrow(()->new RuntimeException("Account does not exist: "+id));
        if(account.getBalance()< amount){
            throw new RuntimeException("Insufficient Balance");
        }
        double total =account.getBalance()-amount;
        account.setBalance(total);
        Account updatedAccount = accountRepository.save(account);
        return AccountMapper.mapTOAccountDto(updatedAccount);
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream().map((account)->AccountMapper.mapTOAccountDto(account))
                .collect(Collectors.toList());

    }

    @Override
    public void deleteAccount(Long id) {
        Account account = accountRepository.findById(id).
                orElseThrow(()->new RuntimeException("Account does not exist: "+id));
        accountRepository.deleteById(id);
    }
}
