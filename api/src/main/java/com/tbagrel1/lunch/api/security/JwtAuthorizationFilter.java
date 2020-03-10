package com.tbagrel1.lunch.api.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;

import static com.tbagrel1.lunch.api.security.SecurityConstants.HEADER_STRING;
import static com.tbagrel1.lunch.api.security.SecurityConstants.SIGNING_KEY;
import static com.tbagrel1.lunch.api.security.SecurityConstants.TOKEN_PREFIX;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private final JwtParser jwtParser = Jwts.parserBuilder()
        .setSigningKey(SIGNING_KEY)
        .build();

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain chain
    ) throws IOException, ServletException {
        UsernamePasswordAuthenticationToken authentication = this.getAuthentication(request);

        if (authentication == null) {
            chain.doFilter(request, response);
            return;
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String header = request.getHeader(HEADER_STRING);
        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            return null;
        }

        Claims claims = jwtParser.parseClaimsJws(header.replace(TOKEN_PREFIX, "")).getBody();
        String username = claims.getSubject();
        if (username == null) {
            return null;
        }

        return new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
    }
}
