package com.tbagrel1.lunch.api.db.repositories;

import com.tbagrel1.lunch.api.db.models.Account;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
    Account findByUsername(String username);
}
