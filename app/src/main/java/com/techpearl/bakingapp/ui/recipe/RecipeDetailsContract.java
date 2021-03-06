package com.techpearl.bakingapp.ui.recipe;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.techpearl.bakingapp.base.BasePresenter;
import com.techpearl.bakingapp.base.BaseView;
import com.techpearl.bakingapp.data.network.model.Recipe;
import com.techpearl.bakingapp.data.network.model.Step;

import java.util.List;

/**
 * Created by Nour on 0017, 17/4/18.
 * An interface to show the relationship between the view and presenter
 * in the recipe details Fragment
 */

public interface RecipeDetailsContract {

    interface View extends BaseView<Presenter>{
        void showRecipeDetails(Recipe recipe);
        void showErrorMessage();
        void showStepDetailsUi(List<Step> steps, int stepIndex);
        boolean isActive();

    }

    interface Presenter extends BasePresenter{
        void loadRecipe(Recipe recipe);
        void openStepDetails(int stepIndex);
    }

}
