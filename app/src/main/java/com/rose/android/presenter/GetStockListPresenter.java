package com.rose.android.presenter;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.rose.android.contract.GetStockListContract;
import com.rose.android.model.MStockListModel;
import com.rose.android.network.HttpClient;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wenen on 2018/1/3.
 */

public class GetStockListPresenter extends BasePresenter implements GetStockListContract.Presenter {
    private GetStockListContract.View view;
    private MStockListModel stockListModel;

    public GetStockListPresenter(GetStockListContract.View view, HttpClient httpClient) {
        this.view = view;
        this.httpClient = httpClient;
    }

    @Override
    public void getStockList(String key, @NonNull Integer pageNo, @Nullable Integer pageSize) {
        httpClient.getStockList(key, pageNo, pageSize).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new Observer<MStockListModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(MStockListModel mStockListModel) {
                        stockListModel = mStockListModel;
                        view.showNext(stockListModel, false, disposable);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showError(e);
                    }

                    @Override
                    public void onComplete() {
                        view.requestCallBack(stockListModel);
                    }
                });
    }
}
