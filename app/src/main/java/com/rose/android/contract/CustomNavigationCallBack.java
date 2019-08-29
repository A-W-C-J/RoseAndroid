package com.rose.android.contract;

import android.app.Activity;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;

/**
 * Created by wenen on 2017/12/18.
 */

public class CustomNavigationCallBack implements NavigationCallback {
    private Activity activity;
    private boolean needFinish;

    public CustomNavigationCallBack(Activity activity, boolean needFinish) {
        this.activity = activity;
        this.needFinish = needFinish;
    }

    @Override
    public void onFound(Postcard postcard) {

    }

    @Override
    public void onLost(Postcard postcard) {

    }

    @Override
    public void onArrival(Postcard postcard) {
        if (needFinish) {
            activity.finish();
        }
    }

    @Override
    public void onInterrupt(Postcard postcard) {

    }
}
