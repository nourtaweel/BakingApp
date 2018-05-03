package com.techpearl.bakingapp.ui.recipe;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.techpearl.bakingapp.R;
import com.techpearl.bakingapp.data.network.model.Step;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Nour on 0017, 17/4/18.
 */

public class RecipeStepsAdapter extends RecyclerView.Adapter<RecipeStepsAdapter.StepViewHolder>{

    private List<Step> mSteps;

    private StepClickListener mListener;

    private int selectedPosition = RecyclerView.NO_POSITION;

    public RecipeStepsAdapter(List<Step> steps, StepClickListener listener){
        this.mSteps = steps;
        this.mListener = listener;
    }

    //set data source and notify change to refresh ui
    public void setSteps(List<Step> steps){
        this.mSteps = steps;
        notifyDataSetChanged();
    }

    public void setSelectedPosition(int position){
        selectedPosition = position;
        notifyItemChanged(position);
    }
    public int getSelectedPosition(){
        return selectedPosition;
    }
    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        return new StepViewHolder(inflater.inflate(R.layout.step_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder holder, int position) {
        holder.bind(position);
    }


    @Override
    public int getItemCount() {
        if(mSteps == null){
            return 0;
        }
        return mSteps.size();
    }

    class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.imageView_step_type) ImageView mStepTypeImageView;
        @BindView(R.id.textView_step_short) TextView mStepShortDescriptionTextView;
        @BindView(R.id.step_container) View mRoot;

        StepViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }
        void bind(int index){
            Step step = mSteps.get(index);
            int imageRes = R.drawable.ic_list;
            if(!step.getVideoURL().isEmpty()){
                imageRes = R.drawable.ic_video_player;
            }else if(!step.getThumbnailURL().isEmpty()){
                imageRes = R.drawable.ic_camera;
            }
            mStepTypeImageView.setImageResource(imageRes);
            mStepShortDescriptionTextView.setText(step.getShortDescription());
            mRoot.setSelected(selectedPosition == index);
        }

        @Override
        public void onClick(View v) {
            highlightSelected(getAdapterPosition());
            mListener.onStepClicked(getAdapterPosition());
        }

        public void highlightSelected(int newPosition) {
            int oldPosition = selectedPosition;
            selectedPosition = newPosition;
            notifyItemChanged(selectedPosition);
            notifyItemChanged(oldPosition);
        }
    }

    interface StepClickListener {
        void onStepClicked(int stepIndex);
    }
}
