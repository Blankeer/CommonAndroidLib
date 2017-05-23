package com.blanke.commonlib.util;

import android.text.TextUtils;

import com.blanke.commonlib.CommonConfig;
import com.orhanobut.logger.Logger;

/**
 * Created by blanke on 2017/3/17.
 */

public class LogUtil {
    private static boolean isShow = CommonConfig.SHOW_LOG;

    public static void setDefaultTag(String tag) {
        Logger.init(tag).methodCount(CommonConfig.SHOW_LOG_METHOD_COUNT);
    }

    public static void showLog(boolean isShow) {
        LogUtil.isShow = isShow;
    }

    private static String formatTagMsg(String tag, String msg) {
        if (TextUtils.isEmpty(tag)) {
            return msg;
        }
        return tag + " : " + msg;
    }

    private static String convertString(Object obj) {
        if (obj == null) {
            return "[empty string]";
        }
        return obj.toString();
    }

    public static void d(Object msg) {
        if (isShow) {
            Logger.d(msg);
        }
    }

    public static void d(final String tag, Object message) {
        if (isShow) {
            Logger.d(formatTagMsg(tag, convertString(message)));
        }
    }

    public static void e(Object msg) {
        if (isShow) {
            if (msg instanceof Throwable) {
                Throwable t = (Throwable) msg;
                Logger.e(t, "");
            } else {
                Logger.e(convertString(msg));
            }
        }
    }

    public static void e(final String tag, Object message) {
        if (isShow) {
            Logger.e(tag, message);
        }
    }

}
