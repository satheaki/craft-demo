package com.intuit.craftdemo.handler;

import org.springframework.http.HttpStatus;

public class ApiResponse {
    boolean success;
    String message;
    HttpStatus httpStattusCode;


    public ApiResponse(boolean success, String message, HttpStatus httpStattusCode) {
        this.success = success;
        this.message = message;
        this.httpStattusCode = httpStattusCode;
    }

    public HttpStatus getHttpStattusCode() {
        return httpStattusCode;
    }

    public void setHttpStattusCode(HttpStatus httpStattusCode) {
        this.httpStattusCode = httpStattusCode;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
