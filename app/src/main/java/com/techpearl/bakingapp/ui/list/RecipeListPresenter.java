package com.techpearl.bakingapp.ui.list;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.util.Log;

import com.techpearl.bakingapp.data.DataManager;
import com.techpearl.bakingapp.data.network.RemoteCallback;
import com.techpearl.bakingapp.data.network.model.Recipe;
import com.techpearl.bakingapp.utils.ConnectionUtils;

import java.util.List;

import idlingResource.SimpleIdlingResource;

public class RecipeListPresenter implements RecipeListContract.Presenter {
    private static final String TAG = RecipeListPresenter.class.getSimpleName();

    private final DataManager dataManager;

    private Context mContext;

    private final RecipeListContract.View mRecipeListView;

    @Nullable private SimpleIdlingResource mIdlingResource;

    public RecipeListPresenter(@NonNull Context context,
                               @NonNull DataManager manager,
                               @NonNull RecipeListContract.View recipeListView){
        mContext = context;
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
        mRecipeListView.showLoadingIndicator();
        if(!ConnectionUtils.isConnected(mContext)){
            mRecipeListView.showLoadingErrorMessage(RecipeListContract.ERROR_CODE_NO_CONNECTION);
            return;
        }
        if(mIdlingResource != null){
            mIdlingResource.setIdleState(false);
        }
        dataManager.getRecipeList(new RemoteCallback<List<Recipe>>() {
            @Override
            public void onSuccess(List<Recipe> response) {
                if(mIdlingResource != null){
                    mIdlingResource.setIdleState(true);
                }
                if(!mRecipeListView.isActive())
                    return;
                mRecipeListView.showRecipes(response);
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.d(TAG, "error fetching recipes " + throwable.getMessage());
                if(!mRecipeListView.isActive())
                    return;
                mRecipeListView.showLoadingErrorMessage(RecipeListContract.ERROR_CODE_API_FAIL);
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
