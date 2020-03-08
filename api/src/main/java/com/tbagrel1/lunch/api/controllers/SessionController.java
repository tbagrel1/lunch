package com.tbagrel1.lunch.api.controllers;

import com.tbagrel1.lunch.core.models.output.OutputSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SessionController extends BaseController {
    public static final String ANONYMOUS_USER = "anonymous";

    @RequestMapping(value = "/api/session",
                    method = RequestMethod.GET,
                    produces= MEDIA_TYPE)
    public String getSession() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        return ok(authenticationToSession(authentication));
    }

    public static OutputSession authenticationToSession(Authentication authentication) {
        if (authentication == null) {
            return new OutputSession(
                    ANONYMOUS_USER,
                    false
            );
        }

        return new OutputSession(
                authentication.getName(),
                authentication.isAuthenticated()
        );
    }
}
