package com.blanke.commonlib.util;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by blanke on 2017/3/17.
 * rx的内存泄露处理工具，当前主要是CompositeSubscription来处理，需要手动调用clear
 */

public class RxLeakUtil {
    private CompositeSubscription mCompositeSubscription;//保存subscription,销毁时取消订阅，防止leak

    public RxLeakUtil() {
        mCompositeSubscription = new CompositeSubscription();
    }

    public Subscription add(Subscription subScription) {
        mCompositeSubscription.add(subScription);
        return subScription;
    }

    public void clear() {
        mCompositeSubscription.unsubscribe();
    }
}
