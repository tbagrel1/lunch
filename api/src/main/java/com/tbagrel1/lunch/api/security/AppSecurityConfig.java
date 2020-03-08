package com.tbagrel1.lunch.api.security;

import com.tbagrel1.lunch.api.controllers.BaseController;
import com.tbagrel1.lunch.api.controllers.SessionController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.*;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {
    // https://stackoverflow.com/a/48831592
    // https://github.com/fuhaiwei/springboot_security_restful_api/blob/master/src/main/java/demo/config/SpringSecurityConfig.java

    @Autowired
    private AppUserDetailsService userDetailsService;

    @Autowired
    private AppLoginHandler loginHandler;

    @Autowired
    private AppLogoutHandler logoutHandler;

    @Autowired
    private AppAccessDeniedHandler accessDeniedHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/api/**").permitAll();
        http.formLogin();

        http.logout()
                .logoutUrl("/api/session/logout")
                .addLogoutHandler(logoutHandler)
                .logoutSuccessHandler(logoutHandler);

        http.exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
                .authenticationEntryPoint(accessDeniedHandler);

        http.csrf()
                .ignoringAntMatchers("/api/session/**");

        http.addFilterBefore(new AcceptHeaderLocaleFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterAt(authenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterAfter(new CsrfTokenResponseHeaderBindingFilter(), CsrfFilter.class);

    }

    @Bean
    public AuthenticationProvider getProvider() {
        AppAuthenticationProvider provider = new AppAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    private AppAuthenticationFilter authenticationFilter() throws Exception {
        AppAuthenticationFilter filter = new AppAuthenticationFilter();
        filter.setAuthenticationSuccessHandler(loginHandler);
        filter.setAuthenticationFailureHandler(loginHandler);
        filter.setAuthenticationManager(authenticationManager());
        filter.setFilterProcessesUrl("/api/session/login");
        return filter;
    }

    private static void responseText(HttpServletResponse response, String content) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
        response.setContentLength(bytes.length);
        response.getOutputStream().write(bytes);
        response.flushBuffer();
    }

    @Component
    public static class AppAccessDeniedHandler implements AuthenticationEntryPoint, AccessDeniedHandler {
        // NoLogged Access Denied
        @Override
        public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
            responseText(response, authException.getMessage());
        }

        // Logged Access Denied
        @Override
        public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
            responseText(response, accessDeniedException.getMessage());
        }
    }

    @Component
    public static class AppLoginHandler extends BaseController implements AuthenticationSuccessHandler, AuthenticationFailureHandler {
        // Login Success
        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
            responseText(response, ok(SessionController.authenticationToSession(authentication)));
        }

        // Login Failure
        @Override
        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
            responseText(response, err(exception.getMessage()));
        }
    }

    @Component
    public static class AppLogoutHandler extends BaseController implements LogoutHandler, LogoutSuccessHandler {
        // Before Logout
        @Override
        public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        }

        // After Logout
        @Override
        public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
            responseText(response, ok(SessionController.authenticationToSession(null)));
        }
    }

    private static class AcceptHeaderLocaleFilter implements Filter {
        private AcceptHeaderLocaleResolver localeResolver;

        private AcceptHeaderLocaleFilter() {
            localeResolver = new AcceptHeaderLocaleResolver();
            localeResolver.setDefaultLocale(Locale.US);
        }

        @Override
        public void init(FilterConfig filterConfig) {
        }

        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
            Locale locale = localeResolver.resolveLocale((HttpServletRequest) request);
            LocaleContextHolder.setLocale(locale);

            chain.doFilter(request, response);
        }

        @Override
        public void destroy() {
        }
    }
}
