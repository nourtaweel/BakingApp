package com.techpearl.bakingapp.ui.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
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
       /* for(int id : appWidgetIds){
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.recipe_widget_layout);
            //update the widget views with recipe data
            remoteViews.setTextViewText(R.id.textView_recipe_name, recipe.getName());
            remoteViews.setTextViewText(R.id.textView_ingredients, recipe.getIngredientListString());
            //prepare intent object
            Intent intent = new Intent(context, RecipeDetailsActivity.class);
            //write Recipe object to parcel. Taken form this solution
            //http://blog.naboo.space/blog/2013/09/01/parcelable-in-pendingintent/
            //to write Parcelable in PendingIntent without losing the object data

            Parcel recipeParcel = Parcel.obtain();
            recipe.writeToParcel(recipeParcel, 0);
            recipeParcel.setDataPosition(0);
            intent.putExtra(Constants.INTENT_EXTRA_RECIPE_MARSHALED, recipeParcel.marshall());
            //set pending intent on the widget to open recipeDetailsActivity
            PendingIntent pendingIntent = PendingIntent.getActivity(this,
                    0,
                    intent,
                    PendingIntent.FLAG_CANCEL_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.widget_container, pendingIntent);
            appWidgetManager.updateAppWidget(id, remoteViews);
        }*/

    }
}
