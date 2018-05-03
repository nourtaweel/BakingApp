package com.techpearl.bakingapp.base;

import android.os.Bundle;
import android.os.Parcelable;

/**
 * Created by Nour on 0012, 12/4/18.
 * Base class for an MVP presenter with state
 */

public interface BasePresenter {
    void start();
    //return a bundle that contains the presenter state in order to save it between creations
    Bundle getState();
    //restore a previous state after recreation
    void restoreState(Bundle state);
}
