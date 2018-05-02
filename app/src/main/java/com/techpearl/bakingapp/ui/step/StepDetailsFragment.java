package com.techpearl.bakingapp.ui.step;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlaybackControlView;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;
import com.techpearl.bakingapp.Constants;
import com.techpearl.bakingapp.R;
import com.techpearl.bakingapp.data.network.model.Step;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Nour on 0018, 18/4/18.
 * A Fragment that shows the step media and/or the instructions
 * this fragment is displayed in a stand-alone StepDetailsActivity
 * or as the detail part in the master/detail screen in RecipeDetailsActivity (on a tablet)
 */

public class StepDetailsFragment extends Fragment implements StepDetailsContract.View{

    private StepDetailsContract.Presenter mPresenter;
    @BindView(R.id.textView_step_desc)
    TextView mStepDescriptionTextView;
    @BindView(R.id.imageView_thumbnail)
    ImageView mThumbnailImageView;
    @BindView(R.id.exoPlayerView)
    PlayerView mPlayerView;
    @BindView(R.id.imageView_back)
    ImageView mBackImageView;
    @BindView(R.id.imageView_next)
    ImageView mNextImageView;
    private SimpleExoPlayer mPlayer;
    private Dialog mFullScreenDialog;
    @BindView(R.id.exo_fullscreen_icon)
    ImageView mFullScreenIcon;
    @BindView(R.id.exo_fullscreen_button)
    View mFullScreenButton;
    @BindView(R.id.main_media_frame)
    View mMediaFrame;
    private long mPosition = 0;
    private boolean isTwoPane;

    private StepNavigationListener mListener;

    public StepDetailsFragment(){

    }

    public static StepDetailsFragment newInstance(boolean isTwoPane){
        StepDetailsFragment fragment = new StepDetailsFragment();
        fragment.isTwoPane = isTwoPane;
        return fragment;
    }
    @Override
    public void setPresenter(StepDetailsContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //retrieve the presenter state & player position if this fragment is getting recreated
        if(savedInstanceState != null){
            Bundle presenterSavedState = savedInstanceState.getBundle(Constants.EXTRA_STATE_PRESENTER);
            if(presenterSavedState != null){
                mPresenter = new StepDetailsPresenter(this, null, false, 0);
                mPresenter.restoreState(presenterSavedState);
            }
            //mPresenter.restoreState(savedInstanceState.getBundle("presenter_state"));
            mPosition = savedInstanceState.getLong(Constants.EXTRA_PLAYER_POSITION);

        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        //save the presenter state (step object + fullscreen mode boolean)
        outState.putBundle(Constants.EXTRA_STATE_PRESENTER, mPresenter.getState());
        //saving the player position if available
        if(mPlayer != null){
            outState.putLong(Constants.EXTRA_PLAYER_POSITION, mPlayer.getCurrentPosition());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_step_layout,
                container,
                false);
        ButterKnife.bind(this, root);
        mMediaFrame.setVisibility(View.GONE);
        mPlayerView.setVisibility(View.GONE);
        mThumbnailImageView.setVisibility(View.GONE);
        initFullscreenButton();
        initFullscreenDialog();
        return root;
    }

    @Override
    public void showStepDetails(Step step) {
        mStepDescriptionTextView.setText(step.getDescription());
        if(!step.getVideoURL().isEmpty()){
            mMediaFrame.setVisibility(View.VISIBLE);
            mPlayerView.setVisibility(View.VISIBLE);
            mThumbnailImageView.setVisibility(View.GONE);
            initExoPlayer(step.getVideoURL());
        }else if(!step.getThumbnailURL().isEmpty()){
            mMediaFrame.setVisibility(View.VISIBLE);
            mThumbnailImageView.setVisibility(View.VISIBLE);
            mPlayerView.setVisibility(View.GONE);
            Picasso.get()
                    .load(step.getThumbnailURL())
                    .placeholder(R.drawable.ic_camera)
                    .error(R.drawable.ic_camera)
                    .into(mThumbnailImageView);
        }else {
            mMediaFrame.setVisibility(View.GONE);
        }
        if(isTwoPane){
            mBackImageView.setVisibility(View.GONE);
            mNextImageView.setVisibility(View.GONE);
        }
    }

    @Override
    public void showFullScreenDialog() {
        ((ViewGroup) mPlayerView.getParent()).removeView(mPlayerView);
        mFullScreenDialog.addContentView(mPlayerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_fullscreen_skrink));
        mFullScreenDialog.show();
    }

    @Override
    public void goToNormalScreen() {
        ((ViewGroup) mPlayerView.getParent()).removeView(mPlayerView);
        ((FrameLayout) getActivity().findViewById(R.id.main_media_frame)).addView(mPlayerView);
        mFullScreenDialog.dismiss();
        mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_fullscreen_expand));
    }

    /**
     * initialize Exoplayer
    * @param videoURL Uri of the video to play*/
    private void initExoPlayer(String videoURL) {
        if(mPlayer != null){
            return;
        }
        //create a default TrackSelector
        BandwidthMeter bwMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bwMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        // create the player
        mPlayer =
                ExoPlayerFactory.newSimpleInstance(this.getContext(), trackSelector);
        //attach the player to the PlayevView
        mPlayerView.setPlayer(mPlayer);
        //prepare DataSource
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this.getContext(),
                Util.getUserAgent(this.getContext(), "bakingApp"));
        MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(videoURL));
        mPlayer.prepare(videoSource);
        mPlayer.seekTo(mPosition);
        mPlayer.setPlayWhenReady(true);
    }

    /**
     * release Exoplayer resources*/
    private void releasePlayer(){
        if(mPlayer == null)
            return;
        mPlayer.stop();
        mPlayer.release();
        mPlayer = null;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mPlayer != null){
            releasePlayer();
        }
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void disableBack() {
        mBackImageView.setEnabled(false);
    }

    @Override
    public void disableNext() {
        mNextImageView.setEnabled(false);
    }

    @Override
    public void enableNext() {
        mNextImageView.setEnabled(true);
    }

    @Override
    public void enableBack() {
        mBackImageView.setEnabled(true);
    }

    private void initFullscreenButton() {

        PlayerControlView controlView = mPlayerView.findViewById(R.id.exo_controller);
        mFullScreenIcon = controlView.findViewById(R.id.exo_fullscreen_icon);
        mFullScreenButton = controlView.findViewById(R.id.exo_fullscreen_button);
        mFullScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.onToggleFullScreen();
            }
        });
    }

    private void initFullscreenDialog() {

        mFullScreenDialog = new Dialog(this.getContext(),
                android.R.style.Theme_Black_NoTitleBar_Fullscreen) {
            public void onBackPressed() {
                mPresenter.onBackPressedInFullScreenDialog();
                super.onBackPressed();
            }
        };
    }

    @OnClick(R.id.imageView_next)
    void onNextClicked(){
        releasePlayer();
        mPresenter.onNextStepClicked();
    }

    @OnClick(R.id.imageView_back)
    void onBackClicked(){
        releasePlayer();
        mPresenter.onPreviousStepClicked();
    }

    public interface StepNavigationListener{
        void onNavigateTo(int stepIndex);
    }
}
