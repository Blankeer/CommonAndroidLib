package com.blanke.commonlib.base.mvp;

public interface BaseView<T extends BasePresenter> {
    T getPresenter();
}
