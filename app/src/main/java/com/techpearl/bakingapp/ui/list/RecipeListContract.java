package com.techpearl.bakingapp.ui.list;

import com.techpearl.bakingapp.data.model.Recipe;
import com.techpearl.bakingapp.ui.base.BaseView;

import java.util.List;

/**
 * Created by Nour on 0012, 12/4/18.
 */

public interface RecipeListContract {
    interface ViewActions {
        void onLoadRecipesRequest();
    }

    interface ListView extends BaseView{
        void showRecipes(List<Recipe> recipes);

    }
}
