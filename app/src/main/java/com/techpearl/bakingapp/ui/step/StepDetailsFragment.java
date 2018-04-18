package com.techpearl.bakingapp.ui.step;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.techpearl.bakingapp.R;
import com.techpearl.bakingapp.data.network.model.Step;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Nour on 0018, 18/4/18.
 * A Fragment that shows the step media and/or the instructions
 */

public class StepDetailsFragment extends Fragment implements StepDetailsContract.View{

    private StepDetailsContract.Presenter mPresenter;
    @BindView(R.id.textView_step_desc)
    TextView mStepDescriptionTextView;
    @BindView(R.id.imageView_back)
    ImageView mBackImageView;
    @BindView(R.id.imageView_next)
    ImageView mNextImageView;

    public StepDetailsFragment(){

    }

    public static StepDetailsFragment newInstance(){
        return new StepDetailsFragment();
    }
    @Override
    public void setPresenter(StepDetailsContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_step_layout,
                container,
                false);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void showStepDetails(Step step) {
        mStepDescriptionTextView.setText(step.getDescription());
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }
}
