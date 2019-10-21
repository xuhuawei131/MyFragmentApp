package com.xuhuawei.myfragmentapp.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.xuhuawei.myfragmentapp.NavigationActivity;

public class BaseFragment extends Fragment {
    private Activity mAttachActivity;


    public void onNewIntent(Intent intent) {
    }

    public void showFragment(BaseFragment fragment) {
        NavigationActivity  activity= (NavigationActivity) getActivity();
        if (activity != null) {
            activity.addSubFragment(fragment);
        }
    }

    public void setVisibleToUser(boolean visible) {

    }
    /*
     * 初始化新的Fragment
     */
    public static <T extends BaseUIFragment> T newFragment(Context context, Class<?> cls) {
        T fragment = (T) Fragment.instantiate(context, cls.getName());
        return fragment;
    }
}
