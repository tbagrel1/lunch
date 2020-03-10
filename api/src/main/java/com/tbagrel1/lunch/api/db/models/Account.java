package com.tbagrel1.lunch.api.db.models;

import com.tbagrel1.lunch.api.security.BCryptPasswordEncoderSingleton;
import com.tbagrel1.lunch.core.models.input.InputAccount;
import com.tbagrel1.lunch.core.models.output.OutputAccount;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Account implements UserDetails {
    @Id
    private String username;

    private String firstname;
    private String lastname;
    private String displayedName;
    private String passwordHash;

    private boolean enabled;

    public Account() {}

    public static Account fromInput(InputAccount inputAccount) {
        Account account = new Account();
        account.setUsername(inputAccount.getUsername());
        account.setFirstname(inputAccount.getFirstname());
        account.setLastname(inputAccount.getLastname());
        account.setDisplayedName(inputAccount.getDisplayedName());
        account.setPasswordHash(BCryptPasswordEncoderSingleton.getInstance().getInner().encode(inputAccount.getPassword()));
        account.disable();
        return account;
    }

    public OutputAccount toOutput() {
        return new OutputAccount(username, firstname, lastname, displayedName, enabled);
    }

    @Override
    public String toString() {
        return String.format(
                "Account[username='%s', firstname='%s', lastname='%s', displayedName='%s', passwordHash='%s', enabled=%s]",
                username, firstname, lastname, displayedName, passwordHash, enabled ? "true" : "false"
        );
    }

    @Override public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override public String getPassword() {
        return passwordHash;
    }

    public String getUsername() {
        return username;
    }

    @Override public boolean isAccountNonExpired() {
        return true;
    }

    @Override public boolean isAccountNonLocked() {
        return true;
    }

    @Override public boolean isCredentialsNonExpired() {
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getDisplayedName() {
        return displayedName;
    }

    public void setDisplayedName(String displayedName) {
        this.displayedName = displayedName;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void enable() {
        this.enabled = true;
    }

    public void disable() {
        this.enabled = false;
    }
}
