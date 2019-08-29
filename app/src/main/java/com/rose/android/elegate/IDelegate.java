package com.rose.android.elegate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by xiaohuabu on 17/8/29.
 */

public interface IDelegate {
    void create(int layoutId, ViewGroup v, Bundle b);

    void create(int layoutId);

    void create(int layoutId, LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState);

    View getRootView();

    void showLoadDialog();

    void dismissLoadDialog();
}
