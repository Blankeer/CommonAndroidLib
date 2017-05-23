package com.blanke.commonandroidlib;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.blanke.commonandroidlib.mvp.MainContract;
import com.blanke.commonandroidlib.mvp.MainPresenter;
import com.blanke.commonlib.base.BaseActivity;

public class MainActivity extends BaseActivity
        implements MainContract.MainView {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }

    };
    private MainPresenter mainPresenter;

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mTextMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().login("1", "2");
            }
        });

    }

    @Override
    public MainContract.MainPresenter getPresenter() {
        if (mainPresenter == null) {
            mainPresenter = new MainPresenter();
        }
        return mainPresenter;
    }

    @Override
    public void onLoginResult(String info) {
        mTextMessage.setText(info);
    }
}
