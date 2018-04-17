package com.techpearl.bakingapp.ui.list;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.techpearl.bakingapp.R;
import com.techpearl.bakingapp.data.network.model.Recipe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Nour on 0016, 16/4/18.
 * An Adapater for the list of recipes recycler view
 */

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.ViewHolder> {

    private List<Recipe> mRecipes;
    private RecipeClickListener mListener;

    public RecipesAdapter(List<Recipe> recipes, RecipeClickListener listener){
        mRecipes = recipes;
        mListener = listener;
    }
    public void setRecipeList(List<Recipe> recipeList){
        this.mRecipes = recipeList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        return new ViewHolder(
                inflater.inflate(R.layout.recipe_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        if(mRecipes == null){
            return 0;
        }
        return mRecipes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.imageView_recipe_image) ImageView recipeImageView;
        @BindView(R.id.textView_recipe_name) TextView recipeNameTextView;
        @BindView(R.id.textView_servings) TextView recipeServingsTextView;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }
        public void bind(int index){
            //bind the views with data
            Recipe r = mRecipes.get(index);
            if(!r.getImage().isEmpty()){
                Picasso.get()
                        .load(r.getImage())
                        .error(R.drawable.ic_recipe)
                        .into(recipeImageView);
            }

            recipeNameTextView.setText(r.getName());
            //TODO: fix countable issue
            recipeServingsTextView.setText(r.getServings() + "servings");
        }

        @Override
        public void onClick(View v) {
            mListener.onRecipeClicked(mRecipes.get(getAdapterPosition()));
        }
    }

    public interface RecipeClickListener{
        public void onRecipeClicked(Recipe recipe);
    }
}
