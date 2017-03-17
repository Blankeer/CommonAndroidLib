package com.blanke.commonlib.base.mvp;

/**
 * Created by blanke on 2017/3/17.
 * mvp view层辅助类，为了避免需要继承BaseMvpActivity
 */
public class MvpViewHelper<V extends BaseView, P extends BasePresenter> {
    private V view;
    private P presenter;

    public MvpViewHelper(V view, P presenter) {
        this.view = view;
        this.presenter = presenter;
    }

    /**
     * 在Activity#onCreate()或fragment方法调用
     */
    public void attachView() {
        presenter.attachView(view);
    }

    public void detachView() {
        presenter.detachView();
    }
}
