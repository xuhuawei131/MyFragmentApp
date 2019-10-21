package com.xuhuawei.myfragmentapp.utils;

import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xuhuawei.myfragmentapp.App;
import com.xuhuawei.myfragmentapp.R;

public class ToastUtil {

    public static Toast showActionResult(int strID) {
        View v = View.inflate(App.getAppContext(),
                R.layout.toast_result_layout, null);
//        UIUtils.dip2px();
//        float w = ScreenSizeUtil.getScreenWidth(App.getAppContext());
//        v.setMinimumWidth((int) (w * 0.4f));
//        v.setMinimumHeight((int) (w * 0.4f));
        TextView tv = (TextView) v.findViewById(R.id.tv_toast);
        tv.setText(App.getAppContext().getString(strID));
        Toast toast = new Toast(App.getAppContext());
        toast.setView(v);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
        return toast;
    }
    public static Toast showActionResult(String str) {
        View v = View.inflate(App.getAppContext(),
                R.layout.common_toast_result_layout, null);
        ImageView iv = (ImageView) v.findViewById(R.id.iv_toast);
        iv.setImageResource( R.drawable.icon_toast);
        TextView tv = (TextView) v.findViewById(R.id.tv_toast);
        tv.setText(str);
        Toast toast = new Toast(App.getAppContext());
        toast.setView(v);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
        return toast;
    }
    public static Toast showActionResult(String str, boolean isOk) {
        View v = View.inflate(App.getAppContext(),
                R.layout.toast_result_layout, null);
//        float w = ScreenSizeUtil.getScreenWidth(Mage.getInstance().getApplication());
//        v.setMinimumWidth((int) (w * 0.4f));
//        v.setMinimumHeight((int) (w * 0.4f));
        ImageView iv = (ImageView) v.findViewById(R.id.iv_toast);
//        iv.setImageResource(isOk ? R.drawable.toast_ok
//                : R.drawable.toast_error);
        iv.setImageResource( R.drawable.icon_toast);
        TextView tv = (TextView) v.findViewById(R.id.tv_toast);
        tv.setText(str);
        Toast toast = new Toast(App.getAppContext());
        toast.setView(v);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
        return toast;
    }

    public static Toast showActionResult(int res, boolean isOk) {
        return showActionResult(App.getAppContext().getString(res), isOk);
    }
}
