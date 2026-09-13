package com.project.myfirstproject.filter;

import com.project.myfirstproject.jwtlearn.Jwtlearning;
import com.project.myfirstproject.service.ClientDetailsServiceImp;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.IOException;

@Component
public class Jwtfilter extends OncePerRequestFilter {

    @Autowired
    private Jwtlearning jwtlearning;

    @Autowired
    private ClientDetailsServiceImp userDetailsService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getServletPath();

        if (path.startsWith("/redis/") || path.equals("/error")) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader =
                request.getHeader("Authorization");

        String username = null;
        String token = null;

        if (authHeader != null &&
                authHeader.startsWith("Bearer ")) {

            token = authHeader.substring(7);

            Object blacklisted =
                    redisTemplate.opsForValue()
                            .get("blacklist:" + token);

            if (Boolean.TRUE.equals(blacklisted)) {
                response.setStatus(
                        HttpServletResponse.SC_UNAUTHORIZED
                );
                return;
            }

            try {
                username =
                        jwtlearning.extractUsername(token);
            } catch (Exception e) {
                System.out.println("Invalid JWT token");
            }
        }
        if (username != null &&
                SecurityContextHolder
                        .getContext()
                        .getAuthentication() == null) {


            UserDetails userDetails =
                    userDetailsService
                            .loadUserByUsername(username);


            if (jwtlearning.validateToken(token)) {

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );


                authentication.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request)
                );

                SecurityContextHolder
                        .getContext()
                        .setAuthentication(authentication);
            }
        }


        filterChain.doFilter(request, response);
    }


}
