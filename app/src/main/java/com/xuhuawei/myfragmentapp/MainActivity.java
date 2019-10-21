package com.xuhuawei.myfragmentapp;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewStub;

import com.xuhuawei.myfragmentapp.base.BaseUIFragment;
import com.xuhuawei.myfragmentapp.fragment.LoginFragment;

public class MainActivity extends NavigationActivity {
    private View main_login;
    private LoginFragment mLoginFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void showLoginFragment() {
        ViewStub view = findViewById(R.id.view_stub);
        view.inflate();
        main_login=findViewById(R.id.main_login);
        mLoginFragment = BaseUIFragment.newFragment(this, LoginFragment.class);
//        mLoginFragment.setAnimationType(AnimType.ANIM_NONE);
        FragmentManager manager = this.getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.main_login, mLoginFragment);
        transaction.commitAllowingStateLoss();
    }

    private void closeLoginFragment() {
        if (main_login!=null&&mLoginFragment!=null){
            main_login.setVisibility(View.GONE);
            FragmentManager manager = this.getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.remove(mLoginFragment);
            transaction.commitAllowingStateLoss();
            mLoginFragment = null;
        }
    }

}
