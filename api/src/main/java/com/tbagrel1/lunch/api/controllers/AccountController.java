package com.tbagrel1.lunch.api.controllers;

import com.tbagrel1.lunch.api.db.models.Account;
import com.tbagrel1.lunch.api.db.repositories.AccountRepository;
import com.tbagrel1.lunch.core.models.input.InputAccount;
import com.tbagrel1.lunch.core.models.output.OutputAccount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping(path = "/account",
                    method = RequestMethod.POST,
                    consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping("/account")
    public OutputAccount postAccount(@RequestBody InputAccount inputAccount) {
        var account = Account.fromInput(inputAccount);
        this.accountRepository.save(account);
        return account.toOutput();
    }

    @RequestMapping(path = "/account",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public List<OutputAccount> getAccounts() {
        return StreamSupport.stream(accountRepository.findAll().spliterator(), true)
                .map(Account::toOutput)
                .collect(Collectors.toList());
    }

    @RequestMapping(path = "/account/{id}",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public OutputAccount getAccount(@PathVariable(value = "id") long id) {
        var account = this.accountRepository.findById(id);
        return account.toOutput();
    }
}
