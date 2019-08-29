package com.rose.android.presenter;

import com.rose.android.contract.BaseContract;
import com.rose.android.network.HttpClient;

import io.reactivex.disposables.Disposable;

/**
 * Created by wenen on 2017/12/18.
 */

public class BasePresenter implements BaseContract.Presenter {
    protected HttpClient httpClient;
    protected Disposable disposable;

    @Override
    public void dispose() {
        if (disposable != null) {
            disposable.dispose();
        }
    }
}
