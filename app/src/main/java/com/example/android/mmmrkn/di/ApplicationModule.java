package com.example.android.mmmrkn.di;

import android.content.Context;

import com.example.android.mmmrkn.infra.api.LoginService;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApplicationModule {

    //実際にはAppクラスが代入される
    private Context applicationContext;

    //JSONとPOJOの相互変換を行うもの
    private Gson gson = new GsonBuilder ()
            //JSONのキーとPOJOの変数の表記の変換の仕方を指定する
            //今回はそのままで良いのでIDENTITYを設定
            //詳しくは https://qiita.com/yysk/items/e549ba40bc2accfdff35
            .setFieldNamingPolicy ( FieldNamingPolicy.IDENTITY )
            .create ();

    //Appクラスでnew ApplicationModule(this)のように生成される
    public ApplicationModule ( Context applicationContext ) {
        this.applicationContext = applicationContext;
    }

    //@Providesは、インジェクトに利用する変数を返す
    @Provides
    public Context provideContext () {
        return applicationContext;
    }

    //@Singletonははじめに生成したものを使いまわすことを意味する
    @Singleton
    @Provides
    public OkHttpClient provideOkHttpClient () {
        //Retrofitが内部で用いる、HTTP通信のクライアント
        //Interceptorを追加することで、CookieなどHTTPヘッダに関わる処理を追加したり、ログ出力を便利に出来たりする。
        return new OkHttpClient.Builder ()
                .build ();
    }

    @Singleton
    @Provides
    //このokHttpClientは上で生成したもの。
    //Daggerでは、先にprovideメソッドを宣言した変数は自由に引数にできる。
    public Retrofit provideRetrofit ( OkHttpClient okHttpClient ) {

        //ビルダパターンでRetrofitのインスタンスを生成する
        return new Retrofit.Builder ()
                .client ( okHttpClient )

                //WebAPIのFQDN
                .baseUrl ( "https://mmmr-mock-api.mybluemix.net" )

                //JSONとPOJOの相互変換を行うものに、gsonを指定
                .addConverterFactory ( GsonConverterFactory.create ( gson ) )

                //RxJavaのObservableで結果を受け取れるようにする
                .addCallAdapterFactory ( RxJava2CallAdapterFactory.create () )

                //設定が終わったのでインスタンスを生成
                .build ();
    }

    //このcreate()にはリフレクションを使用するが、
    //リフレクションは重いため、一度限りで済むようにシングルトンにする
    @Singleton
    @Provides
    public LoginService provideLoginService ( Retrofit retrofit ) {
        //Retrofitがリフレクションを用いて、インタフェースから実装クラスを自動生成する
        return retrofit.create ( LoginService.class );
    }

}

