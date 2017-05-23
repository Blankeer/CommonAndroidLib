package com.blanke.commonandroidlib.mvp;

import com.blanke.commonlib.base.mvp.BasePresenter;
import com.blanke.commonlib.base.mvp.BaseView;

/**
 * Created by blanke on 2017/5/23.
 */

public interface MainContract {

    interface MainView extends BaseView<MainPresenter> {
        void onLoginResult(String info);
    }

    abstract class MainPresenter extends BasePresenter<MainView> {
        public abstract void login(String username, String pwd);
    }

}
