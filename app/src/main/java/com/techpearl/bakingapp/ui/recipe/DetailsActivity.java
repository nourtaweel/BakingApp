package com.techpearl.bakingapp.ui.recipe;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.techpearl.bakingapp.Constants;
import com.techpearl.bakingapp.R;
import com.techpearl.bakingapp.data.DataManager;
import com.techpearl.bakingapp.data.network.model.Recipe;
import com.techpearl.bakingapp.ui.list.RecipeListFragment;
import com.techpearl.bakingapp.ui.list.RecipeListPresenter;

import butterknife.ButterKnife;

/**
 * Created by Nour on 0017, 17/4/18.
 */

public class DetailsActivity extends AppCompatActivity {
    private static final String TAG = DetailsActivity.class.getSimpleName();
    private DetailsContract.Presenter mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        ButterKnife.bind(this);
        DetailsFragment recipeDetailsFragment = (DetailsFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_recipe_details);
        Log.d(TAG, getIntent().getExtras().getParcelable(Constants.INTENT_EXTRA_RECIPE).toString());
        if(getIntent().hasExtra(Constants.INTENT_EXTRA_RECIPE)){
            Log.d(TAG, getIntent().getExtras().getParcelable(Constants.INTENT_EXTRA_RECIPE).toString());
            Recipe recipe = getIntent().getParcelableExtra(Constants.INTENT_EXTRA_RECIPE);
            mPresenter = new DetailsPresenter(recipeDetailsFragment, recipe);
        }

        if(recipeDetailsFragment == null){
            //create the fragment

            recipeDetailsFragment = DetailsFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_recipe_details,recipeDetailsFragment)
                    .commit();
        }

    }

}
