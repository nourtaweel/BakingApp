package com.techpearl.bakingapp.ui.list;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.util.Log;

import com.techpearl.bakingapp.data.DataManager;
import com.techpearl.bakingapp.data.network.RemoteCallback;
import com.techpearl.bakingapp.data.network.model.Recipe;

import java.util.List;

import idlingResource.SimpleIdlingResource;

public class RecipeListPresenter implements RecipeListContract.Presenter {

    private final DataManager dataManager;

    private final RecipeListContract.View mRecipeListView;

    @Nullable private SimpleIdlingResource mIdlingResource;

    public RecipeListPresenter(@NonNull DataManager manager,
                               @NonNull RecipeListContract.View recipeListView){
        dataManager = manager;
        mRecipeListView = recipeListView;
        mRecipeListView.setPresenter(this);
    }

    @Override
    public void start() {
        loadRecipes();
    }

    @Override
    public Bundle getState() {
        return null;
    }

    @Override
    public void restoreState(Bundle state) {

    }

    @Override
    public void loadRecipes() {
        if(mIdlingResource != null){
            mIdlingResource.setIdleState(false);
        }
        mRecipeListView.showLoadingIndicator();
        dataManager.getRecipeList(new RemoteCallback<List<Recipe>>() {
            @Override
            public void onSuccess(List<Recipe> response) {
                Log.d("Recipes", "count:" + response.size());
                if(mIdlingResource != null){
                    mIdlingResource.setIdleState(true);
                }
                if(!mRecipeListView.isActive())
                    return;
                mRecipeListView.hideLoadingIndicator();
                mRecipeListView.showRecipes(response);
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.d("Recipes", "error fetching recipes " + throwable.getMessage());
                if(!mRecipeListView.isActive())
                    return;
                mRecipeListView.hideLoadingIndicator();
                mRecipeListView.showLoadingErrorMessage();
            }
        });
    }

    @Override
    public void openRecipeDetails(@NonNull Recipe recipeToShow) {
        mRecipeListView.showRecipeDetailsUi(recipeToShow);
    }

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource(){
        if(mIdlingResource == null){
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }
}
