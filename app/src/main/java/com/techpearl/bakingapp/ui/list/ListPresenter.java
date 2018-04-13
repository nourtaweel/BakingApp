package com.techpearl.bakingapp.ui.list;

import android.support.annotation.NonNull;
import android.util.Log;

import com.techpearl.bakingapp.data.DataManager;
import com.techpearl.bakingapp.data.network.RemoteCallback;
import com.techpearl.bakingapp.data.network.model.Recipe;
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

    private final DataManager dataManager;

    public ListPresenter(@NonNull DataManager manager){
        dataManager = manager;
    }
    @Override
    public void onLoadRecipesRequest() {
        if(!isViewAttached()){
            return;
        }
        mView.showLoading();
        dataManager.getRecipeList(new RemoteCallback<List<Recipe>>() {
            @Override
            public void onSuccess(List<Recipe> response) {
                Log.d("Recipes", "count:" + response.size());
                mView.showRecipes(response);
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.d("Recipes", "error fetching recipes " + throwable.getMessage());
            }
        });
    }
}
