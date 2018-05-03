package com.techpearl.bakingapp;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Parcelable;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.techpearl.bakingapp.ui.step.StepDetailsActivity;

import org.junit.Assume;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.not;

/**
 * Created by Nour on 0001, 1/5/18.
 * Test StepDetailsActivity on mobile view
 * Tests three types of Steps: text-only, text&image, text&video
 * Also, tests the state of next and back arrows
 */

@RunWith(AndroidJUnit4.class)
public class StepDetailsActivityTest {
    @Rule
    public ActivityTestRule<StepDetailsActivity> mStepDetailsActivityRule =
            new ActivityTestRule<>(StepDetailsActivity.class,
                    false,
                    false);

    @Test
    public void startActivityWithIntent_firstStep_onlyText(){
        Intent intent = new Intent();
        intent.putParcelableArrayListExtra(Constants.INTENT_EXTRA_STEPS,
                new ArrayList<Parcelable>(Constants.MOCK_STEPS_LIST));
        intent.putExtra(Constants.INTENT_EXTRA_STEP, Constants.MOCK_STEP_TEXT);
        mStepDetailsActivityRule.launchActivity(intent);
        String descriptionText = Constants.MOCK_STEPS_LIST
                .get(Constants.MOCK_STEP_TEXT)
                .getDescription();
        //description is correct
        onView(withId(R.id.textView_step_desc))
                .check(matches(withText(descriptionText)));
        //this step only contains text -> media frame is not displayed
        onView(withId(R.id.main_media_frame))
                .check(matches(not(isDisplayed())));
        //this is first -> step back arrow disabled
        onView(withId(R.id.imageView_back))
                .check(matches(not(isEnabled())));
        onView(withId(R.id.imageView_next))
                .check(matches(isEnabled()));
    }

    @Test
    public void startActivityWithIntent_secondStep_textImage(){
        Intent intent = new Intent();
        intent.putParcelableArrayListExtra(Constants.INTENT_EXTRA_STEPS,
                new ArrayList<Parcelable>(Constants.MOCK_STEPS_LIST));
        intent.putExtra(Constants.INTENT_EXTRA_STEP, Constants.MOCK_STEP_IMAGE);
        mStepDetailsActivityRule.launchActivity(intent);
        String descriptionText = Constants.MOCK_STEPS_LIST
                .get(Constants.MOCK_STEP_IMAGE)
                .getDescription();
        //description is correct
        onView(withId(R.id.textView_step_desc))
                .check(matches(withText(descriptionText)));
        //this step only contains text -> media frame & image are displayed
        onView(withId(R.id.main_media_frame))
                .check(matches(isDisplayed()));
        onView(withId(R.id.imageView_thumbnail))
                .check(matches(isDisplayed()));
        //this is second step -> step back & next arrow are enabled
        onView(withId(R.id.imageView_back))
                .check(matches(isEnabled()));
        onView(withId(R.id.imageView_next))
                .check(matches(isEnabled()));
    }

    @Test
    public void startActivityWithIntent_thirdStep_textVideo(){
        Intent intent = new Intent();
        intent.putParcelableArrayListExtra(Constants.INTENT_EXTRA_STEPS,
                new ArrayList<Parcelable>(Constants.MOCK_STEPS_LIST));
        intent.putExtra(Constants.INTENT_EXTRA_STEP, Constants.MOCK_STEP_VIDEO);
        mStepDetailsActivityRule.launchActivity(intent);
        //only run this in portrait, in landscape Activity would start fullscreen video
        int dir = mStepDetailsActivityRule.getActivity().getResources().getConfiguration().orientation;
        Assume.assumeTrue(dir == Configuration.ORIENTATION_PORTRAIT);
        String descriptionText = Constants.MOCK_STEPS_LIST
                .get(Constants.MOCK_STEP_VIDEO)
                .getDescription();
        //description is correct
        onView(withId(R.id.textView_step_desc))
                .check(matches(withText(descriptionText)));
        //this step only contains text -> media frame & video are displayed
        onView(withId(R.id.main_media_frame))
                .check(matches(isDisplayed()));
        onView(withId(R.id.exoPlayerView))
                .check(matches(isDisplayed()));
        //this is second step -> next arrow is disabled
        onView(withId(R.id.imageView_back))
                .check(matches(isEnabled()));
        onView(withId(R.id.imageView_next))
                .check(matches(not(isEnabled())));
    }

    @Test
    public void clickBackArrow_moveToPreviousStep(){
        //start the activity in middle step
        Intent intent = new Intent();
        intent.putParcelableArrayListExtra(Constants.INTENT_EXTRA_STEPS,
                new ArrayList<Parcelable>(Constants.MOCK_STEPS_LIST));
        intent.putExtra(Constants.INTENT_EXTRA_STEP, Constants.MOCK_STEP_IMAGE);
        mStepDetailsActivityRule.launchActivity(intent);
        onView(withId(R.id.imageView_back))
                .perform(scrollTo(), click());
        //get description of previos step to verify it is displayed
        String descriptionText = Constants.MOCK_STEPS_LIST
                .get(Constants.MOCK_STEP_TEXT)
                .getDescription();
        onView(withId(R.id.textView_step_desc))
                .check(matches(withText(descriptionText)));
    }

    @Test
    public void clickNextArrow_moveToNextStep(){
        //start the activity in middle step
        Intent intent = new Intent();
        intent.putParcelableArrayListExtra(Constants.INTENT_EXTRA_STEPS,
                new ArrayList<Parcelable>(Constants.MOCK_STEPS_LIST));
        intent.putExtra(Constants.INTENT_EXTRA_STEP, Constants.MOCK_STEP_IMAGE);
        mStepDetailsActivityRule.launchActivity(intent);
        //only run this in portrait, in landscape Activity would start fullscreen video
        int dir = mStepDetailsActivityRule.getActivity().getResources().getConfiguration().orientation;
        Assume.assumeTrue(dir == Configuration.ORIENTATION_PORTRAIT);

        //click on next arrow
        onView(withId(R.id.imageView_next))
                .perform(scrollTo(), click());
        //get description of next step to verify it is displayed
        String descriptionText = Constants.MOCK_STEPS_LIST
                .get(Constants.MOCK_STEP_VIDEO)
                .getDescription();
        onView(withId(R.id.textView_step_desc))
                .check(matches(withText(descriptionText)));
    }
}
