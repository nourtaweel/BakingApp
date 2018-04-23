package com.techpearl.bakingapp.ui.step;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.techpearl.bakingapp.data.network.model.Step;

/**
 * Created by Nour on 0018, 18/4/18.
 */

public class StepDetailsPresenter implements StepDetailsContract.Presenter {

    private final StepDetailsContract.View mView;
    private boolean mFullScreen;

    @Nullable
    private Step mStep;

    public StepDetailsPresenter(@NonNull StepDetailsContract.View view,
                                Step step,
                                boolean fullScreen){
        mView = view;
        mStep = step;
        mFullScreen = fullScreen;
        mView.setPresenter(this);
    }
    @Override
    public void start() {
        loadStep(mStep);
    }

    @Override
    public Bundle getState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("step", mStep);
        bundle.putBoolean("is_full_screen", mFullScreen);
        return bundle;
    }

    @Override
    public void restoreState(Bundle state) {
        if(state == null){
            return;
        }
        this.mStep = state.getParcelable("step");
        this.mFullScreen = state.getBoolean("is_full_screen");
    }

    @Override
    public void loadStep(Step step) {
        mStep = step;
        mView.showStepDetails(mStep);
        if(mFullScreen){
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
}
