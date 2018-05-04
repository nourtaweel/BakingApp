package com.techpearl.bakingapp.testingUtils;

import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.techpearl.bakingapp.R;
import com.techpearl.bakingapp.ui.list.RecipesAdapter;

import org.hamcrest.Description;

/**
 * Created by Nour on 0004, 4/5/18.
 */

public class RecipeHolderMatcher extends BoundedMatcher<RecyclerView.ViewHolder,
        RecipesAdapter.ViewHolder> {
    private String mText;

    public RecipeHolderMatcher(String text) {
        super(RecipesAdapter.ViewHolder.class);
        mText = text;
    }

    @Override
    protected boolean matchesSafely(RecipesAdapter.ViewHolder item) {
        TextView nameView = item.itemView.findViewById(R.id.textView_recipe_name);
        if(nameView == null){
            return false;
        }
        return nameView.getText().toString().contains(mText);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("No ViewHolder found with text: " + mText);
    }
}

