package com.tbagrel1.lunch.api.db.repositories;

import com.tbagrel1.lunch.api.db.models.Account;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AccountRepository extends CrudRepository<com.tbagrel1.lunch.api.db.models.Account, Long> {
    Account findById(long id);
    List<Account> findByFirstname(String firstname);
    List<Account> findByLastname(String lastname);
}
