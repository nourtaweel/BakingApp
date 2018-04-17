package com.techpearl.bakingapp.ui.recipe;

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
import com.techpearl.bakingapp.R;
import com.techpearl.bakingapp.data.network.model.Ingredient;
import com.techpearl.bakingapp.data.network.model.Recipe;
import com.techpearl.bakingapp.data.network.model.Step;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Nour on 0017, 17/4/18.
 * a Fragment the shows a List/Grid of available {@link Step}s
 */

public class DetailsFragment extends Fragment implements DetailsContract.View{

    private RecipeStepsAdapter mStepsAdapter;
    @BindView(R.id.textView_name) TextView mNameTextView;
    @BindView(R.id.textView_servings) TextView mServingsNumTextView;
    @BindView(R.id.imageView_recipe_image) ImageView mImageView;
    @BindView(R.id.textView_ingredients) TextView mIngredientsTextView;
    @BindView(R.id.recyclerView_steps) RecyclerView mStepsRecyclerView;

    private DetailsContract.Presenter mPresenter;

    //required empty constructor
    public DetailsFragment(){

    }

    public static DetailsFragment newInstance(){
        DetailsFragment fragment = new DetailsFragment();
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(DetailsContract.Presenter presenter) {
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
        mStepsAdapter = new RecipeStepsAdapter(null);
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

    }

    @Override
    public boolean isActive() {
        return isAdded();
    }
}
