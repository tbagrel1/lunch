package com.tbagrel1.lunch.api.security;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

class AppAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        UsernamePasswordAuthenticationToken authRequest;
        try (InputStream inputStream = request.getInputStream()) {
            JsonNode context = OBJECT_MAPPER.readTree(inputStream);
            String username = context.get("username").asText();
            String password = context.get("password").asText();
            authRequest = new UsernamePasswordAuthenticationToken(username, password);
        } catch (IOException e) {
            // TODO: maybe remove in production
            e.printStackTrace();
            authRequest = new UsernamePasswordAuthenticationToken("", "");
        }
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

}

