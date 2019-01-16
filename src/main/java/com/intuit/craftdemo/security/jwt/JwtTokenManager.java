package com.intuit.craftdemo.security.jwt;

import com.intuit.craftdemo.util.Constants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.ldap.userdetails.LdapUserDetailsImpl;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenManager {

    public String generateJwtToken(Authentication authentication) {

        LdapUserDetailsImpl ldapUserDetails = (LdapUserDetailsImpl) authentication.getPrincipal();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + Constants.EXPIRATION_TIME);

        return Jwts.builder()
                .setSubject(ldapUserDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, Constants.SECRET)
                .compact();
    }

    public String getUserNameFromContext() {
        final SecurityContext securityContext = SecurityContextHolder.getContext();
        final Authentication authentication = securityContext.getAuthentication();
        if (authentication != null)
            return authentication.getName();
        else
            return null;
    }

}
