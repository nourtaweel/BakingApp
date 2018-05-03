package com.techpearl.bakingapp.data.network;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Nour on 0013, 13/4/18.
 *
 * A Wrapper class based on retrofit Callback
 * this is used by the MVP presenter layer to define actions upon remote calls (success/fail)
 */

public abstract class RemoteCallback<T> implements Callback<T> {
    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if(response.body() != null){
            onSuccess(response.body());
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        onFailure(t);
    }

    /*should be implemented by subclass
    * make action when the remote call is successful*/
    public abstract void onSuccess(T response);

    /*should be implemented by subclass
    * make action when the remote call is unsuccessful*/
    public abstract void onFailure(Throwable throwable);
}
