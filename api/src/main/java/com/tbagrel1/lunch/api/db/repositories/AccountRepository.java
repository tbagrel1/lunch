package com.tbagrel1.lunch.api.db.repositories;

import com.tbagrel1.lunch.api.db.models.Account;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, String> {
    Optional<Account> findByUsername(String username);
    default boolean isNewUsernameValid(String username) {
        return this.findByUsername(username).isPresent()
            // TODO: add regex here
            && true;
    }
}
