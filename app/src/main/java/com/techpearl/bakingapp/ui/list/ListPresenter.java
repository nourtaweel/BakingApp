package com.techpearl.bakingapp.ui.list;

import android.util.Log;

import com.techpearl.bakingapp.data.model.Recipe;
import com.techpearl.bakingapp.data.network.BakingApiClient;
import com.techpearl.bakingapp.data.network.ServiceGenerator;
import com.techpearl.bakingapp.ui.base.BaseMVPPresenter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Nour on 0013, 13/4/18.
 */

public class ListPresenter extends BaseMVPPresenter<RecipeListContract.ListView>
        implements RecipeListContract.ViewActions  {
    @Override
    public void onLoadRecipesRequest() {
        BakingApiClient bakingApiClient = ServiceGenerator.createService(BakingApiClient.class);
        Call<List<Recipe>> call =  bakingApiClient.recipes();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                Log.d("Recipes", "count:" + response.body().size());
                mView.showRecipes(response.body());
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.d("Recipes", "error fetching recipes " + t.getMessage());
            }
        });
    }
}
