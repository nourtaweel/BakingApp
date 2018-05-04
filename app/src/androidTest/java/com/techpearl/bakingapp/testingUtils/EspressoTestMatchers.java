package com.techpearl.bakingapp.testingUtils;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.hamcrest.Matcher;

/**
 * Created by Nour on 0004, 4/5/18.
 * custom matchers for this project
 */

public class EspressoTestMatchers {
    public static Matcher<View> withDrawable(final int resourceId) {
        return new DrawableMatcher(resourceId);
    }

    public static Matcher<View> noDrawable() {
        return new DrawableMatcher(-1);
    }

    public static Matcher<RecyclerView.ViewHolder> withHolderContainsText(String text){
        return new RecipeHolderMatcher(text);
    }
}
