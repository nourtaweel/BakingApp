package com.techpearl.bakingapp.ui.step;

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

import butterknife.ButterKnife;

/**
 * Created by Nour on 0018, 18/4/18.
 */

public class StepDetailsActivity extends AppCompatActivity {

    private final static String TAG = StepDetailsActivity.class.getSimpleName();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_details);
        ButterKnife.bind(this);
        StepDetailsFragment stepDetailsFragment = (StepDetailsFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_step);

        if(stepDetailsFragment == null){
            //create the Fragment
            stepDetailsFragment = StepDetailsFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_step, stepDetailsFragment)
                    .commit();
        }
        Step step = null;
        if(getIntent().hasExtra(Constants.INTENT_EXTRA_STEP)){
            //Log.d(TAG, getIntent().getExtras().getParcelable(Constants.INTENT_EXTRA_RECIPE).toString());
            step = getIntent().getParcelableExtra(Constants.INTENT_EXTRA_STEP);
        }
        //create the presenter
        new StepDetailsPresenter(stepDetailsFragment, step);
    }
}
