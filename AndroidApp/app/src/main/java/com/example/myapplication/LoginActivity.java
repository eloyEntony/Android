package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.application.HomeApplication;
import com.example.myapplication.models.AuthenticateResponse;
import com.example.myapplication.models.LoginDTOBadRequest;
import com.example.myapplication.models.LoginViewModel;
import com.example.myapplication.models.RegisterViewModel;
import com.example.myapplication.security.JwtSecurityService;
import com.example.myapplication.services.NetworkService;
import com.example.myapplication.services.SessionManager;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    LoginActivity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        activity = this;
    }

    public void onClickLogin(View view){

        TextInputEditText textEmail = findViewById(R.id.inputEmailLogin);
        String email = textEmail.getText().toString();

        TextInputEditText textPass = findViewById(R.id.inputPassLogin);
        String password = textPass.getText().toString();

        LoginViewModel user = new LoginViewModel(email, password);
        LoginUser(user);
    }

    public void showMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void LoginUser(LoginViewModel user){

        NetworkService.getInstance()
                .getJSONApi()
                .loginUser(user)
                .enqueue(new Callback<AuthenticateResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<AuthenticateResponse> call, @NonNull Response<AuthenticateResponse> response) {
                        if(response.isSuccessful()) {
                            String token = response.body().getToken();
                            showMessage(token);
                            JwtSecurityService jwtService = HomeApplication.getInstance();
                            jwtService.saveJwtToken(token);
                        }
                        else {
                            try {
                                String json = response.errorBody().string();
                                Gson gson = new Gson();
                                LoginDTOBadRequest result = gson.fromJson(json, LoginDTOBadRequest.class); // зробити супер мега вложений клас
                                //errorMessage.setText(result.getInvalid());

                                //json = result.errors..get(0);

                                    JSONObject jObjError = new JSONObject(response.errorBody().string());
                                    //Toast.makeText(this, jObjError.getJSONObject("error").getString("message"), Toast.LENGTH_LONG).show();
                                showMessage(jObjError.getJSONObject("error").getString("message"));
                            }
                            catch (Exception ex) {}
                        }
                    }
                    @Override
                    public void onFailure(@NonNull Call<AuthenticateResponse> call, @NonNull Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

}