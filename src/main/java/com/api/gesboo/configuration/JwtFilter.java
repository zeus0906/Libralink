package com.api.gesboo.configuration;


import com.api.gesboo.entite.User.Jwt;
import com.api.gesboo.service.UtilisateurService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Service
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    UtilisateurService utilisateurService;
    @Autowired
    JwtService jwtService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = null;
        Jwt tokenDansLaBDD = null;
        String username = null;
        boolean isTokenExpired = true;

       final String authorization = request.getHeader("Authorization");
       if(authorization != null && authorization.startsWith("bearer ")){
           token = authorization.substring(7);
           tokenDansLaBDD = this.jwtService.tokenByValue(token);
           isTokenExpired = jwtService.isTokenExpired(token);
           username = jwtService.extractUsername(token);
       }

       if( !isTokenExpired
               && tokenDansLaBDD.getUtilisateur().getEmail().equals(username)
               && SecurityContextHolder.getContext().getAuthentication() == null
       ){
           UserDetails userDetails = utilisateurService.loadUserByUsername(username);
           UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
           SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
       }

       filterChain.doFilter(request,response);

    }


}
