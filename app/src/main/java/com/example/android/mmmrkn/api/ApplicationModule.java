package com.example.android.mmmrkn.api;

import android.content.Context;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javax.inject.Singleton;
import dagger.Component;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApplicationModule {
    
    private Context applicationContext;
    private Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
    
    public ApplicationModule(Context applicationContext) {
        this.applicationContext = applicationContext;
    }
    
    @Provides
    public Context provideContext() {
        return applicationContext;
    }
    
    @Provides
    public OkHttpClient providesOkHttp() {
        return new OkHttpClient.Builder()
                       .build();
    }
    
    @Provides
    public Retrofit provideRetrofit( OkHttpClient oktHttpClient) {
        return new Retrofit.Builder()
                       .client(oktHttpClient)
                       .baseUrl("https://mmmr-mock-api.mybluemix.net")
                       .addConverterFactory(GsonConverterFactory.create(gson))
                       .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                       .build();
    }
    

}

