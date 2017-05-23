package com.blanke.commonlib.base.network;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class ApiTransformers {
    static final ObservableTransformer schedulersTransformer = new ObservableTransformer() {
        @Override
        public ObservableSource apply(@NonNull Observable upstream) {
            return upstream.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }
    };

    //统一错误处理
    static final ObservableTransformer errorTransformer = new ObservableTransformer() {
        @Override
        public ObservableSource apply(@NonNull Observable upstream) {
            return upstream.onErrorResumeNext(new Function<Throwable, ObservableSource>() {
                @Override
                public ObservableSource apply(@NonNull Throwable throwable) throws Exception {
                    return Observable.error(ApiException.handleException(throwable));
                }
            });
        }
    };

    public static <T> ObservableTransformer applySchedulers() {
        return schedulersTransformer;
    }

    public static <T> ObservableTransformer applyError() {
        return errorTransformer;
    }
}