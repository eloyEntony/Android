package com.example.myapplication.security;

public interface JwtSecurityService {

    void saveJwtToken(String token);
    String getToken();
    void deleteToken();


}
