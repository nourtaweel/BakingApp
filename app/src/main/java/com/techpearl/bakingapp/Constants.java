package com.techpearl.bakingapp;

import com.techpearl.bakingapp.data.network.model.Ingredient;
import com.techpearl.bakingapp.data.network.model.Recipe;
import com.techpearl.bakingapp.data.network.model.Step;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nour on 0017, 17/4/18.
 * A class that provides all constant values for all classes as static values
 */

public class Constants {
    public static final String INTENT_EXTRA_RECIPE = "extra_recipe";
    public static final String INTENT_EXTRA_STEP = "extra_step";

    public static final String INTENT_EXTRA_RECIPE_MARSHALED = "extra_recipe_marshaled";
    public static final String INTENT_EXTRA_STEPS = "extra_steps";

    public static final Recipe MOCK_RECIPE_OBJECT = getMockRecipeObject();
    public static final List<Step> MOCK_STEPS_LIST = getMockStepsList();
    public static final int MOCK_STEP_TEXT = 0;
    public static final int MOCK_STEP_IMAGE = 1;
    public static final int MOCK_STEP_VIDEO = 2;

    private static Recipe getMockRecipeObject(){
        Recipe r =new Recipe();
        r.setName("Mock Recipe");
        r.setServings(2);
        r.setImage("");
        Ingredient ingredient = new Ingredient();
        ingredient.setIngredient("mock ingredient");
        ingredient.setMeasure("mock measure");
        ingredient.setQuantity(2.0f);
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(ingredient);
        r.setIngredients(ingredients);
        r.setSteps(getMockStepsList());
        return r;
    }

    public static Step getMockStepObject(boolean hasImage, boolean hasVideo, int index) {
        Step step = new Step();
        step.setDescription("mock step description " + index);
        step.setShortDescription("mock short description " + index);
        step.setThumbnailURL(hasImage?
                "https://www.recipeboy.com/wp-content/uploads/2016/09/No-Bake-Nutella-Pie.jpg" : "");
        step.setVideoURL(hasVideo?
                "https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffda45_9-add-mixed-nutella-to-crust-creampie/9-add-mixed-nutella-to-crust-creampie.mp4" : "");
        return step;
    }
    public static List<Step> getMockStepsList(){
        List<Step> steps = new ArrayList<>();
        steps.add(getMockStepObject(false,false,0));
        steps.add(getMockStepObject(true,false, 1));
        steps.add(getMockStepObject(false,true, 2));
        return steps;
    }
}
