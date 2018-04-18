package com.techpearl.bakingapp.ui.recipe;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.techpearl.bakingapp.Constants;
import com.techpearl.bakingapp.R;
import com.techpearl.bakingapp.data.network.model.Recipe;

import butterknife.ButterKnife;

/**
 * Created by Nour on 0017, 17/4/18.
 */

public class RecipeDetailsActivity extends AppCompatActivity {
    private static final String TAG = RecipeDetailsActivity.class.getSimpleName();
    //TODO: consider removing the presenter here
    private RecipeDetailsContract.Presenter mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        ButterKnife.bind(this);
        RecipeDetailsFragment recipeRecipeDetailsFragment = (RecipeDetailsFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_recipe_details);
        if(getIntent().hasExtra(Constants.INTENT_EXTRA_RECIPE)){
            Log.d(TAG, getIntent().getExtras().getParcelable(Constants.INTENT_EXTRA_RECIPE).toString());
            Recipe recipe = getIntent().getParcelableExtra(Constants.INTENT_EXTRA_RECIPE);
            mPresenter = new RecipeDetailsPresenter(recipeRecipeDetailsFragment, recipe);
        }

        if(recipeRecipeDetailsFragment == null){
            //create the fragment

            recipeRecipeDetailsFragment = RecipeDetailsFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_recipe_details, recipeRecipeDetailsFragment)
                    .commit();
        }

    }

}
