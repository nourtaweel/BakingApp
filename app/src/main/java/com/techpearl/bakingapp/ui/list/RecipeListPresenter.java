/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
        mRecipeListView.showRecipeDetails(recipeToShow.getId());
    }
}
