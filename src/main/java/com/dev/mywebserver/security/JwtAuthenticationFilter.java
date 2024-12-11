package com.dev.mywebserver.security;

import com.dev.mywebserver.db.dao.RefreshTokenRepository;
import com.dev.mywebserver.db.dao.UserRepository;
import com.dev.mywebserver.db.dto.User;
import com.dev.mywebserver.jwt.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;




@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final HandlerExceptionResolver handlerExceptionResolver;

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request
            , @NonNull HttpServletResponse response
            , @NonNull FilterChain filterChain) throws ServletException, IOException {

        logger.debug("JWT FILTER INVOKED");
        final String authHeader = request.getHeader("Authorization");
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            // invalid header
            logger.debug("header error : " + authHeader);
            filterChain.doFilter(request, response);

        }
        try{
            final String jwt = authHeader.substring(7);
            logger.debug("extracting jwt");
            final String userId = jwtService.extractUsername(jwt);
            logger.debug("user Id : " + userId);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if(userId != null && authentication == null){
                UserDetails userDetails = userDetailsService.loadUserByUsername(userId);

                if(jwtService.isTokenValid(jwt, userDetails)){
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails
                            , null
                            , userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);

                }
                else{
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                }
            }
            filterChain.doFilter(request, response);
        }
        catch(Exception exception){
            logger.debug("jwt filter exception");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            handlerExceptionResolver.resolveException(request, response, null, exception);
        }

    }


}
