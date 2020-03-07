package com.tbagrel1.lunch.api.db.repositories;

import com.tbagrel1.lunch.api.db.models.Account;

import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<com.tbagrel1.lunch.api.db.models.Account, Long> {
    Account findById(long id);
    Iterable<Account> findByFirstname(String firstname);
    Iterable<Account> findByLastname(String lastname);
}
