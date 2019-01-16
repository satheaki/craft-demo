package com.intuit.craftdemo.handler;

public class JWTAuthenticationResponse {
    String header;
    String token;
    String tokenType;


    public JWTAuthenticationResponse(String header, String token, String tokenType) {
        this.header = header;
        this.token = token;
        this.tokenType = tokenType;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}
