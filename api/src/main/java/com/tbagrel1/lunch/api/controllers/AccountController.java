package com.tbagrel1.lunch.api.controllers;

import com.tbagrel1.lunch.api.db.models.Account;
import com.tbagrel1.lunch.api.db.repositories.AccountRepository;
import com.tbagrel1.lunch.api.exceptions.NotFoundException;
import com.tbagrel1.lunch.core.models.input.InputAccount;
import com.tbagrel1.lunch.core.models.output.OutputAccount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping(path = "api/account",
                    method = RequestMethod.POST,
                    consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OutputAccount> postAccount(@RequestBody InputAccount inputAccount) {
        Account account = Account.fromInput(inputAccount);
        this.accountRepository.save(account);
        return new ResponseEntity<>(account.toOutput(), HttpStatus.OK);
    }

    // TODO: remove
    @RequestMapping(path = "api/account/{username}/enable",
                    method = RequestMethod.POST)
    public ResponseEntity<?> postEnableAccount(@PathVariable(name = "username", required = true) String username) {
        Account account = this.accountRepository.findByUsername(username)
            .orElseThrow(NotFoundException::new);
        if (account.isEnabled()) {
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        account.enable();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // TODO: remove
    @RequestMapping(path = "api/account/{username}/disable",
                    method = RequestMethod.POST)
    public ResponseEntity<?> postDisableAccount(@PathVariable(name = "username", required = true) String username) {
        Account account = this.accountRepository.findByUsername(username)
            .orElseThrow(NotFoundException::new);
        if (!account.isEnabled()) {
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        account.disable();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
