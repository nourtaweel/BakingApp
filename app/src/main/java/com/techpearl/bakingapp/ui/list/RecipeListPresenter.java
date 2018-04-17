package com.techpearl.bakingapp.ui.list;

import android.support.annotation.NonNull;
import android.util.Log;

import com.techpearl.bakingapp.data.DataManager;
import com.techpearl.bakingapp.data.network.RemoteCallback;
import com.techpearl.bakingapp.data.network.model.Recipe;

import java.util.List;

public class RecipeListPresenter implements RecipeListContract.Presenter {

    //TODO : replace with repository concept
    private final DataManager dataManager;

    private final RecipeListContract.View mRecipeListView;

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
    public void loadRecipes() {
        //TODO: here add idling resources logic

        mRecipeListView.showLoadingIndicator();
        dataManager.getRecipeList(new RemoteCallback<List<Recipe>>() {
            @Override
            public void onSuccess(List<Recipe> response) {
                Log.d("Recipes", "count:" + response.size());
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
}
