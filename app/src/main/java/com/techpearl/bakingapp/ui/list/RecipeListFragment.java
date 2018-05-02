package com.techpearl.bakingapp.ui.list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.techpearl.bakingapp.Constants;
import com.techpearl.bakingapp.R;
import com.techpearl.bakingapp.data.network.model.Recipe;
import com.techpearl.bakingapp.ui.recipe.RecipeDetailsActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Nour on 0015, 15/4/18.
 * a Fragment the shows a List/Grid of available {@link Recipe}s
 */

public class RecipeListFragment extends Fragment implements
        RecipeListContract.View,
        RecipesAdapter.RecipeClickListener{

    private static final String TAG = RecipeListFragment.class.getSimpleName();
    private RecipeListContract.Presenter mPresenter;
    private RecipesAdapter mRecipesAdapter;
    private RecipeClickListener mListener;
    @BindView(R.id.recyclerView_recipes) RecyclerView mRecipesRecyclerView;
    @BindView(R.id.loadingIndicator) ProgressBar mLoadingIndicator;
    @BindView(R.id.textView_error_message) TextView mErrorTextView;
    @BindView(R.id.imageView_error)ImageView mErrorImage;
    @BindView(R.id.error_message_container) View mErrorContainer;

    /*Required empty constructor*/
    public RecipeListFragment(){

    }

    public static RecipeListFragment newInstance(){
        return new RecipeListFragment();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (RecipeClickListener) context;
        }catch (ClassCastException exp){
            Log.e(TAG, context.toString() + " must implement RecipeClickListener");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_list_recipes, container, false);
        ButterKnife.bind(this, root);
        mRecipesAdapter = new RecipesAdapter(null, this);
        mRecipesRecyclerView.setLayoutManager(
                new GridLayoutManager(this.getContext(), numberOfColumns()));
        mRecipesRecyclerView.setAdapter(mRecipesAdapter);
        return root;
    }

    @Override
    public void setPresenter(@NonNull RecipeListContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showLoadingIndicator() {
        mLoadingIndicator.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingIndicator() {
        mLoadingIndicator.setVisibility(View.GONE);
    }

    @Override
    public void showRecipes(List<Recipe> recipes) {
        mRecipesAdapter.setRecipeList(recipes);
    }

    @Override
    public void showLoadingErrorMessage(int code) {
        switch (code){
            case RecipeListContract.ERROR_CODE_NO_CONNECTION:
                mErrorTextView.setText(R.string.message_no_connection);
                mErrorImage.setImageResource(R.drawable.ic_no_signal_interface_symbol);
                break;
            case RecipeListContract.ERROR_CODE_API_FAIL:
                mErrorTextView.setText(R.string.message_api_fail);
                mErrorImage.setImageResource(R.drawable.ic_sad_face_on_screen);
                break;
        }
        mErrorContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void showRecipeDetailsUi(Recipe recipe) {
        Intent intent = new Intent(this.getContext(), RecipeDetailsActivity.class);
        intent.putExtra(Constants.INTENT_EXTRA_RECIPE, recipe);
        startActivity(intent);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    /* Dynamically determine number of columns for different widths
     */
    private int numberOfColumns() {
        if(getActivity() == null){
            return 0;
        }
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int widthDividerDp = 300;
        float widthDividerPx = widthDividerDp * (displayMetrics.densityDpi / 160f);
        int width = displayMetrics.widthPixels;
        return Math.round(width / widthDividerPx);
    }

    @Override
    public void onRecipeClicked(Recipe recipe) {
        mListener.onRecipeClicked(recipe);
    }

    public interface RecipeClickListener{
        void onRecipeClicked(Recipe recipe);
    }
}
