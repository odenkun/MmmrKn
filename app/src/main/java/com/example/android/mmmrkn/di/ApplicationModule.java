package com.example.android.mmmrkn.di;

import android.content.Context;

import com.example.android.mmmrkn.infra.api.LoginService;
import com.example.android.mmmrkn.infra.api.StudentsService;
import com.example.android.mmmrkn.infra.api.PartiesService;
import com.example.android.mmmrkn.infra.api.StudentsService;
import com.example.android.mmmrkn.infra.api.TeacherService;
import com.example.android.mmmrkn.infra.entity.OrmaDatabase;
import com.example.android.mmmrkn.infra.entity.Teacher;
import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.github.gfx.android.orma.AccessThreadConstraint;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;


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

    @Singleton
    @Provides
    public SharedPrefsCookiePersistor provideSharedPrefsCookiePersistor (Context context) {
        return new SharedPrefsCookiePersistor (context);
    }


    //@Singletonははじめに生成したものを使いまわすことを意味する
    @Singleton
    @Provides
    //Retrofitが内部で用いる、HTTP通信のクライアント
    public OkHttpClient provideOkHttpClient (SharedPrefsCookiePersistor sharedPrefsCookiePersistor) {


        ClearableCookieJar cookieJar = new PersistentCookieJar (new SetCookieCache (), sharedPrefsCookiePersistor);


        //Interceptorを追加することで、HTTPヘッダに関わる処理を追加したり、ログ出力を便利に出来たりする
        //詳しくは http://yuki312.blogspot.jp/2016/03/okhttp-interceptor.html
        HttpLoggingInterceptor logger = new HttpLoggingInterceptor ( log -> Timber.tag ( "OkHttp" ).v ( log ));

        //ヘッダとボディのログを取得
        logger.setLevel ( HttpLoggingInterceptor.Level.HEADERS );
        logger.setLevel ( HttpLoggingInterceptor.Level.BODY );

        return new OkHttpClient.Builder ()
                .cookieJar(cookieJar)
                .addInterceptor ( logger )
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

    //このcreate()にはリフレクションを使用するが、
    //リフレクションは重いため、一度限りで済むようにシングルトンにする
    @Singleton
    @Provides
    public TeacherService provideTeacherService ( Retrofit retrofit ) {
        //Retrofitがリフレクションを用いて、インタフェースから実装クラスを自動生成する
        return retrofit.create ( TeacherService.class );
    }

    @Singleton
    @Provides
    public PartiesService providePartyService(Retrofit retrofit){
        return  retrofit.create(PartiesService.class);
    }


    @Singleton
    @Provides
    public StudentsService provideStudentsService(Retrofit retrofit){
        return retrofit.create(StudentsService.class);
    }

    @Singleton
    @Provides
    public OrmaDatabase provideOrma(Context context) {
        return OrmaDatabase.builder(context)
                .writeOnMainThread( AccessThreadConstraint.FATAL)
                .readOnMainThread(AccessThreadConstraint.FATAL)
                .trace(true)
                .build();
    }
}

