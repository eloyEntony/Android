package com.example.myapplication;

import lombok.Data;

@Data
public class RegisterViewModel {

    public String Email;
    public String Username;
    public String Password;
    public String Photo;

    public RegisterViewModel(String email, String username, String password, String photo) {
        Email = email;
        Username = username;
        Password = password;
        Photo = photo;
    }
}
