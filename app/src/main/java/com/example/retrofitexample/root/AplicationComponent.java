package com.example.retrofitexample.root;

import com.example.retrofitexample.MainActivity;
import com.example.retrofitexample.http.ApiModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, ApiModule.class})
public interface AplicationComponent {
    void inject (MainActivity target);
}
