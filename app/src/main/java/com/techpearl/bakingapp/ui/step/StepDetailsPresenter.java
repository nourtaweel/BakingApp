package com.techpearl.bakingapp.ui.step;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.techpearl.bakingapp.data.network.model.Step;

/**
 * Created by Nour on 0018, 18/4/18.
 */

public class StepDetailsPresenter implements StepDetailsContract.Presenter {

    private final StepDetailsContract.View mView;

    @Nullable
    private Step mStep;

    public StepDetailsPresenter(@NonNull StepDetailsContract.View view,
                                Step step){
        mView = view;
        mStep = step;
        mView.setPresenter(this);
    }
    @Override
    public void start() {
        loadStep(mStep);
    }

    @Override
    public void loadStep(Step step) {
        mStep = step;
        mView.showStepDetails(mStep);
    }
}
