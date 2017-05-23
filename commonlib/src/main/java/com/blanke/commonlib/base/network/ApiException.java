package com.blanke.commonlib.base.network;

import android.net.ParseException;

import com.google.gson.JsonParseException;
import com.orhanobut.logger.Logger;

import org.json.JSONException;

import java.net.ConnectException;

import retrofit2.HttpException;


public class ApiException extends RuntimeException {
    public static final int NO_NETWORK = -1;
    public static final int UNKNOWN_ERROR = 0;
    public static final int TOKEN_FAIL = 3;
    public static final int JSON_ERROR = 4;
    public static final int CONNECT_ERROR = 5;
    public static final int LOGIC_TIMEOUT = 6;
    public static final int HTTP_UNAUTHORIZED_401 = 401;
    public static final int HTTP_FORBIDDEN_403 = 403;
    public static final int HTTP_NOT_FOUND_404 = 404;
    public static final int HTTP_REQUEST_TIMEOUT_408 = 408;
    public static final int HTTP_INTERNAL_SERVER_ERROR_500 = 500;
    public static final int HTTP_BAD_GATEWAY_502 = 502;
    public static final int HTTP_SERVICE_UNAVAILABLE_503 = 503;
    public static final int HTTP_GATEWAY_TIMEOUT_504 = 504;

    private int resultCode;

    public ApiException(int resultCode) {
        this(getApiExceptionMsg(resultCode));
        this.resultCode = resultCode;
    }

    public ApiException(String message) {
        super(message);
    }

    //统一处理所有http异常
    public static ApiException handleException(Throwable e) {
        Logger.e("api exception," + e.getMessage());
        if (e instanceof HttpException) {
            HttpException exception = (HttpException) e;
            int code = exception.code();
            if (code == HTTP_UNAUTHORIZED_401) {//token失效
//                EventBus.getDefault().post(new TokenInvalidEvent());
            }
            return new ApiException(code);
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            return new ApiException(JSON_ERROR);
        } else if (e instanceof ConnectException) {
            return new ApiException(CONNECT_ERROR);
        } else if (e instanceof ApiException) {
            return (ApiException) e;
        } else {
            return new ApiException(UNKNOWN_ERROR);
        }
    }

    private static String getApiExceptionMsg(int code) {
        String msg = "网络错误";
        switch (code) {
            case HTTP_UNAUTHORIZED_401:
                msg = "token失效";
                break;
            case NO_NETWORK:
                msg = "无网络连接";
                break;
            case CONNECT_ERROR:
                msg = "网络连接失败";
                break;
        }
        return msg;
    }

    public int getResultCode() {
        return resultCode;
    }

    @Override
    public String toString() {
        return resultCode + ":" + getMessage();
    }
}
