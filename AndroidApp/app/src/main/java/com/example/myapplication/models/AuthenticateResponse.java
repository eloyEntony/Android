package com.example.myapplication.models;

import lombok.Data;

@Data
public class AuthenticateResponse {

    public int id;
    public String username;
    public String token;

}
