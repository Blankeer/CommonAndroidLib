package com.blanke.commonlib;

import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by blanke on 2017/5/23.
 */

public class CommonConfig {
    public final static boolean SHOW_LOG = true;
    public final static int SHOW_LOG_METHOD_COUNT = 3;
    public final static String KEY_HTTP_LOG = "OkHttp";
    public final static String KEY_HTTP_HEAD_TOKEN = "token";
    public final static String HTTP_BASE_URL = "https://www.easy-mock.com/mock/590952b97a878d73716e86c6/example/";
    public final static int HTTP_CONNECT_TIMEOUT = 3000;
    public final static int HTTP_READ_TIMEOUT = 3000;
    public final static boolean HTTP_IS_MOCK = false;
    public final static HttpLoggingInterceptor.Level HTTP_LOG_LEVEL = HttpLoggingInterceptor.Level.BASIC;


}
