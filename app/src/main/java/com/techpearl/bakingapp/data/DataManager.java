package com.techpearl.bakingapp.data;

import com.techpearl.bakingapp.data.network.BakingApiClient;
import com.techpearl.bakingapp.data.network.RemoteCallback;
import com.techpearl.bakingapp.data.network.ServiceGenerator;
import com.techpearl.bakingapp.data.network.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Nour on 0013, 13/4/18.
 * the DataManager provides a singleton implementation of the retrofit api calls
 * its used by the presenter layer to get data
 */

public class DataManager {

    private static DataManager sInstance;
    private final BakingApiClient bakingApiClient;

    /*provides a singleton object of the class*/
    public static DataManager getsInstance(){
        if(sInstance == null){
            sInstance = new DataManager();
        }
        return sInstance;
    }

    /*constructor that defines the api service */
    public DataManager(){
        bakingApiClient = ServiceGenerator.createService(BakingApiClient.class);
    }
    /*initiate a remote call that is handled by the data manager
    * @param listener for the call result*/
    public void getRecipeList(RemoteCallback<List<Recipe>> listener){
        Call<List<Recipe>> call =  bakingApiClient.recipes();
        call.enqueue(listener);
    }

}
