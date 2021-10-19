package com.example.myapplication;

import com.example.myapplication.models.AuthenticateResponse;
import com.example.myapplication.models.ImageModel;
import com.example.myapplication.models.UserImageModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface JSONPlaceHolderApi {
    @GET("/WeatherForecast")
    public Call<List<Post>> getWeather();

    @POST("/api/Account/register")
    public Call<AuthenticateResponse> registerUser(@Body RegisterViewModel data);

    @GET("/Image/all")
    public Call<List<ImageModel>> getImages();

    @POST("/Image/upload")
    public Call<UserImageModel> uploadImg(@Body UserImageModel userImageModel);
}
