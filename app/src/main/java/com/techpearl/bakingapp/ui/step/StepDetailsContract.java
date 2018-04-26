package com.techpearl.bakingapp.ui.step;

import com.techpearl.bakingapp.base.BasePresenter;
import com.techpearl.bakingapp.base.BaseView;
import com.techpearl.bakingapp.data.network.model.Step;

/**
 * Created by Nour on 0017, 17/4/18.
 * An interface that defines the relationship between the
 * View and Presenter in the step details Fragment
 */

public interface StepDetailsContract {
    interface View extends BaseView<Presenter>{
        void showStepDetails(Step step);
        void showFullScreenDialog();
        void goToNormalScreen();
        boolean isActive();
        void disableBack();
        void disableNext();
        void enableNext();
        void enableBack();
    }

    interface Presenter extends BasePresenter{
        void loadCurrentStep();
        void openFullScreen();
        void closeFullScreen();
        void onToggleFullScreen();
        void onBackPressedInFullScreenDialog();
        void onNextStepClicked();
        void onPreviousStepClicked();
    }
}
