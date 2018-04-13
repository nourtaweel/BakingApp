package com.techpearl.bakingapp.ui.list;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.techpearl.bakingapp.R;
import com.techpearl.bakingapp.data.DataManager;
import com.techpearl.bakingapp.data.network.model.Recipe;

import java.util.List;

public class ListActivity extends AppCompatActivity implements RecipeListContract.ListView {

    private static final String TAG = ListActivity.class.getSimpleName();
    private ListPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPresenter = new ListPresenter(DataManager.getsInstance());
        mPresenter.attachView(this);
        mPresenter.onLoadRecipesRequest();

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError() {

    }

    @Override
    public void showRecipes(List<Recipe> recipes) {
        for(Recipe recipe : recipes){
            Log.d(TAG, recipe.getName());
        }
    }
}
