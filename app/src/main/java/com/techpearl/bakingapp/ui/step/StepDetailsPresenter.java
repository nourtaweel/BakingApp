package com.techpearl.bakingapp.ui.step;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.techpearl.bakingapp.Constants;
import com.techpearl.bakingapp.data.network.model.Step;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nour on 0018, 18/4/18.
 * MVP presenter of step details screen
 */

public class StepDetailsPresenter implements StepDetailsContract.Presenter {

    private final StepDetailsContract.View mView;
    //whether in fullscreen mode or not
    private boolean mFullScreen;
    //list of all steps to a recipe
    @Nullable
    private List<Step> mSteps;
    //the position of the currently displayed step
    private int mCurrentStep;

    public StepDetailsPresenter(@NonNull StepDetailsContract.View view,
                                List<Step> steps,
                                boolean fullScreen,
                                int stepNum){
        mView = view;
        mSteps = steps;
        mFullScreen = fullScreen;
        mCurrentStep = stepNum;
        mView.setPresenter(this);
    }
    @Override
    public void start() {
        if(mSteps == null)
            return;
        loadCurrentStep();
    }

    //returns a bundle that holds the state of this Presenter
    @Override
    public Bundle getState() {
        Bundle bundle = new Bundle();
        if(mSteps != null){
            bundle.putParcelableArrayList(Constants.EXTRA_STATE_PRESENTER_STEPS,
                    new ArrayList<Parcelable>(mSteps));
        }
        bundle.putBoolean(Constants.EXTRA_STATE_PRESENTER_FULL_SCREEN, mFullScreen);
        bundle.putInt(Constants.EXTRA_STATE_PRESENTER_CURRENT_STEP, mCurrentStep);
        return bundle;
    }

    //restores this Presenter's state
    @Override
    public void restoreState(Bundle state) {
        if(state == null){
            return;
        }
        this.mSteps = state.getParcelableArrayList(Constants.EXTRA_STATE_PRESENTER_STEPS);
        this.mCurrentStep = state.getInt(Constants.EXTRA_STATE_PRESENTER_CURRENT_STEP);
        this.mFullScreen = state.getBoolean(Constants.EXTRA_STATE_PRESENTER_FULL_SCREEN);
    }

    @Override
    public void loadCurrentStep() {
        mView.showStepDetails(mSteps.get(mCurrentStep));
        //if first step, disable back button
        if(mCurrentStep == 0){
            mView.disableBack();
        }//if last step, disable next button
        else if(mCurrentStep == mSteps.size()-1){
            mView.disableNext();
        }//if full screen mode and a video is available display it in full screen
        if(mFullScreen && !mSteps.get(mCurrentStep).getVideoURL().isEmpty()){
            mView.showFullScreenDialog();
        }
    }

    @Override
    public void openFullScreen() {
        //make the MVP View to open full screen
        mView.showFullScreenDialog();
        //set presenter state
        mFullScreen = true;
    }

    @Override
    public void closeFullScreen() {
        //make the MVP View to exit full screen
        mView.goToNormalScreen();
        //unset fullscreen state
        mFullScreen = false;
    }

    //used to toggle between both modes
    @Override
    public void onToggleFullScreen() {
        if(mFullScreen){
            mView.goToNormalScreen();
        }else {
            mView.showFullScreenDialog();
        }
    }

    //pressing back in full-screen mode causes exit from it
    @Override
    public void onBackPressedInFullScreenDialog() {
        if(mFullScreen){
            closeFullScreen();
        }
    }

    //callback when next button is clicked
    @Override
    public void onNextStepClicked() {
        //move one step forward, unless the last step has been reached
        if(mCurrentStep < mSteps.size()){
            mCurrentStep++;
            mView.enableBack();
            loadCurrentStep();
        }
    }

    //callback when next button is clicked
    @Override
    public void onPreviousStepClicked() {
        //if not the first step, go back
        if(mCurrentStep > 0){
            mCurrentStep--;
            mView.enableNext();
            loadCurrentStep();
        }
    }
}
