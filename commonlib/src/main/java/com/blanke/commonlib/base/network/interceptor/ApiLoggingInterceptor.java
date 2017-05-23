package com.blanke.commonlib.base.network.interceptor;

import com.blanke.commonlib.CommonConfig;
import com.blanke.commonlib.util.LogUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by blanke on 2017/3/17.
 */

public class ApiLoggingInterceptor implements Interceptor {
    private HttpLoggingInterceptor mHttpLoggingInterceptor;

    public ApiLoggingInterceptor(final String httpLogKey) {
        mHttpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                LogUtil.d(httpLogKey, message);
            }
        });
        mHttpLoggingInterceptor.setLevel(CommonConfig.HTTP_LOG_LEVEL);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        return mHttpLoggingInterceptor.intercept(chain);
    }
}
