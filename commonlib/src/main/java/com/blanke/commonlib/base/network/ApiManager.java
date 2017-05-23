package com.blanke.commonlib.base.network;

import com.blanke.commonlib.CommonConfig;
import com.blanke.commonlib.base.network.interceptor.ApiLoggingInterceptor;
import com.blanke.commonlib.base.network.interceptor.TokenInterceptor;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by blanke on 2017/3/17.
 * 和业务相关的，项目http全局初始化在这，制定策略(json，rxJava等)
 */

public class ApiManager {
    private static ApiManager sApiManager;
    private ApiHelper apiHelper;
    private TokenInterceptor tokenInterceptor;

    public static ApiManager getInstance() {
        if (sApiManager == null) {
            synchronized (ApiManager.class) {
                if (sApiManager == null) {
                    sApiManager = new ApiManager();
                    sApiManager.init();
                }
            }
        }
        return sApiManager;
    }

    /**
     * 可在application调用，getRetrofit才会真正build
     */
    public void init() {
        tokenInterceptor = new TokenInterceptor(CommonConfig.KEY_HTTP_HEAD_TOKEN);
        apiHelper = new ApiHelper.Builder()
                .baseUrl(CommonConfig.HTTP_BASE_URL)
                .connectTimeOut(CommonConfig.HTTP_CONNECT_TIMEOUT)
                .readTimeOut(CommonConfig.HTTP_READ_TIMEOUT)
                .addConverterFactory(GsonConverterFactory.create())
                .addcallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addInterceptors(new ApiLoggingInterceptor(CommonConfig.KEY_HTTP_LOG))
                .addInterceptors(tokenInterceptor)
                .isMock(CommonConfig.HTTP_IS_MOCK)
                .build();
    }

    public Retrofit getRetrofit() {
        if (apiHelper != null) {
            return apiHelper.getRetrofit();
        }
        return null;
    }

    public void setToken(String token) {
        if (tokenInterceptor != null) {
            tokenInterceptor.setToken(token);
        }
    }

    public String getToken() {
        if (tokenInterceptor != null) {
            return tokenInterceptor.getToken();
        }
        return null;
    }
}
