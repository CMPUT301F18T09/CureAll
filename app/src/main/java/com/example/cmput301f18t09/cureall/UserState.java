package com.example.cmput301f18t09.cureall;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class UserState {
    private ConnectivityManager connectivityManager;
    public UserState(Context context){
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public boolean getState(){
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
