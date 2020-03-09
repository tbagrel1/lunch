package com.tbagrel1.lunch.api.controllers;

import com.tbagrel1.lunch.api.db.models.Account;
import com.tbagrel1.lunch.api.db.repositories.AccountRepository;
import com.tbagrel1.lunch.core.models.input.InputAccount;
import com.tbagrel1.lunch.core.models.output.OutputAccount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping(path = "/account",
                    method = RequestMethod.POST,
                    consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public OutputAccount postAccount(@RequestBody InputAccount inputAccount) {
        var account = Account.fromInput(inputAccount);
        this.accountRepository.save(account);
        return account.toOutput();
    }
}
