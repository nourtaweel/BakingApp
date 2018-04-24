package com.techpearl.bakingapp.ui.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.techpearl.bakingapp.Constants;
import com.techpearl.bakingapp.R;
import com.techpearl.bakingapp.data.network.model.Recipe;
import com.techpearl.bakingapp.ui.recipe.RecipeDetailsActivity;

/**
 * Created by Nour on 0023, 23/4/18.
 */

public class RecipeWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int count = appWidgetIds.length;
        //loop over all widgets by this provider
        for(int id : appWidgetIds){
            RemoteViews remoteViews = new RemoteViews(context.getPackageName()
                    , R.layout.recipe_ingredients_layout);
            Recipe recipe = null;
            remoteViews.setTextViewText(R.id.textView_recipe_name, "widget recipe name");
            remoteViews.setTextViewText(R.id.textView_ingredients, "ingredients");
            Intent intent = new Intent(context, RecipeDetailsActivity.class);
            intent.putExtra(Constants.INTENT_EXTRA_RECIPE, recipe);
            PendingIntent pendingIntent = PendingIntent.getActivity(context,
                    0,
                    intent,
                    0);
            remoteViews.setOnClickPendingIntent(R.id.widget_container, pendingIntent);
            appWidgetManager.updateAppWidget(id, remoteViews);
        }
    }
}
