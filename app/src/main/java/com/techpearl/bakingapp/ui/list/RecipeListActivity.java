package com.techpearl.bakingapp.ui.list;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.techpearl.bakingapp.R;
import com.techpearl.bakingapp.data.DataManager;
import com.techpearl.bakingapp.data.network.model.Recipe;

import java.util.List;

public class RecipeListActivity extends AppCompatActivity {

    private static final String TAG = RecipeListActivity.class.getSimpleName();
    private RecipeListPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecipeListFragment recipeListFragment = (RecipeListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_recipe_list);
        if(recipeListFragment == null){
            //create the fragment
            recipeListFragment = RecipeListFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_recipe_list,recipeListFragment)
                    .commit();
        }
        mPresenter = new RecipeListPresenter(DataManager.getsInstance(), recipeListFragment);


    }

}
