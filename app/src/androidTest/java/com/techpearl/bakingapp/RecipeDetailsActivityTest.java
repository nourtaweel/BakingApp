package com.techpearl.bakingapp;

import android.content.Intent;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.techpearl.bakingapp.ui.recipe.RecipeDetailsActivity;

import org.junit.Assume;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.hasSibling;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.techpearl.bakingapp.testingUtils.EspressoTestMatchers.withDrawable;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;

/**
 * Created by Nour on 0030, 30/4/18.
 */

@RunWith(AndroidJUnit4.class)
public class RecipeDetailsActivityTest {
    @Rule
    public ActivityTestRule<RecipeDetailsActivity> mRecipeDetailsRule =
            new ActivityTestRule<>(RecipeDetailsActivity.class,false,false);
    @Test
    public void startActivity_showRecipeDetails(){
        launchActivityWithIntent();
        onView(withId(R.id.textView_name))
                .check(matches(withText(Constants.MOCK_RECIPE_OBJECT.getName())));
        onView(withId(R.id.textView_ingredients))
                .check(matches(withText(Constants.MOCK_RECIPE_OBJECT.getIngredientListString())));
        //scroll to first item and check it matches first step
        onView(withId(R.id.recyclerView_steps))
                .perform(RecyclerViewActions.scrollToPosition(Constants.MOCK_STEP_TEXT));
        String shortDescription = Constants.MOCK_STEPS_LIST.get(Constants.MOCK_STEP_TEXT)
                .getShortDescription();
        onView(allOf(withText(shortDescription), hasSibling(withDrawable(R.drawable.ic_list))))
                .check(matches(isDisplayed()));
        //scroll to second item and check it matches second step
        onView(withId(R.id.recyclerView_steps))
                .perform(RecyclerViewActions.scrollToPosition(Constants.MOCK_STEP_IMAGE));
        shortDescription = Constants.MOCK_STEPS_LIST.get(Constants.MOCK_STEP_IMAGE)
                .getShortDescription();
        onView(allOf(withText(shortDescription), hasSibling(withDrawable(R.drawable.ic_camera))))
                .check(matches(isDisplayed()));
        //scroll to third item and check it matches third step
        onView(withId(R.id.recyclerView_steps))
                .perform(RecyclerViewActions.scrollToPosition(Constants.MOCK_STEP_VIDEO));
        shortDescription = Constants.MOCK_STEPS_LIST.get(Constants.MOCK_STEP_VIDEO)
                .getShortDescription();
        onView(allOf(withText(shortDescription), hasSibling(withDrawable(R.drawable.ic_video_player))))
                .check(matches(isDisplayed()));
    }

    @Test
    public void clickOnStep_launchActivity(){
        launchActivityWithIntent();
        String shortDescription = Constants.MOCK_STEPS_LIST.get(Constants.MOCK_STEP_TEXT)
                .getShortDescription();
        String longDescription = Constants.MOCK_STEPS_LIST.get(Constants.MOCK_STEP_TEXT)
                .getDescription();
        onView(withId(R.id.recyclerView_steps))
                .perform(RecyclerViewActions.scrollToPosition(Constants.MOCK_STEP_TEXT));
        onView(withText(shortDescription))
                .perform(click());
        onView(withId(R.id.textView_step_desc))
                .check(matches(withText(longDescription)));
    }

    @Test
    public void clickOnStep_twoPaneMode_navigationArrowsNotDisplayed(){
        launchActivityWithIntent();
        //preform this test only in two pane
        Assume.assumeTrue(mRecipeDetailsRule.getActivity().isTwoPane());
        onView(withId(R.id.recyclerView_steps))
                .perform(RecyclerViewActions.scrollToPosition(Constants.MOCK_STEP_TEXT));
        String shortDescription = Constants.MOCK_STEPS_LIST.get(Constants.MOCK_STEP_TEXT)
                .getShortDescription();
        onView(withText(shortDescription))
                .perform(click());
        onView(withId(R.id.step_detail_container)).check(matches(isDisplayed()));
        onView(withId(R.id.imageView_next)).check(matches(not(isDisplayed())));
        onView(withId(R.id.imageView_back)).check(matches(not(isDisplayed())));

    }

    private void launchActivityWithIntent() {
        Intent intent = new Intent();
        intent.putExtra(Constants.INTENT_EXTRA_RECIPE, Constants.MOCK_RECIPE_OBJECT);
        mRecipeDetailsRule.launchActivity(intent);
    }
}
