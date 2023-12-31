package com.notrew.tothink.core.security;

import com.notrew.tothink.modules.account.repositories.TokenRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final TokenRepository tokenRepository;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        if (_shouldBypassAuthentication(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt = _extractJwtFromRequest(request);
        if (_isValidJwt(jwt)) {
            final String userEmail = jwtService.extractUsername(jwt);
            if (_shouldAuthenticate(userEmail)) {
                final UserDetails userDetails = _getUserDetails(userEmail);
                if (_isTokenValid(jwt, userDetails) && _isTokenPersisted(jwt)) {
                    _setAuthenticationToken(request, userDetails);
                }
            }
        }

        filterChain.doFilter(request, response);
    }

    private boolean _shouldBypassAuthentication(HttpServletRequest request) {
        return request.getServletPath().contains("/api/v1/auth");
    }

    private String _extractJwtFromRequest(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

    private boolean _isValidJwt(String jwt) {
        return jwt != null;
    }

    private boolean _shouldAuthenticate(String userEmail) {
        return userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null;
    }

    private UserDetails _getUserDetails(String userEmail) {
        return userDetailsService.loadUserByUsername(userEmail);
    }

    private boolean _isTokenValid(String jwt, UserDetails userDetails) {
        return jwtService.isTokenValid(jwt, userDetails);
    }

    private boolean _isTokenPersisted(String jwt) {
        return tokenRepository.findByToken(jwt)
                .map(t -> !t.isExpired() && !t.isRevoked())
                .orElse(false);
    }

    private void _setAuthenticationToken(HttpServletRequest request, UserDetails userDetails) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }
}
