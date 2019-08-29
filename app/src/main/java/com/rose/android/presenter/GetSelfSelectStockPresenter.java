package com.rose.android.presenter;

import com.rose.android.contract.GetSelfSelectStockContract;
import com.rose.android.model.MSelfSelectListModel;
import com.rose.android.network.HttpClient;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wenen on 2018/1/4.
 */

public class GetSelfSelectStockPresenter extends BasePresenter implements GetSelfSelectStockContract.Presenter {
    private GetSelfSelectStockContract.View view;
    private MSelfSelectListModel selfSelectListModel;

    public GetSelfSelectStockPresenter(GetSelfSelectStockContract.View view, HttpClient httpClient) {
        this.httpClient = httpClient;
        this.view = view;
    }

    @Override
    public void getSelfSelectStock() {
        httpClient.getSelfSelectList().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MSelfSelectListModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(MSelfSelectListModel mSelfSelectListModel) {
                        view.showNext(mSelfSelectListModel, false, disposable);
                        selfSelectListModel = mSelfSelectListModel;
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showError(e);
                    }

                    @Override
                    public void onComplete() {
                        view.requestCallBack(selfSelectListModel);
                    }
                });
    }
}
