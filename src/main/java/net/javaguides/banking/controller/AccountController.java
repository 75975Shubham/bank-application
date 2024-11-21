package net.javaguides.banking.controller;

import net.javaguides.banking.dto.AccountDto;
import net.javaguides.banking.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    private final AccountService accountService;
    //this is sub_branch 
    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    //Add Account REST API
    @PostMapping
    public ResponseEntity<AccountDto> addAccount(@RequestBody AccountDto accountDto){
        return new ResponseEntity<>(accountService.createAccount(accountDto), HttpStatus.CREATED);
    }

    //Get Account REST API
    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable("id") Long id){
       AccountDto accountDto =  accountService.getAccountById(id);
       return ResponseEntity.ok(accountDto);

    }

    //Deposit Balance Rest API
    @PutMapping("/{id}/deposit")
    public ResponseEntity<AccountDto> updateBalance(@PathVariable("id") Long id,
                                                    @RequestBody Map<String, Double> request){
        Double amount =request.get("amount");
        AccountDto accountDto = accountService.deposit(id,amount);
        return ResponseEntity.ok(accountDto);
    }

    //withdraw balance REST API
    @PutMapping("/{id}/withdraw")
     public ResponseEntity<AccountDto> withdrawBalanceLong(@PathVariable("id") Long id ,
                                                           @RequestBody Map<String, Double> request)
    {
        double amount = request.get("amount");
       AccountDto accountDto = accountService.withdraw(id, amount);
       return ResponseEntity.ok(accountDto);
     }

     //get all account REST API
    @GetMapping
    public ResponseEntity<List<AccountDto>> getAllAccounts(){
        List<AccountDto> accounts = accountService.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }

    //delete Account REST API
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable("id") Long id){
        accountService.deleteAccount(id);
        return ResponseEntity.ok("Account is deleted successfully!!");
    }




}
