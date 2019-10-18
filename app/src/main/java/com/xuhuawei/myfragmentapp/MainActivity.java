package com.xuhuawei.myfragmentapp;

import androidx.appcompat.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.xuhuawei.myfragmentapp.fragment.BaseFragment;

import java.util.List;

public class MainActivity extends FragmentActivity {

    private BaseFragment mLastFragment;
    private int x;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    /*
     * 显示主框架
     * 这里只有两个 一个是登录注册页面 一个是MainFragment页面
     */
    public void showFragment(BaseFragment fragment) {
        this.mLastFragment = fragment;
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.main_container, fragment);
        transaction.commitAllowingStateLoss();
    }

    public void addSubFragment(BaseFragment fragment) {
        if (!this.isFinishing()) {
            BaseFragment lastFragment = this.mLastFragment;
            if (lastFragment != null && this.isSameFragment(lastFragment, fragment)) {
                Intent intent = new Intent();
                if (fragment.getArguments() != null) {
                    intent.putExtras(fragment.getArguments());
                }

                lastFragment.onNewIntent(intent);
            } else {
                if (fragment != null && this.mLastFragment != null) {
                    (fragment).setParent(this, this.mLastFragment);
                }

                this.mLastFragment = fragment;
                FragmentTransaction transaction = this.getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.main_container, fragment);
                transaction.commitAllowingStateLoss();
//                if (lastFragment != null) {
//                    lastFragment.setVisibleToUser(false);
//                }
//
//                fragment.setVisibleToUser(true);
            }
        }
    }

    private boolean isSameFragment(Fragment topFragment, BaseFragment currentFragment) {
        if (topFragment != null && currentFragment != null) {
            boolean isSameType = topFragment.getClass().getName().equals(currentFragment.getClass().getName());
            if (topFragment.getArguments() != null && currentFragment.getArguments() != null) {
                boolean isSameArgument = topFragment.getArguments().toString().equals(currentFragment.getArguments().toString());
                return isSameType && isSameArgument;
            } else {
                return topFragment.getArguments() == null && currentFragment.getArguments() == null ? isSameType : false;
            }
        } else {
            return false;
        }
    }

    public void removeSubFragment(BaseFragment fragment) {
        if (!this.isFinishing()) {
            FragmentManager manager = this.getSupportFragmentManager();
            List<Fragment> children = manager.getFragments();
            if (children != null && !children.isEmpty()) {
                Fragment topFragment = (Fragment)children.get(children.size() - 1);
                if (fragment == topFragment) {
                    if (children.size() > 1) {
                        Fragment nextFragment = (Fragment)children.get(children.size() - 2);
                        if (nextFragment instanceof BaseFragment) {
//                            ((BaseFragment)nextFragment).setVisibleToUser(true);
                            this.mLastFragment = (BaseFragment)nextFragment;
                        }
                    }
                } else if (topFragment instanceof BaseFragment) {
//                    ((BaseFragment)topFragment).setVisibleToUser(true);
                    this.mLastFragment = (BaseFragment)topFragment;
                }
            } else {
                this.mLastFragment = null;
            }

            FragmentTransaction transaction = manager.beginTransaction();
            transaction.remove(fragment);
            transaction.commitAllowingStateLoss();
        }
    }
}
