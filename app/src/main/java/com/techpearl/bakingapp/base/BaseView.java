package com.techpearl.bakingapp.base;

/**
 * Created by Nour on 0012, 12/4/18.
 * Base class for an MVP view
 */

public interface BaseView<T> {
    void setPresenter(T presenter);
}
