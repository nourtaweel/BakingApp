package com.techpearl.bakingapp.base;

import android.os.Bundle;
import android.os.Parcelable;

/**
 * Created by Nour on 0012, 12/4/18.
 */

public interface BasePresenter {
    void start();
    Bundle getState();
    void restoreState(Bundle state);
}
