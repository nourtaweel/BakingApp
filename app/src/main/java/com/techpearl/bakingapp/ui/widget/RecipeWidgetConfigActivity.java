package com.techpearl.bakingapp.ui.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.RemoteViews;

import com.techpearl.bakingapp.Constants;
import com.techpearl.bakingapp.R;
import com.techpearl.bakingapp.data.DataManager;
import com.techpearl.bakingapp.data.network.model.Recipe;
import com.techpearl.bakingapp.ui.list.RecipeListFragment;
import com.techpearl.bakingapp.ui.list.RecipeListPresenter;
import com.techpearl.bakingapp.ui.recipe.RecipeDetailsActivity;

/**
 * Created by Nour on 0024, 24/4/18.
 */

public class RecipeWidgetConfigActivity extends AppCompatActivity implements
        RecipeListFragment.RecipeClickListener{
    int id = AppWidgetManager.INVALID_APPWIDGET_ID;
    RemoteViews remoteViews;
    AppWidgetManager widgetManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResult(RESULT_CANCELED);
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
        new RecipeListPresenter(DataManager.getsInstance(), recipeListFragment);
        //get AppWigetManager inctance
       widgetManager = AppWidgetManager.getInstance(this);
        remoteViews = new RemoteViews(this.getPackageName(), R.layout.recipe_widget_layout);
        //find the widget ID

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            id = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }
        if(id == AppWidgetManager.INVALID_APPWIDGET_ID){
            finish();
            return;
        }

    }

    @Override
    public void onRecipeClicked(Recipe recipe) {
        //update the widget views with recipe data
        remoteViews.setTextViewText(R.id.textView_recipe_name, recipe.getName());
        remoteViews.setTextViewText(R.id.textView_ingredients, recipe.getIngredientListString());
        //set pending intent on the widget to open recipeDetailsActivity
        Intent intent = new Intent(this, RecipeDetailsActivity.class);
        intent.putExtra(Constants.INTENT_EXTRA_RECIPE, recipe);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0,
                intent,
                0);
        remoteViews.setOnClickPendingIntent(R.id.widget_container, pendingIntent);
        widgetManager.updateAppWidget(id, remoteViews);
        //prepare results from configActivity
        Intent result = new Intent();
        result.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, id);
        setResult(RESULT_OK, result);
        finish();
    }

}
