package com.techpearl.bakingapp.ui.step;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.techpearl.bakingapp.data.network.model.Step;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nour on 0018, 18/4/18.
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

    @Override
    public Bundle getState() {
        Bundle bundle = new Bundle();
        if(mSteps != null)
            bundle.putParcelableArrayList("steps", new ArrayList<Parcelable>(mSteps));
        bundle.putBoolean("is_full_screen", mFullScreen);
        bundle.putInt("current_step", mCurrentStep);
        return bundle;
    }

    @Override
    public void restoreState(Bundle state) {
        if(state == null){
            return;
        }
        this.mSteps = state.getParcelableArrayList("steps");
        this.mCurrentStep = state.getInt("current_step");
        this.mFullScreen = state.getBoolean("is_full_screen");
    }

    @Override
    public void loadCurrentStep() {
        mView.showStepDetails(mSteps.get(mCurrentStep));
        //if reached first step, disable back button
        if(mCurrentStep == 0){
            mView.disableBack();
        }else if(mCurrentStep == mSteps.size()-1){
            mView.disableNext();
        }
        if(mFullScreen && !mSteps.
                get(mCurrentStep).getVideoURL().isEmpty()){
            mView.showFullScreenDialog();
        }
    }

    @Override
    public void openFullScreen() {
        mView.showFullScreenDialog();
        mFullScreen = true;
    }

    @Override
    public void closeFullScreen() {
        mView.goToNormalScreen();
        mFullScreen = false;
    }

    @Override
    public void onToggleFullScreen() {
        if(mFullScreen){
            mView.goToNormalScreen();
        }else {
            mView.showFullScreenDialog();
        }
    }

    @Override
    public void onBackPressedInFullScreenDialog() {
        if(mFullScreen){
            closeFullScreen();
        }
    }

    @Override
    public void onNextStepClicked() {
        if(mCurrentStep < mSteps.size()){
            mCurrentStep++;
            mView.enableBack();
            loadCurrentStep();
        }
    }

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
