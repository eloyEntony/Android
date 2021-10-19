package com.example.myapplication;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.models.AuthenticateResponse;
import com.example.myapplication.models.UserImageModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.oginotihiro.cropview.CropView;

import java.io.ByteArrayOutputStream;
import java.text.CollationElementIterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private static int RES = 1;

    RegisterActivity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        activity = this;
    }

    public void onClickRegister(View view){

        TextInputLayout textInputLayout = findViewById(R.id.layoutEmail);
        TextInputEditText textEmail = findViewById(R.id.inputEmail);
        String email = textEmail.getText().toString();


        TextInputEditText textPass = findViewById(R.id.inputPassword);
        String password = textPass.getText().toString();

//        if(email.isEmpty())
//            textInputLayout.setError("Field is empty");
//        else
//            textInputLayout.setError("");


       RegisterViewModel newUser = new RegisterViewModel(email, email, password, convertImage());
       RegisterUser(newUser);
    }

    private String convertImage(){
        try{
            CropView cropView = (CropView) findViewById(R.id.cropView);
            Bitmap croppedBitmap = cropView.getOutput();
            Matrix matrix = new Matrix();

            matrix.postRotate(cropView.getRotation());
            Bitmap rotatedBitmap = Bitmap.createBitmap(croppedBitmap, 0, 0, croppedBitmap.getWidth(), croppedBitmap.getHeight(), matrix, true);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            rotatedBitmap.compress(Bitmap.CompressFormat.PNG, 50, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
            return encoded;
        }
        catch (Exception ex){
            return "No image";
        }
    }

    public void onClickSelectImage(View view) {
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        //Intent intent = new Intent(this, SomeActivity.class);
        someActivityResultLauncher.launch(i);
    }

    // You can do the assignment inside onAttach or onCreate, i.e, before the activity is displayed
    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();

                        //CommonUtils.showLoading(this);
                        Uri selectedImage = data.getData();
                        CropView cropView = (CropView) findViewById(R.id.cropView);
                        cropView.of(selectedImage)
                                //.withAspect(x, y)
                                .withOutputSize(100, 100)
                                .initialize(activity);
                        //CommonUtils.hideLoading();
                    }

                }
            });


    public void onClickLeft(View view){
        CropView cropView = (CropView) findViewById(R.id.cropView);
        cropView.setRotation(cropView.getRotation()-90);
    }
    public void onClickRight(View view){
        CropView cropView = (CropView) findViewById(R.id.cropView);
        cropView.setRotation(cropView.getRotation()+90);
    }

    public void onClickSendFoto(View view){

        CropView cropView = (CropView) findViewById(R.id.cropView);
        Bitmap croppedBitmap = cropView.getOutput();
        Matrix matrix = new Matrix();

        matrix.postRotate(cropView.getRotation());
        Bitmap rotatedBitmap = Bitmap.createBitmap(croppedBitmap, 0, 0, croppedBitmap.getWidth(), croppedBitmap.getHeight(), matrix, true);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        rotatedBitmap.compress(Bitmap.CompressFormat.PNG, 50, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);


        UserImageModel userImageModel = new UserImageModel("...", encoded);

        NetworkService.getInstance()
                .getJSONApi()
                .uploadImg(userImageModel)
                .enqueue(new Callback<UserImageModel>() {
                    @Override
                    public void onResponse(@NonNull Call<UserImageModel> call, @NonNull Response<UserImageModel> response) {
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
                    public void onFailure(@NonNull Call<UserImageModel> call, @NonNull Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    public void showMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void RegisterUser(RegisterViewModel newUser){

        //RegisterViewModel newUser = new RegisterViewModel("string", "string","string","string");

        RegisterActivity intasnce = this;

        NetworkService.getInstance()
                .getJSONApi()
                .registerUser(newUser)
                .enqueue(new Callback<AuthenticateResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<AuthenticateResponse> call, @NonNull Response<AuthenticateResponse> response) {
                        //List<Post> post = response.body();
//                        Toast.makeText(intasnce,post.get(0).getTemperatureC(), Toast.LENGTH_LONG).show();
                        //Object obj = response.body();
                        //Toast.makeText(this, obj.toString(), Toast.LENGTH_LONG).show();
                        String token = response.body().getToken();



                        if(response.isSuccessful()) {
//                            TokenDTO tokenDTO = response.body();
//                            ((JwtServiceHolder) getActivity()).SaveJWTToken(tokenDTO.getToken()); // Navigate to the register Fragment
//                            ((NavigationHost) getActivity()).navigateTo(new ProductGridFragment(), false); // Navigate to the products Fragment
//                            Log.e(TAG,"*************GOOD Request***********"+ tokenDTO.getToken());
                            //token = response.body().toString();
                            showMessage(token);
                            //Toast.makeText(this, token, Toast.LENGTH_LONG).show();
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
                    public void onFailure(@NonNull Call<AuthenticateResponse> call, @NonNull Throwable t) {
                        t.printStackTrace();
                    }
                });
    }
}