package com.blanke.commonlib.base.network;

import com.blanke.commonlib.base.network.interceptor.ApiLoggingInterceptor;
import com.blanke.commonlib.base.network.interceptor.TokenInterceptor;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by blanke on 2017/3/17.
 */

public class ApiManager {
    private static ApiManager sApiManager;
    private static ApiHelper apiHelper;

    public static ApiManager getInstance() {
        if (sApiManager == null) {
            synchronized (ApiManager.class) {
                if (sApiManager == null) {
                    sApiManager = new ApiManager();
                }
            }
        }
        return sApiManager;
    }

    /**
     * 可在application调用，getRetrofit才会真正build
     * @param baseUrl
     */
    public static void init(String baseUrl) {
        apiHelper = new ApiHelper.Builder()
                .baseUrl(baseUrl)
                .connectTimeOut(3000)
                .readTimeOut(3000)
                .addConverterFactory(GsonConverterFactory.create())
                .addcallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addInterceptors(new ApiLoggingInterceptor())
                .addInterceptors(new TokenInterceptor(""))

                .build();
    }

    public static ApiHelper getApiHelper() {
        return apiHelper;
    }

    public static Retrofit getRetrofit() {
        if (apiHelper != null) {
            return apiHelper.getRetrofit();
        }
        return null;
    }
}
