package com.techpearl.bakingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BakingApiClient bakingApiClient = ServiceGenerator.createService(BakingApiClient.class);
        Call<List<Recipe>> call =  bakingApiClient.recipes();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                Log.d("Recipes", "count:" + response.body().size());
                for(Recipe recipe : response.body()){
                    Log.d("Recipes", recipe.getName());
                }

            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.d("Recipes", "error fetching recipes " + t.getMessage());
            }
        });

    }
}
