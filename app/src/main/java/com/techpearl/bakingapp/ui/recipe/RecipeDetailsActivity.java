package com.techpearl.bakingapp.ui.recipe;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.techpearl.bakingapp.Constants;
import com.techpearl.bakingapp.R;
import com.techpearl.bakingapp.data.network.model.Recipe;
import com.techpearl.bakingapp.data.network.model.Step;
import com.techpearl.bakingapp.ui.step.StepDetailsActivity;
import com.techpearl.bakingapp.ui.step.StepDetailsFragment;
import com.techpearl.bakingapp.ui.step.StepDetailsPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Nour on 0017, 17/4/18.
 * shows the recipe details (ingredient + steps)
 * if the width more than 600dp, then this will be a two pane Activity with recipeDetailsFragment as
 * master and mStepDetailsFragment in the details pane
 */

public class RecipeDetailsActivity extends AppCompatActivity
implements RecipeDetailsFragment.OnStepClickListener{
    //tag for Logging
    private static final String TAG = RecipeDetailsActivity.class.getSimpleName();
    //step details container if in two-pane mode
    //boolean to save the two-pane/one-pane mode status
    private boolean mTwoPane;
    @Nullable @BindView(R.id.step_detail_container)
    View mStepDetailsContainerView;
    //StepDetailsFragment reference, null if not in two-pane
    private StepDetailsFragment mStepDetailsFragment;
    //RecipeDetailFragmnet reference
    private  RecipeDetailsFragment mRecipeDetailsFragment;
    private RecipeDetailsPresenter mRecipeDetailsPresenter;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        ButterKnife.bind(this);
        //if can find details container view, then it is two-pane mode
        mTwoPane = (mStepDetailsContainerView != null);
        //find the static recipeDetailsFragment
        mRecipeDetailsFragment = (RecipeDetailsFragment)
                getSupportFragmentManager()
                .findFragmentById(R.id.fragment_recipe_details);
        //get the recipe to display from the starting intent and create a new presenter for the
        // master fragment
        if(getIntent().hasExtra(Constants.INTENT_EXTRA_RECIPE)){
            Recipe recipe = getIntent().getParcelableExtra(Constants.INTENT_EXTRA_RECIPE);
            mRecipeDetailsPresenter = new RecipeDetailsPresenter(mRecipeDetailsFragment, recipe);
        }else if(getIntent().hasExtra(Constants.INTENT_EXTRA_RECIPE_MARSHALED)){
            byte [] bytes = getIntent().getByteArrayExtra(Constants.INTENT_EXTRA_RECIPE_MARSHALED);
            Parcel parcel = Parcel.obtain();
            parcel.unmarshall(bytes, 0, bytes.length);
            parcel.setDataPosition(0);
            Recipe recipe = Recipe.CREATOR.createFromParcel(parcel);
            mRecipeDetailsPresenter = new RecipeDetailsPresenter(mRecipeDetailsFragment, recipe);
        }
        //Todo: remove next code
/*
        if(recipeDetailsFragment == null){
            //create the fragment
            recipeDetailsFragment = RecipeDetailsFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_recipe_details, recipeDetailsFragment)
                    .commit();
        }*/

        //if in two-pane mode, retrieve the previous details fragment if any
        if(mTwoPane && savedInstanceState != null){
            mStepDetailsFragment = (StepDetailsFragment) getSupportFragmentManager()
                    .getFragment(savedInstanceState, "stepDetailsSavedFragment");
            if(mStepDetailsFragment != null){
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.step_detail_container, mStepDetailsFragment)
                        .commit();
            }
           /* //re-create the presenter
            Bundle bundle = savedInstanceState.getBundle("presenter_state");
            if(bundle != null)
                new StepDetailsPresenter(mStepDetailsFragment,
                        (Step) bundle.getParcelable("step"),
                        bundle.getBoolean("is_full_screen"));*/
        }

    }

    /**
     * used to save the details fragment instance*/
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(mStepDetailsFragment != null){
            getSupportFragmentManager().putFragment(outState, "stepDetailsSavedFragment", mStepDetailsFragment);
        }

    }

    /*callback method when a step is clicked*/
    @Override
    public void onStepClicked(List<Step> steps, int stepIndex) {
        //if not in master/details, open a new activity to show the step details
        if(!mTwoPane){
            Intent intent = new Intent(this, StepDetailsActivity.class);
            intent.putParcelableArrayListExtra(Constants.INTENT_EXTRA_STEPS, new ArrayList<Parcelable>(steps));
            intent.putExtra(Constants.INTENT_EXTRA_STEP, stepIndex);
            startActivity(intent);
        }//if in master/details, create a new stepDetailsFragment and add it to the screen
        else{
            //create the Fragment
            mStepDetailsFragment = StepDetailsFragment.newInstance(mTwoPane);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.step_detail_container, mStepDetailsFragment)
                    .commit();
            //create the presenter for the fragment
            new StepDetailsPresenter(mStepDetailsFragment, steps, false, stepIndex);
        }

    }

}
