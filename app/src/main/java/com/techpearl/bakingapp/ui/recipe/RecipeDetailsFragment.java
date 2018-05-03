package com.techpearl.bakingapp.ui.recipe;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.techpearl.bakingapp.Constants;
import com.techpearl.bakingapp.R;
import com.techpearl.bakingapp.data.network.model.Ingredient;
import com.techpearl.bakingapp.data.network.model.Recipe;
import com.techpearl.bakingapp.data.network.model.Step;
import com.techpearl.bakingapp.ui.step.StepDetailsActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Nour on 0017, 17/4/18.
 * a Fragment the shows the ingredients and a List of available {@link Step}s
 * of a {@link Recipe}
 */

public class RecipeDetailsFragment extends Fragment implements
        RecipeDetailsContract.View,
        RecipeStepsAdapter.StepClickListener{
    //steps adapter
    private RecipeStepsAdapter mStepsAdapter;
    //views
    @BindView(R.id.textView_error_message) TextView mErrorTextView;
    @BindView(R.id.textView_name) TextView mNameTextView;
    @BindView(R.id.textView_servings) TextView mServingsNumTextView;
    @BindView(R.id.imageView_recipe_image) ImageView mImageView;
    @BindView(R.id.textView_ingredients) TextView mIngredientsTextView;
    @BindView(R.id.recyclerView_steps) RecyclerView mStepsRecyclerView;
    //MVP presenter reference
    private RecipeDetailsContract.Presenter mPresenter;
    //listener for clicks on steps
    private OnStepClickListener mListener;

    //required empty constructor
    public RecipeDetailsFragment(){
    }

    public static RecipeDetailsFragment newInstance(){
        RecipeDetailsFragment fragment = new RecipeDetailsFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //The hosting Activity must implement this interface
        try{
            mListener = (OnStepClickListener) context;
        }catch (ClassCastException exp){
            throw new ClassCastException(context.toString()
                    + " must implement OnStepClickListener interface");
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        //saving selected step index
        outState.putInt(Constants.EXTRA_SELECTED_STEP_POSITION,
                mStepsAdapter.getSelectedPosition());
    }

    @Override
    public void onResume() {
        super.onResume();
        //start the presenter
        mPresenter.start();
    }

    @Override
    public void setPresenter(RecipeDetailsContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_recipe_details,
                container,
                false);
        mStepsAdapter = new RecipeStepsAdapter(null, this);
        ButterKnife.bind(this, root);
        //setup the recyclerView
        mStepsRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mStepsRecyclerView.setAdapter(mStepsAdapter);
        if(savedInstanceState != null){
            //restore the selected step index if any
            int savedSelectedPosition =
                    savedInstanceState.getInt(Constants.EXTRA_SELECTED_STEP_POSITION);
            mStepsAdapter.setSelectedPosition(savedSelectedPosition);
        }
        return root;
    }

    @Override
    public void showRecipeDetails(Recipe recipe) {
        //display recipe data
        mNameTextView.setText(recipe.getName());
        mServingsNumTextView.setText(String.format(getString(R.string.servings_format)
                , recipe.getServings()));
        //if no image, hide the image view
        if(recipe.getImage().isEmpty()){
            mImageView.setVisibility(View.GONE);
        }else {
            Picasso.get()
                    .load(recipe.getImage())
                    .placeholder(R.drawable.ic_picture)
                    .error(R.drawable.ic_recipe)
                    .into(mImageView);
        }
        mIngredientsTextView.setText(recipe.getIngredientListString());
        //set adapter data
        mStepsAdapter.setSteps(recipe.getSteps());
    }

    @Override
    public void showErrorMessage() {
        mErrorTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showStepDetailsUi(List<Step> steps, int stepIndex) {
        mListener.onStepClicked(steps, stepIndex);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    //callback from the adapter
    @Override
    public void onStepClicked(int stepIndex) {
        mPresenter.openStepDetails(stepIndex);
    }

    //An interface to allow this fragment communicate with hosting Activity
    interface OnStepClickListener {
        void onStepClicked(List<Step> steps, int stepIndex);
    }
}
