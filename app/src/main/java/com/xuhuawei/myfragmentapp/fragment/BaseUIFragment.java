package com.xuhuawei.myfragmentapp.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public abstract class BaseUIFragment extends BaseFragment{
    /**
     * 初始化的工作都放在这里
     *
     * @param savedInstanceState
     */
    protected abstract void init(Bundle savedInstanceState);

    /**
     * 获取控件的布局
     *
     * @return
     */
    protected abstract int getContentViewId();

    /**
     * 初始化控件放在这里
     * 以及设置各种Listener
     */
    protected abstract void findViewByIds();

    /**
     * 注册广播等
     * 一些讲究 尽快注册的工作可以放在这里
     * 如果不需要尽快注册的 尽可能的放在requestService中做
     */
    protected abstract void registerMoudles();

    /**
     * 做一些设置数据 或者请求网络工作
     * 为了防止Fragment加载时候卡顿
     * 延迟了一段时间区调用
     */
    protected abstract void requestService();

    /**
     * 一些回收的工作都在这里
     */
    protected abstract void onMyDestory();

    private Subscription initObservable;
    private List<Toast> cacheToastList;

    private int requestId=-1;

    @Override
    public void onCreateImpl(Bundle savedInstanceState) {
        super.onCreateImpl(savedInstanceState);
        cacheToastList = new ArrayList<>();
        Bundle bundle=getArguments();
        if(bundle!=null){
            requestId=bundle.getInt("requestId",-1);
        }

        init(savedInstanceState);
    }

    @Override
    public View onCreateViewImpl(Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), getContentViewId(), null);
        return view;
    }

    @Override
    public void onViewCreatedImpl(View view, Bundle savedInstanceState) {
        super.onViewCreatedImpl(view, savedInstanceState);
        findViewByIds();
        registerMoudles();
        initObservable = Observable.just("").subscribeOn(Schedulers.io())
                .delay(500, TimeUnit.MICROSECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        LogUtil.v("xhw","throwable content:"+throwable.getLocalizedMessage());
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        if (!isDetached() && getActivity() != null) {
                            requestService();
                        }
                    }
                });
    }

    @Override
    public void onDestroyViewImpl() {
        super.onDestroyViewImpl();
        if (initObservable != null && !initObservable.isUnsubscribed()) {
            initObservable.unsubscribe();
        }
        for (Toast toast : cacheToastList) {
            toast.cancel();
        }
        cacheToastList.clear();
        onMyDestory();
    }

    /**
     * findview工作可以使用他
     *
     * @param resId
     * @return View
     */
    protected View findViewById(int resId) {
        return getContentView().findViewById(resId);
    }

    /**
     * 对每一个要显示Toast做了工作
     * 如果页面关闭 会立即回收 防止页面关闭了 还在显示toast
     * 对于多次点击toast的回收 很有效果
     *
     * @param res
     */
    protected void showToast(String res) {
        Toast toast = Toast.makeText(App.getAppContext(), res, Toast.LENGTH_SHORT);
        toast.show();
        cacheToastList.add(toast);

    }

    protected void showToast(int res) {
        showToast(getString(res));
    }

    /**
     * 显示toast
     * @param res
     * @param isOk
     */
    protected void showToast(String res, boolean isOk) {
        Toast toast = ToastUtil.showActionResult(res, isOk);
        cacheToastList.add(toast);
    }

    protected void showToast(int res, boolean isOk) {
        Toast toast = ToastUtil.showActionResult(res, isOk);
        cacheToastList.add(toast);
    }

    protected void showPushFragmentForResult(BaseUIFragment<?> fragment, int request){
        fragment.setAnimationType(AnimType.RIGHT_TO_LEFT);
        showFragmentForResult(fragment,request);
    }
    protected void showPopFragmentForResult(BaseUIFragment<?> fragment,int request){
        fragment.setAnimationType(AnimType.BOTTOM_TO_TOP);
        showFragmentForResult(fragment,request);
    }
    protected void showFragmentForResult(BaseUIFragment<?> fragment,int requestId){
        Bundle bundle=fragment.getArguments();
        if (bundle==null){
            bundle=new Bundle();
            bundle.putInt("requestId",requestId);
        }
        fragment.setArguments(bundle);

        if (fragment instanceof MyBaseUIFragment){
            ((MyBaseUIFragment)fragment).lastFragment=this;
        }
        super.showFragment(fragment);
    }

    protected void OnFragmentResult(Bundle bundle,int request,int result){

    }

    public void setResult(Bundle bundle,int result){
        if (bundle==null){
            bundle=new Bundle();
        }
        if (lastFragment!=null){
            lastFragment.OnFragmentResult(bundle,requestId,result);
        }
    }
}
