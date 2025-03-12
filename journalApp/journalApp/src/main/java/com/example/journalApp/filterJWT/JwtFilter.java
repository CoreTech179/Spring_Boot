package com.example.journalApp.filterJWT;

import com.example.journalApp.utilJWT.JWTUtil;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsServiceObj;

    @Autowired
    private JWTUtil jwtUtilObj;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException, java.io.IOException {
        String authorizationHeader = request.getHeader("Authorization");
        String userName = null;
        String JWT = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            JWT = authorizationHeader.substring(7);
            userName = jwtUtilObj.extractUsername(JWT);
        }
        if (userName != null) {
            UserDetails userDetails = userDetailsServiceObj.loadUserByUsername(userName);
            if (jwtUtilObj.validateToken(JWT)) {
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        response.addHeader("Admin", "ram");
        chain.doFilter(request, response);
    }

}
