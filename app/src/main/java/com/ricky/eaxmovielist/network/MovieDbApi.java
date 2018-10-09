package com.ricky.eaxmovielist.network;

import android.content.res.Configuration;

import com.ricky.eaxmovielist.model.Movie;
import com.ricky.eaxmovielist.model.ResultList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class MovieDbApi {
    private final static String BASE_URL = "https://api.themoviedb.org/3/";
    private final static String API_KEY = "76280e3bcf77180cd25eca297c22a7f7";

    private interface MovieDbEndPointInterface {
        @GET("discover/movie")
        Call<ResultList<Movie>> discoverMovies(@Query("api_key") String api_key, @Query("page") int page);

        @GET("configuration")
        Call<Configuration> getConfiguration(@Query("api_key") String api_key);
    }

    private static Retrofit sRetrofitInstance = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private static MovieDbEndPointInterface sMovieDbService;

    private static MovieDbEndPointInterface getService() {
        if (sMovieDbService == null) {
            sMovieDbService = sRetrofitInstance.create(MovieDbEndPointInterface.class);
        }

        return sMovieDbService;
    }

    public static void discover(int page, Callback<ResultList<Movie>> callback) {
        Call<ResultList<Movie>> call = MovieDbApi.getService().discoverMovies(API_KEY, page);
        call.enqueue(callback);
    }

    public static void getConfiguration(Callback<Configuration> callback) {
        Call<Configuration> call = MovieDbApi.getService().getConfiguration(API_KEY);
        call.enqueue(callback);
    }
}
