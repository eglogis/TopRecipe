package com.example.toprecipe;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class InstanciaRetrofit {

    private static Retrofit retrofit;
    private static final String BASE_URL = "http://192.168.1.140:80/topRecipes/";
    //private static final String BASE_URL = "http://192.168.1.32:80/topRecipes/";

     /*static Gson gson = new GsonBuilder()
            .setLenient()
            .create();*/


    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public interface GetDataService {

        @GET("obtenerUsuarios.php/")
        Call<List<usuario>> getAllPhotos();
    }

    /*public interface APIService {

        @POST("/posts")
        Call<Post> savePost(@Field("title") String title,
                            @Field("body") String body,
                            @Field("userId") long userId);
    }*/
}
