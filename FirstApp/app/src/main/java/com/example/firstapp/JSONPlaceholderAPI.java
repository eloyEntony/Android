package com.example.firstapp;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface JSONPlaceholderAPI {
    @GET("/posts/{id}")
    public Call<Post> getPostWithID(@Path("id") int id);

}
