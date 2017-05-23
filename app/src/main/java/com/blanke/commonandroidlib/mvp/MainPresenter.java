package com.blanke.commonandroidlib.mvp;

import com.blanke.commonandroidlib.LoginResponse;
import com.blanke.commonandroidlib.MainApi;
import com.blanke.commonlib.base.network.ApiManager;

import java.util.Random;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by blanke on 2017/5/23.
 */

public class MainPresenter extends MainContract.MainPresenter {
    @Override
    public void login(String username, String pwd) {
        mRxLeakUtil.add(ApiManager.getInstance().getRetrofit().create(MainApi.class)
                .login()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Consumer<LoginResponse>() {
                    @Override
                    public void accept(@NonNull LoginResponse loginResponse) throws Exception {
                        getView().onLoginResult(loginResponse.getData().get(0).getUser().getName() + "\n" + new Random().nextInt(1024));
                    }
                }));
    }
}
