package com.techpearl.bakingapp.ui.recipe;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.techpearl.bakingapp.Constants;
import com.techpearl.bakingapp.R;
import com.techpearl.bakingapp.data.network.model.Recipe;
import com.techpearl.bakingapp.data.network.model.Step;
import com.techpearl.bakingapp.ui.step.StepDetailsActivity;
import com.techpearl.bakingapp.ui.step.StepDetailsFragment;
import com.techpearl.bakingapp.ui.step.StepDetailsPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Nour on 0017, 17/4/18.
 */

public class RecipeDetailsActivity extends AppCompatActivity
implements RecipeDetailsFragment.OnStepClickListener{
    private static final String TAG = RecipeDetailsActivity.class.getSimpleName();
    @Nullable @BindView(R.id.step_detail_container)
    View mStepDetailsFragment;
    //TODO: consider removing the presenter here
    private RecipeDetailsContract.Presenter mPresenter;
    private boolean mTwoPane;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        ButterKnife.bind(this);
        mTwoPane = (mStepDetailsFragment != null);
        RecipeDetailsFragment recipeRecipeDetailsFragment = (RecipeDetailsFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_recipe_details);
        if(getIntent().hasExtra(Constants.INTENT_EXTRA_RECIPE)){
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

    @Override
    public void onStepClicked(Step step) {
        if(!mTwoPane){
            Intent intent = new Intent(this, StepDetailsActivity.class);
            intent.putExtra(Constants.INTENT_EXTRA_STEP, step);
            startActivity(intent);
        }else{
                //create the Fragment
                StepDetailsFragment stepDetailsFragment = StepDetailsFragment.newInstance();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.step_detail_container, stepDetailsFragment)
                        .commit();
            new StepDetailsPresenter(stepDetailsFragment, step);
        }

    }
}
