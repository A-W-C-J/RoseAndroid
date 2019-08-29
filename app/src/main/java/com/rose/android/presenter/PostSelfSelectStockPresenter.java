package com.rose.android.presenter;

import com.rose.android.contract.PostSelfSelectStockContract;
import com.rose.android.model.MSelfSelectStockModel;
import com.rose.android.network.HttpClient;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wenen on 2018/1/4.
 */

public class PostSelfSelectStockPresenter extends BasePresenter implements PostSelfSelectStockContract.Presenter {
    private PostSelfSelectStockContract.View view;
    private MSelfSelectStockModel selfSelectStockModel;

    public PostSelfSelectStockPresenter(HttpClient httpClient, PostSelfSelectStockContract.View view) {
        this.httpClient = httpClient;
        this.view = view;
    }

    @Override
    public void postSelfSelect(String stockExchange, String stockName, String stockSymbol) {
        httpClient.postSelfSelectStock(stockExchange, stockName, stockSymbol).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<MSelfSelectStockModel>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onNext(MSelfSelectStockModel mSelfSelectStockModel) {
                view.showNext(mSelfSelectStockModel, true, disposable);
                selfSelectStockModel = mSelfSelectStockModel;
            }

            @Override
            public void onError(Throwable e) {
                view.showError(e);
            }

            @Override
            public void onComplete() {
                view.requestCallBack(selfSelectStockModel);
            }
        });
    }
}
