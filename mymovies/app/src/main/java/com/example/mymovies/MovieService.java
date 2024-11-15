package com.example.mymovies;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieService {

    @GET("api/find-movies")  // A parte base da URL já está no RetrofitConfig
    Call<List<Movie>> getMovie(@Query("filter") String filter);
}
