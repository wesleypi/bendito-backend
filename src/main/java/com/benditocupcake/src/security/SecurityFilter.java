package com.benditocupcake.src.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private final JWTProvider jwtProvider;

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain) throws ServletException, IOException {
        SecurityContextHolder.getContext().setAuthentication(null);

        final var authHeader = request.getHeader("Authorization");

        if (request.getRequestURI().startsWith("/api/auth")) {
            filterChain.doFilter(request, response);
            return;
        }
        
        if (nonNull(authHeader)) {
            final var token = jwtProvider.validateToken(authHeader);

            if (isNull(token)) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return;
            }

            final var tokenSubject = token.getSubject();

            request.setAttribute("user_id", tokenSubject);

            final var roles = token.getClaim("roles").asList(Object.class);
            final var grants = roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.toString())).toList();
            final var auth = new UsernamePasswordAuthenticationToken(token.getSubject(), null, grants);

            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(request, response);
    }


}