package com.techpearl.bakingapp.ui.recipe;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.techpearl.bakingapp.data.network.model.Recipe;
import com.techpearl.bakingapp.data.network.model.Step;

/**
 * Created by Nour on 0017, 17/4/18.
 */

public class RecipeDetailsPresenter implements RecipeDetailsContract.Presenter {

    private final RecipeDetailsContract.View mDetailsView;

    @Nullable
    private Recipe mRecipe;

    public RecipeDetailsPresenter(@NonNull RecipeDetailsContract.View detailsView,
                                  @Nullable Recipe recipe){
        this.mDetailsView = detailsView;
        this.mDetailsView.setPresenter(this);
        this.mRecipe = recipe;
    }
    @Override
    public void start() {
        loadRecipe(mRecipe);
    }

    @Override
    public void loadRecipe(Recipe recipe) {
        mRecipe = recipe;
        mDetailsView.showRecipeDetails(mRecipe);
    }

    @Override
    public void openStepDetails(@NonNull Step step) {
        mDetailsView.showStepDetailsUi(step);
    }
}