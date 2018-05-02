package com.techpearl.bakingapp.ui.step;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.techpearl.bakingapp.Constants;
import com.techpearl.bakingapp.R;
import com.techpearl.bakingapp.data.network.model.Recipe;
import com.techpearl.bakingapp.data.network.model.Step;
import com.techpearl.bakingapp.ui.recipe.RecipeDetailsPresenter;

import java.util.List;

import butterknife.ButterKnife;

/**
 * This Activity is displayed only if the screen width is less than 600dp
 * to show a step details (media + description)
 * Created by Nour on 0018, 18/4/18.
 */

public class StepDetailsActivity extends AppCompatActivity{
    //tag for logging
    private final static String TAG = StepDetailsActivity.class.getSimpleName();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_details);
        ButterKnife.bind(this);
        //find the static fragment in the layout
        StepDetailsFragment stepDetailsFragment = (StepDetailsFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_step);
        //Todo: remove the creation code as never accessed
        if(stepDetailsFragment == null){
            //create the Fragment
            stepDetailsFragment = StepDetailsFragment.newInstance(false);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_step, stepDetailsFragment)
                    .commit();
        }

        //if no saved state is found, create a new presenter
        if(savedInstanceState == null){
            List<Step> steps = null;
            int stepIndex = 0;
            //get the step from the starting intent
            if(getIntent().hasExtra(Constants.INTENT_EXTRA_STEPS)){
                steps = getIntent().getParcelableArrayListExtra(Constants.INTENT_EXTRA_STEPS);
            }
            if(getIntent().hasExtra(Constants.INTENT_EXTRA_STEP)){
                stepIndex = getIntent().getIntExtra(Constants.INTENT_EXTRA_STEP, 0);
            }
            //open video in fullscreen in landscape
            boolean isLandscape = getResources().getConfiguration().orientation
                    == Configuration.ORIENTATION_LANDSCAPE;
            new StepDetailsPresenter(stepDetailsFragment, steps, isLandscape, stepIndex);
        }

    }
}
