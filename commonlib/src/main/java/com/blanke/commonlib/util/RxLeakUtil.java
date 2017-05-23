package com.blanke.commonlib.util;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by blanke on 2017/3/17.
 * rx的内存泄露处理工具，当前主要是 CompositeDisposable 来处理，需要手动调用clear
 */

public class RxLeakUtil {
    private CompositeDisposable mCompositeDisposable;//保存subscription,销毁时取消订阅，防止leak

    public RxLeakUtil() {
        mCompositeDisposable = new CompositeDisposable();
    }

    public Disposable add(Disposable disposable) {
        mCompositeDisposable.add(disposable);
        return disposable;
    }

    public void clear() {
        mCompositeDisposable.dispose();
    }
}
