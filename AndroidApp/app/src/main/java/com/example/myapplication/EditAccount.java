package com.example.myapplication;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.adapter.UserAdapter;
import com.example.myapplication.application.HomeApplication;
import com.example.myapplication.models.AuthenticateResponse;
import com.example.myapplication.models.User;
import com.example.myapplication.services.NetworkService;
import com.google.android.material.textfield.TextInputEditText;
import com.oginotihiro.cropview.CropView;

import java.io.ByteArrayOutputStream;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditAccount extends AppCompatActivity {

    TextView email;
    ImageView userImage;
    TextInputEditText textUserName;
    EditAccount activity;
    Boolean changeImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);

        activity = this;
        userImage = findViewById(R.id.userImage);
        changeImage = false;
        email = findViewById(R.id.userEmail);
        textUserName = findViewById(R.id.editUserNameField);
        onLoadData();
    }

    public void onLoadData(){
        NetworkService.getInstance()
                .getJSONApi()
                .getMyAccount()
                .enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                        User currentUser = response.body();
                        email.setText(currentUser.getEmail());
                        textUserName.setText(currentUser.getUserName());

                        String url = "http://10.0.2.2:5000/" + currentUser.getImage();
                        Glide.with(HomeApplication.getAppContext())
                                .load(url)
                                //.circleCrop()
                                .apply(new RequestOptions().override(300, 300))
                                .into(userImage);
                    }

                    @Override
                    public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    public void clickBtnSave(View view){
        User updatedUser = new User();
        updatedUser.setUserName(textUserName.getText().toString());
        if(changeImage)
            updatedUser.setImage(convertImage());
        updateUser(updatedUser);
    }

    private void updateUser(User upUser){
        NetworkService.getInstance()
                .getJSONApi()
                .updateUserAccount(upUser)
                .enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        Intent intent = new Intent(EditAccount.this, MainActivity.class);
                        finish();
                        startActivity(intent);
                    }
                    @Override
                    public void onFailure(Call<User> call, Throwable t) {

                    }
                });
    }

    public void clickBtnCancel(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void clickBtnPhoto(View view){
        changeImage = true;
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
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
                        Uri selectedImage = data.getData();
                        CropView cropView = (CropView) findViewById(R.id.userImage);
                        cropView.of(selectedImage)
                                .withOutputSize(100, 100)
                                .initialize(activity);
                    }
                }
            });


    private String convertImage(){
        try{
            CropView cropView = (CropView) findViewById(R.id.userImage);
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
}