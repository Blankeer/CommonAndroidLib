package com.blanke.commonlib.base.network.interceptor;

import android.text.TextUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by blanke on 2017/3/17.
 */

public class TokenInterceptor implements Interceptor {
    private String HEAD_TOKEN;
    private String token;

    public TokenInterceptor(String HEAD_TOKEN) {
        this.HEAD_TOKEN = HEAD_TOKEN;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (!TextUtils.isEmpty(token)) {
            request = request.newBuilder().header(HEAD_TOKEN, token).build();
        }
        return chain.proceed(request);
    }
}
