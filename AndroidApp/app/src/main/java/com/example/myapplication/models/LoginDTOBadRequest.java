package com.example.myapplication.models;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class LoginDTOBadRequest {

    String type;
    public List<String> errors;

    public List<String> Email;

    public LoginDTOBadRequest() {
        Email = new ArrayList<>();
    }

    public LoginDTOBadRequest(List<String> email) {
        Email = email;
    }
}
