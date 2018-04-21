package com.techpearl.bakingapp.ui.list;

import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        new RecipeListPresenter(DataManager.getsInstance(), recipeListFragment);


    }

    @Override
    public void onRecipeClicked(Recipe recipe) {
        Intent intent = new Intent(this, RecipeDetailsActivity.class);
        intent.putExtra(Constants.INTENT_EXTRA_RECIPE, recipe);
        startActivity(intent);
    }
}
