package com.techpearl.bakingapp.data.network;

import com.techpearl.bakingapp.data.network.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Nour on 0011, 11/4/18.
 */

public interface BakingApiClient {

    @GET("baking.json")
    Call<List<Recipe>> recipes();
}
