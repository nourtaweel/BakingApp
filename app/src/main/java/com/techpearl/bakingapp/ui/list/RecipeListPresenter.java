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
    //singleton data source
    private final DataManager dataManager;

    private Context mContext;
    //MVP view
    private final RecipeListContract.View mRecipeListView;
    //Idling resource for testing purposes
    @Nullable private SimpleIdlingResource mIdlingResource;

    public RecipeListPresenter(@NonNull Context context,
                               @NonNull DataManager manager,
                               @NonNull RecipeListContract.View recipeListView){
        mContext = context;
        dataManager = manager;
        mRecipeListView = recipeListView;
        mRecipeListView.setPresenter(this);
    }

    //start loading recipes
    @Override
    public void start() {
        loadRecipes();
    }

    //this presenter is state less
    @Override
    public Bundle getState() {
        return null;
    }

    @Override
    public void restoreState(Bundle state) {
        //do nothing, stateless presenter
    }

    //start loading data from network
    @Override
    public void loadRecipes() {
        mRecipeListView.showLoadingIndicator();
        //check connection, if not present show an error message
        if(!ConnectionUtils.isConnected(mContext)){
            mRecipeListView.showLoadingErrorMessage(RecipeListContract.ERROR_CODE_NO_CONNECTION);
            return;
        }
        //prepare idling resource not idle just before network interaction
        if(mIdlingResource != null){
            mIdlingResource.setIdleState(false);
        }
        //init loading
        dataManager.getRecipeList(new RemoteCallback<List<Recipe>>() {
            @Override
            public void onSuccess(List<Recipe> response) {
                if(mIdlingResource != null){
                    //now we are idle
                    mIdlingResource.setIdleState(true);
                }
                if(!mRecipeListView.isActive())
                    return;
                //display recipes on the View
                mRecipeListView.showRecipes(response);
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.d(TAG, "error fetching recipes " + throwable.getMessage());
                if(!mRecipeListView.isActive())
                    return;
                //display error message
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
