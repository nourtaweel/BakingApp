package com.techpearl.bakingapp.ui.list;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.techpearl.bakingapp.Constants;
import com.techpearl.bakingapp.R;
import com.techpearl.bakingapp.data.DataManager;
import com.techpearl.bakingapp.data.network.model.Recipe;
import com.techpearl.bakingapp.ui.recipe.RecipeDetailsActivity;

public class RecipeListActivity extends AppCompatActivity implements
        RecipeListFragment.RecipeClickListener{

    private static final String TAG = RecipeListActivity.class.getSimpleName();
    //MVP presenter refernce
    private RecipeListPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //get the fragment from FragmentManager
        RecipeListFragment recipeListFragment = (RecipeListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_recipe_list);
        if(recipeListFragment == null){
            //create the fragment
            recipeListFragment = RecipeListFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_recipe_list,recipeListFragment)
                    .commit();
        }
        //create the presenter
        mPresenter = new RecipeListPresenter(this,
                DataManager.getsInstance(),
                recipeListFragment);
    }

    @Override
    public void onRecipeClicked(Recipe recipe) {
        mPresenter.openRecipeDetails(recipe);
    }

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource(){
        return mPresenter.getIdlingResource();
    }
}
