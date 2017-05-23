package com.blanke.commonlib.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blanke.commonlib.base.mvp.BaseView;
import com.blanke.commonlib.base.mvp.MvpViewHelper;
import com.blanke.commonlib.util.RxLeakUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;


public abstract class BaseFragment extends SupportFragment {
    private Unbinder unBinder;
    protected BaseActivity mBaseActivity;
    protected RxLeakUtil mRxLeakUtil;//销毁时取消订阅，防止leak
    private MvpViewHelper mvpViewHelper;

    public abstract int getLayout();

    public abstract void initView(View view, Bundle savedInstanceState);

    public abstract void initData(View view, Bundle savedInstanceState);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayout(), container, false);
        unBinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRxLeakUtil = new RxLeakUtil();
        initView(view, savedInstanceState);
        initData(view, savedInstanceState);
        if (_mActivity instanceof BaseActivity) {
            mBaseActivity = (BaseActivity) _mActivity;
        }
        initMvp();
    }

    private void initMvp() {
        BaseView baseView = getBaseView();
        if (baseView != null) {
            mvpViewHelper = new MvpViewHelper(baseView, baseView.getPresenter());
            mvpViewHelper.attachView();
        }
    }

    protected void startActivity(Class clazz) {
        Intent intent = new Intent(_mActivity, clazz);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unBinder.unbind();
        mRxLeakUtil.clear();
        if (mvpViewHelper != null) {
            mvpViewHelper.detachView();
        }
    }

    private BaseView getBaseView() {
        if (this instanceof BaseView) {
            return (BaseView) this;
        }
        return null;
    }
}
