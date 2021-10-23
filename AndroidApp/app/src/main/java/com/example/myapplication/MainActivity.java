package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.toolbox.NetworkImageView;
import com.bumptech.glide.Glide;
import com.example.myapplication.adapter.UserAdapter;
import com.example.myapplication.models.ImageModel;
import com.example.myapplication.models.Post;
import com.example.myapplication.models.User;
import com.example.myapplication.network.ImageRequester;
import com.example.myapplication.services.NetworkService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText txtData;

    private ImageRequester imageRequester;
    private NetworkImageView myImage;

    ImageView myImg;
    List<User> users;
    //LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //txtData=findViewById(R.id.txtData);

        String url = "https://www.hootsuite.com/uploads/images/craft/components/Group-81.png";


//        imageRequester= ImageRequester.getInstance();
//        myImg = findViewById(R.id.myimg);
//        imageRequester.setImageFromUrl(myImage, url);

        //LoadImages();

        //layout = findViewById(R.id.layout);

        myImg = findViewById(R.id.myimg);

        Glide.with(this)
                .load(url)
                //.centerCrop()
                .into(myImg);

        LoadUsers();

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
        Intent intent = new Intent(this, EditAccount.class);
        startActivity(intent);

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
            case R.id.mlogin:
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void LoadUsers(){
        NetworkService.getInstance()
                .getJSONApi()
                .getUsers()
                .enqueue(new Callback<List<User>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<User>> call, @NonNull Response<List<User>> response) {
                        users = response.body();

                        RecyclerView recyclerView = findViewById(R.id.recycler);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this,1, LinearLayoutManager.VERTICAL, false));
                        UserAdapter adapter = new UserAdapter(MainActivity.this, users);
                        recyclerView.setAdapter(adapter);
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<User>> call, @NonNull Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

}