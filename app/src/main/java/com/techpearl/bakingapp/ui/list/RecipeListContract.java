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

import com.techpearl.bakingapp.base.BasePresenter;
import com.techpearl.bakingapp.data.network.model.Recipe;
import com.techpearl.bakingapp.base.BaseView;

import java.util.List;

/*An interface to show the relationship between the view and presenter
* in the main recipe list Activity*/
public interface RecipeListContract {

    interface View extends BaseView<Presenter>{
        void showLoadingIndicator();
        void hideLoadingIndicator();
        void showRecipes(List<Recipe> recipes);
        void showLoadingErrorMessage();
        void showRecipeDetailsUi(Recipe recipe);
        boolean isActive();

    }
    interface Presenter extends BasePresenter{
        void loadRecipes();
        void openRecipeDetails(@NonNull Recipe recipeToShow);
    }


}
