package com.tbagrel1.lunch.api.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tbagrel1.lunch.api.db.models.Account;
import com.tbagrel1.lunch.core.models.security.AccountCredential;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import io.jsonwebtoken.Jwts;

import static com.tbagrel1.lunch.api.security.SecurityConstants.EXPIRATION_TIME;
import static com.tbagrel1.lunch.api.security.SecurityConstants.HEADER_STRING;
import static com.tbagrel1.lunch.api.security.SecurityConstants.SIGNATURE_ALGORITHM;
import static com.tbagrel1.lunch.api.security.SecurityConstants.SIGNING_KEY;
import static com.tbagrel1.lunch.api.security.SecurityConstants.TOKEN_ISSUER;
import static com.tbagrel1.lunch.api.security.SecurityConstants.TOKEN_PREFIX;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl("/api/session/login");
    }

    @Override
    public Authentication attemptAuthentication(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws AuthenticationException {
        try {
            AccountCredential accountCredential = new ObjectMapper()
                .readValue(request.getInputStream(), AccountCredential.class);

            return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    accountCredential.getUsername(),
                    accountCredential.getPassword(),
                    new ArrayList<>()
                )
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain chain,
        Authentication authentication
    ) {
        long now = System.currentTimeMillis();
        Date issuedAt = new Date(now);
        Date expiresAt = new Date(now + EXPIRATION_TIME);

        String token = Jwts.builder()
            .setIssuer(TOKEN_ISSUER)
            .setSubject(((Account) authentication.getPrincipal()).getUsername())
            .setIssuedAt(issuedAt)
            .setExpiration(expiresAt)
            .signWith(SIGNING_KEY, SIGNATURE_ALGORITHM)
            .compact();
        response.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
    }
}
