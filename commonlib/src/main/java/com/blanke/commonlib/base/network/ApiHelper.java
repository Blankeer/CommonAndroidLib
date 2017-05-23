package com.blanke.commonlib.base.network;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * 相对抽象的，不依赖业务的，包装okHttpClient和Retrofit建造者
 */
class ApiHelper {
    private String baseUrl;
    private int readTimeOut = 3000;
    private int connectTimeOut = 3000;
    private List<Interceptor> interceptors;
    private List<CallAdapter.Factory> callAdapterFactories;
    private List<Converter.Factory> converterFactories;
    private Retrofit mRetrofit;
    private boolean isMock;

    private ApiHelper() {
    }

    private ApiHelper(Builder builder) {
        baseUrl = builder.baseUrl;
        readTimeOut = builder.readTimeOut;
        connectTimeOut = builder.connectTimeOut;
        interceptors = builder.interceptors;
        callAdapterFactories = builder.callAdapterFactories;
        converterFactories = builder.converterFactories;
        isMock = builder.isMock;
    }

    public Retrofit getRetrofit() {
        if (mRetrofit != null) {
            return mRetrofit;
        }
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder()
                .readTimeout(readTimeOut, TimeUnit.MILLISECONDS)
                .connectTimeout(connectTimeOut, TimeUnit.MILLISECONDS);
        for (Interceptor interceptor : interceptors) {
            clientBuilder.addInterceptor(interceptor);
        }
        OkHttpClient httpClient = clientBuilder.build();
        Retrofit.Builder reBuilder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(httpClient);
        for (CallAdapter.Factory callAdapterFactory : callAdapterFactories) {
            reBuilder.addCallAdapterFactory(callAdapterFactory);
        }
        for (Converter.Factory converterFactory : converterFactories) {
            reBuilder.addConverterFactory(converterFactory);
        }
        mRetrofit = reBuilder.build();
        return mRetrofit;
    }

    public static final class Builder {
        private String baseUrl;
        private int readTimeOut;
        private int connectTimeOut;
        private List<Interceptor> interceptors;
        private List<CallAdapter.Factory> callAdapterFactories;
        private List<Converter.Factory> converterFactories;
        private boolean isMock;

        public Builder() {
            interceptors = new ArrayList<>();
            callAdapterFactories = new ArrayList<>();
            converterFactories = new ArrayList<>();
        }

        public Builder baseUrl(String val) {
            baseUrl = val;
            return this;
        }

        public Builder readTimeOut(int val) {
            readTimeOut = val;
            return this;
        }

        public Builder connectTimeOut(int val) {
            connectTimeOut = val;
            return this;
        }

        public Builder interceptors(List<Interceptor> val) {
            interceptors = val;
            return this;
        }

        public Builder callAdapterFactories(List<CallAdapter.Factory> val) {
            callAdapterFactories = val;
            return this;
        }

        public Builder converterFactories(List<Converter.Factory> val) {
            converterFactories = val;
            return this;
        }

        public Builder addInterceptors(Interceptor val) {
            interceptors.add(val);
            return this;
        }

        public Builder addcallAdapterFactory(CallAdapter.Factory factory) {
            callAdapterFactories.add(factory);
            return this;
        }

        public Builder addConverterFactory(Converter.Factory factory) {
            converterFactories.add(factory);
            return this;
        }

        public Builder isMock(boolean val) {
            isMock = val;
            return this;
        }

        public ApiHelper build() {
            if (TextUtils.isEmpty(baseUrl)) {
                throw new NullPointerException("baseUrl is empty!!");
            }

            return new ApiHelper(this);
        }
    }
}
