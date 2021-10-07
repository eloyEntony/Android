package com.example.myapplication;

import lombok.Data;

@Data
public class RegisterViewModel {

    public String Email;
    public String FirstName;
    public String SecondName;
    public String Phone;
    public String Password;
    public String ConfirmPassword;

    public RegisterViewModel() {
    }

    public RegisterViewModel(String email, String firstName, String secondName, String phone, String password, String confirmPassword) {
        Email = email;
        FirstName = firstName;
        SecondName = secondName;
        Phone = phone;
        Password = password;
        ConfirmPassword = confirmPassword;
    }

    public RegisterViewModel(String email, String password) {
        Email = email;
        Password = password;
    }
}
