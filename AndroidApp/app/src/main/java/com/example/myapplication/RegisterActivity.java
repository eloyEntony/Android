package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void onClickRegister(View view){

        TextInputLayout textInputLayout = findViewById(R.id.layoutEmail);
        TextInputEditText textEmail = findViewById(R.id.inputEmail);
        String text = textEmail.getText().toString();

        if(text.isEmpty())
            textInputLayout.setError("Field is empty");
        else
            textInputLayout.setError("");
    }
}