package com.blanke.commonlib.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.blanke.commonlib.base.mvp.BaseView;
import com.blanke.commonlib.base.mvp.MvpViewHelper;
import com.blanke.commonlib.util.RxLeakUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

public abstract class BaseActivity extends SupportActivity {
    private Unbinder mUnbinder;
    protected RxLeakUtil mRxLeakUtil;
    private MvpViewHelper mvpViewHelper;

    public abstract int getLayout();

    public abstract void initData(Bundle savedInstanceState);

    public abstract void initView(Bundle savedInstanceState);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        mRxLeakUtil = new RxLeakUtil();
        initMvp();
        initView(savedInstanceState);
        initData(savedInstanceState);
    }

    private void initMvp() {
        BaseView baseView = getBaseView();
        if (baseView != null) {
            mvpViewHelper = new MvpViewHelper(baseView, baseView.getPresenter());
            mvpViewHelper.attachView();
        }
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        mUnbinder = ButterKnife.bind(this);
    }

    protected void startActivity(Class activityClass) {
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }

    @Override
    protected FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
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
