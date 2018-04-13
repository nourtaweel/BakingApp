package com.techpearl.bakingapp.ui.base;

import android.support.annotation.NonNull;

/**
 * Created by Nour on 0012, 12/4/18.
 */

public abstract class BaseMVPPresenter<V> {
    protected V mView;

    public final void attachView(@NonNull V view){
        mView = view;
    }

    public final void detachView(){
        mView = null;
    }
    //check if the activity still available after async call
    public final boolean isViewAttached(){
        return mView != null;
    }
}
