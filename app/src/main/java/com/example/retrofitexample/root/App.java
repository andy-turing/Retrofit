package com.example.retrofitexample.root;

import android.app.Application;

import com.example.retrofitexample.http.ApiModule;

public class App extends Application {

    private AplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerAplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .apiModule(new ApiModule())
                .build();

    }

    public AplicationComponent getComponent() {
        return component;
    }
}