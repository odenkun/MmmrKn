package com.example.android.mmmrkn.di;

import android.content.Context;

import com.example.android.mmmrkn.infra.api.LoginService;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
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
        //Retrofitが内部で用いる、HTTP通信のクライアント
        //ここをいじることで、CookieなどHTTPヘッダに関わる処理を追加したり、ログ出力を便利に出来たりする
        return new OkHttpClient.Builder()
                       .build();
    }
    
    @Provides
    public Retrofit provideRetrofit( OkHttpClient oktHttpClient) {
        //ビルダパターンでRetrofitのインスタンスを生成する
        return new Retrofit.Builder()
                //上で生成したものを利用する
                       .client(oktHttpClient)
                //WebAPIのFQDN
                       .baseUrl("https://mmmr-mock-api.mybluemix.net")
                //JSONとPOJOの相互変換を行うものに、gsonを指定
                       .addConverterFactory(GsonConverterFactory.create(gson))
                //RxJavaで結果を受け取れるようにする
                       .addCallAdapterFactory( RxJava2CallAdapterFactory.create())
                //設定が終わったのでインスタンスを生成
                       .build();
    }

    @Provides
    public LoginService provideLoginService( Retrofit retrofit) {
        //Retrofitが、インタフェースから実装クラスを自動生成する
        return retrofit.create(LoginService.class);
    }

}

