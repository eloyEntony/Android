package com.example.myapplication.models;

import lombok.Data;

@Data
public class LoginViewModel {

    public String email;
    public String password;

    public LoginViewModel(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
