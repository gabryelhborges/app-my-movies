package com.example.mymovies;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConfig {
    private final Retrofit retrofit;

    public RetrofitConfig() {
        retrofit = new Retrofit.Builder().baseUrl("http://10.0.2.2:8080").// TODO: !!!ATENÇÃO!!! Altere para o IP da sua máquina EX: "http://000.000.00.00:8080"
                addConverterFactory(GsonConverterFactory.create()).build();
    }

    public MovieService getMovieService() {
        return this.retrofit.create(MovieService.class);
    }
}