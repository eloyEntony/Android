package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;
import com.bumptech.glide.Glide;
import com.example.myapplication.models.ImageModel;
import com.example.myapplication.network.ImageRequester;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText txtData;

    private ImageRequester imageRequester;
    private NetworkImageView myImage;

    ImageView myImg;
    //LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtData=findViewById(R.id.txtData);

        String url = "https://upload.wikimedia.org/wikipedia/commons/7/78/Image.jpg";


//        imageRequester= ImageRequester.getInstance();
//        myImg = findViewById(R.id.myimg);
//        imageRequester.setImageFromUrl(myImage, url);

        //LoadImages();

        //layout = findViewById(R.id.layout);

        myImg = findViewById(R.id.myimg);

        Glide.with(this)
                .load(url)
                .centerCrop()
                .into(myImg);


    }

    public void LoadImages(){

        MainActivity intasnce = this;
        NetworkService.getInstance()
                .getJSONApi()
                .getImages()
                .enqueue(new Callback<List<ImageModel>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<ImageModel>> call, @NonNull Response<List<ImageModel>> response) {
                        List<ImageModel> images = response.body();
//                        Toast.makeText(intasnce,post.get(0).getTemperatureC(), Toast.LENGTH_LONG).show();

                        for(ImageModel item : images){
                            NetworkImageView imageView = new NetworkImageView(MainActivity.this);

                            imageRequester= ImageRequester.getInstance();

                            imageRequester.setImageFromUrl(imageView, "http://10.0.2.2:5000/Images/"+ item.getName());

                            addvieW(imageView, 200, 200);
                        }

                    }

                    @Override
                    public void onFailure(@NonNull Call<List<ImageModel>> call, @NonNull Throwable t) {
                        t.printStackTrace();
                    }
                });


    }
    private void addvieW(NetworkImageView imageView, int width, int height) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);

        // setting the margin in linearlayout
        params.setMargins(0, 10, 0, 10);
        imageView.setLayoutParams(params);

        // adding the image in layout
        //layout.addView(imageView);

    }


    public void ClickBtnHello(View view) {
        //Toast.makeText(this,txtData.getText(),Toast.LENGTH_LONG).show();
        MainActivity intasnce = this;
        NetworkService.getInstance()
                .getJSONApi()
                .getWeather()
                .enqueue(new Callback<List<Post>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Post>> call, @NonNull Response<List<Post>> response) {
                        List<Post> post = response.body();
//                        Toast.makeText(intasnce,post.get(0).getTemperatureC(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<Post>> call, @NonNull Throwable t) {
                        t.printStackTrace();
                    }
                });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.headmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch(item.getItemId()) {
            case R.id.mhome:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.mregister:
                intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}