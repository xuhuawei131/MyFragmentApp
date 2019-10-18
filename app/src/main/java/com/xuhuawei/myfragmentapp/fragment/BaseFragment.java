package com.xuhuawei.myfragmentapp.fragment;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;

public class BaseFragment extends Fragment {
    private Activity mAttachActivity;
    private BaseFragment mParentFragment;

    public void onNewIntent(Intent intent) {
    }
    public BaseFragment setParent(Activity activity, BaseFragment parentFragment) {
        this.mAttachActivity = activity;
        this.mParentFragment = parentFragment;
        return this;
    }
}
