package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.text.CollationElementIterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void onClickRegister(View view){

//        TextInputLayout textInputLayout = findViewById(R.id.layoutEmail);
//        TextInputEditText textEmail = findViewById(R.id.inputEmail);
//        String text = textEmail.getText().toString();
//
//        if(text.isEmpty())
//            textInputLayout.setError("Field is empty");
//        else
//            textInputLayout.setError("");

        RegisterUser();
    }

    public void RegisterUser(){

        RegisterViewModel newUser = new RegisterViewModel("string", "string","string","string","string","string");

        RegisterActivity intasnce = this;

        NetworkService.getInstance()
                .getJSONApi()
                .registerUser(newUser)
                .enqueue(new Callback<RegisterViewModel>() {
                    @Override
                    public void onResponse(@NonNull Call<RegisterViewModel> call, @NonNull Response<RegisterViewModel> response) {
                        //List<Post> post = response.body();
//                        Toast.makeText(intasnce,post.get(0).getTemperatureC(), Toast.LENGTH_LONG).show();
                        //Object obj = response.body();
                        //Toast.makeText(this, obj.toString(), Toast.LENGTH_LONG).show();

                        if(response.isSuccessful()) {
//                            TokenDTO tokenDTO = response.body();
//                            ((JwtServiceHolder) getActivity()).SaveJWTToken(tokenDTO.getToken()); // Navigate to the register Fragment
//                            ((NavigationHost) getActivity()).navigateTo(new ProductGridFragment(), false); // Navigate to the products Fragment
                            //Log.e(TAG,"*************GOOD Request***********"+ tokenDTO.getToken());
                        }
                        else {
                            try {
                                String json = response.errorBody().string();
                                Gson gson = new Gson();
                                LoginDTOBadRequest result = gson.fromJson(json, LoginDTOBadRequest.class); // зробити супер мега вложений клас
                                //errorMessage.setText(result.getInvalid());

                                json = result.Email.get(0);
                            }
                            catch (Exception ex) {
                                //errorMessage.setText(ex.getMessage());
                            }

                            //Log.e(TAG,"_______________________"+response.errorBody().charStream());
                        }

                    }

                    @Override
                    public void onFailure(@NonNull Call<RegisterViewModel> call, @NonNull Throwable t) {
                        t.printStackTrace();
                    }
                });
    }
}