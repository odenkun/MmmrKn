package com.example.android.mmmrkn.presentation;


import io.reactivex.disposables.CompositeDisposable;

public abstract class Presenter {

    //通信のリスト
    protected final CompositeDisposable disposables = new CompositeDisposable ();
    /**
     *  ログイン通信の結果通知を受け取らないようにする
     */
    public void dispose () {
        disposables.dispose ();
    }

    /**
     *
     * @return 通信の応答待ちがあるかどうか
     */
    protected boolean hasDisposables () {
        return this.disposables.size () > 0;
    }

}
