package com.example.retrofitexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.retrofitexample.http.TwitchAPI;
import com.example.retrofitexample.http.apimodel.Top;
import com.example.retrofitexample.http.apimodel.Twitch;
import com.example.retrofitexample.root.App;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    @Inject
    TwitchAPI twitchAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((App) getApplication()).getComponent().inject(this);

        Call<Twitch> call = twitchAPI.getTopGames("ucfushjsspe5yzxccpzfub35yz0zgs","application/vnd.twitchtv.v5+json");
        call.enqueue(new Callback<Twitch>() {
            @Override
            public void onResponse(Call<Twitch> call, Response<Twitch> response) {
                List<Top> gameTop = response.body().getTop();
                for (Top top:gameTop){
                    System.out.println(top.getGame().getName());
                }
            }

            @Override
            public void onFailure(Call<Twitch> call, Throwable t) {
                t.printStackTrace();
            }
        });

        twitchAPI.getTopGamesObservable("ucfushjsspe5yzxccpzfub35yz0zgs","application/vnd.twitchtv.v5+json").
                flatMap(new Func1<Twitch, Observable<Top>>() {
                    @Override
                    public Observable<Top> call(Twitch twitch) {
                        return Observable.from(twitch.getTop());
                    }
                }).flatMap(new Func1<Top, Observable<Integer>>() {
            @Override
            public Observable<Integer> call(Top top) {
                return Observable.just(top.getGame().getPopularity());
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Integer>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(Integer s) {
                System.out.println("Forom rx: Popularity " +s.toString());
            }
        });

        twitchAPI.getTopGamesObservable("ucfushjsspe5yzxccpzfub35yz0zgs","application/vnd.twitchtv.v5+json").
                flatMap(new Func1<Twitch, Observable<Top>>() {
                    @Override
                    public Observable<Top> call(Twitch twitch) {
                        return Observable.from(twitch.getTop());
                    }
                }).flatMap(new Func1<Top, Observable<String>>() {
            @Override
            public Observable<String> call(Top top) {
                return Observable.just(top.getGame().getName());
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(String s) {
                System.out.println("Forom rx: " +s);
            }
        });
        twitchAPI.getTopGamesObservable("ucfushjsspe5yzxccpzfub35yz0zgs","application/vnd.twitchtv.v5+json").
                flatMap(new Func1<Twitch, Observable<Top>>() {
                    @Override
                    public Observable<Top> call(Twitch twitch) {
                        return Observable.from(twitch.getTop());
                    }
                }).flatMap(new Func1<Top, Observable<String>>() {
            @Override
            public Observable<String> call(Top top) {
                return Observable.just(top.getGame().getName());
            }
        }).filter(new Func1<String, Boolean>() {
            @Override
            public Boolean call(String s) {
                return s.startsWith("H");
            }
        })
        .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(String s) {
                System.out.println("Forom rx: Starts with H " +s);
            }
        });




    }
}
