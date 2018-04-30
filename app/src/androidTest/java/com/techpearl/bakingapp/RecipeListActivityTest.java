package com.techpearl.bakingapp;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.techpearl.bakingapp.ui.list.RecipeListActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Nour on 0030, 30/4/18.
 * Test class for the RecipeListActivity
 */

@RunWith(AndroidJUnit4.class)
public class RecipeListActivityTest {
    private final String RECIPE_NAME = "Yellow Cake";
    private IdlingResource mIdlingResource;
    @Rule
    public ActivityTestRule<RecipeListActivity> mRecipeListActivityTestRule =
            new ActivityTestRule<>(RecipeListActivity.class);

    @Before
    public void registerIdlingResource(){
        mIdlingResource = mRecipeListActivityTestRule.getActivity().getIdlingResource();
        IdlingRegistry.getInstance().register(mIdlingResource);
    }
    @Test
    public void clickRecipe_launchRecipeDetails(){
        //scroll to view with text RECIPE_NAME
        onView(withId(R.id.recyclerView_recipes))
                .perform(RecyclerViewActions.scrollTo(hasDescendant(withText(RECIPE_NAME))));
        //click on the view
        onView(withText(RECIPE_NAME)).perform(click());
        //assert that new screen has the recipe name
        onView(withId(R.id.textView_name))
                .check(matches(withText(RECIPE_NAME)));
    }
    @After
    public void unregisterIdlingResource(){
        if (mIdlingResource != null){
            IdlingRegistry.getInstance().unregister(mIdlingResource);
        }
    }
}

