package com.techpearl.bakingapp.ui.recipe;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Nour on 0017, 17/4/18.
 * a Fragment the shows a List/Grid of available {@link Step}s
 */

public class RecipeDetailsFragment extends Fragment implements
        RecipeDetailsContract.View,
        RecipeStepsAdapter.StepClickListener{

    private RecipeStepsAdapter mStepsAdapter;
    @BindView(R.id.textView_name) TextView mNameTextView;
    @BindView(R.id.textView_servings) TextView mServingsNumTextView;
    @BindView(R.id.imageView_recipe_image) ImageView mImageView;
    @BindView(R.id.textView_ingredients) TextView mIngredientsTextView;
    @BindView(R.id.recyclerView_steps) RecyclerView mStepsRecyclerView;

    private RecipeDetailsContract.Presenter mPresenter;

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
        try{
            mListener = (OnStepClickListener) context;
        }catch (ClassCastException exp){
            throw new ClassCastException(context.toString()
                    + " must implement OnStepClickListener interface");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
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
        mStepsRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mStepsRecyclerView.setAdapter(mStepsAdapter);
        return root;
    }

    @Override
    public void showRecipeDetails(Recipe recipe) {
        mNameTextView.setText(recipe.getName());
        mServingsNumTextView.setText(String.format(getString(R.string.servings_format)
                , recipe.getServings()));
        if(recipe.getImage().isEmpty()){
           // mImageView.setVisibility(View.GONE);
            mImageView.setImageResource(R.drawable.ic_recipe);
        }else {
            Picasso.get()
                    .load(recipe.getImage())
                    .error(R.drawable.ic_recipe)
                    .into(mImageView);
        }
        String ingredientsString = "";
        for(Ingredient ingredient : recipe.getIngredients()){
            ingredientsString += "\u2022"
                    + " " + ingredient.getQuantity()
                    + " " + ingredient.getMeasure()
                    + " " + ingredient.getIngredient()
                    + "\n";
        }
        mIngredientsTextView.setText(ingredientsString);
        mStepsAdapter.setSteps(recipe.getSteps());
    }

    @Override
    public void showErrorMessage() {

    }

    @Override
    public void showStepDetailsUi(Step step) {
        mListener.onStepClicked(step);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void onStepClicked(Step step) {
        mPresenter.openStepDetails(step);
    }

    interface OnStepClickListener {
        void onStepClicked(Step step);
    }
}
