package com.techpearl.bakingapp.ui.recipe;

import android.os.Bundle;
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
    public Bundle getState() {
        return null;
    }

    @Override
    public void restoreState(Bundle state) {

    }

    @Override
    public void loadRecipe(Recipe recipe) {
        if(recipe == null){
            mDetailsView.showErrorMessage();
            return;
        }
        mRecipe = recipe;
        mDetailsView.showRecipeDetails(mRecipe);
    }

    @Override
    public void openStepDetails(int stepIndex) {
        mDetailsView.showStepDetailsUi(mRecipe.getSteps(), stepIndex);
    }
}
