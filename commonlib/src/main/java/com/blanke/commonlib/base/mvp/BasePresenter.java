package com.blanke.commonlib.base.mvp;

import com.blanke.commonlib.util.RxLeakUtil;

import java.lang.ref.WeakReference;


public class BasePresenter<V extends BaseView> {
    private WeakReference<V> view;
    protected RxLeakUtil mRxLeakUtil;//主要处理调用model层的逻辑防止rx的内存泄露

    public BasePresenter() {
        mRxLeakUtil = new RxLeakUtil();
    }

    public V getView() {
        return view.get();
    }

    public void attachView(V view) {
        if (view != null) {
            this.view = new WeakReference<>(view);
        }
    }

    public void detachView() {
        mRxLeakUtil.clear();
        view.clear();
        view = null;
        mRxLeakUtil = null;
    }
}