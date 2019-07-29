package com.example.apinstagramclone;


import android.app.Application;

import com.parse.Parse;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(getString(R.string.YOUR_APP_ID))
                // if defined
                .clientKey(getString(R.string.YOUR_CLIENT_KEY))
                .server(getString(R.string.BACKEND_URL))
                .build()
        );
    }
}
