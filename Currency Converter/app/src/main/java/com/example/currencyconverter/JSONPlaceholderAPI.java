package com.example.currencyconverter;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface JSONPlaceholderAPI {
    @GET("p24api/pubinfo?json&exchange&coursid=3")
    public Call<List<Currency>> getCurrencyList();

}
