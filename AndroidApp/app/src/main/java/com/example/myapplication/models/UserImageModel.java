package com.example.myapplication.models;

import lombok.Data;

@Data
public class UserImageModel {
    String Email;
    String Image;

    public UserImageModel(String email, String image) {
        this.Email = email;
        this.Image = image;
    }
}
