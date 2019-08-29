package com.rose.android.presenter;

import com.rose.android.contract.DeleteSelfSelectStockContract;
import com.rose.android.contract.PostSelfSelectStockContract;
import com.rose.android.model.MSelectStockDeleteModel;
import com.rose.android.network.HttpClient;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wenen on 2018/1/4.
 */

public class DeleteSelfSelectStockPresenter extends BasePresenter implements DeleteSelfSelectStockContract.Presenter {
    private DeleteSelfSelectStockContract.View view;
    private MSelectStockDeleteModel selfSelectStockModel;

    public DeleteSelfSelectStockPresenter(HttpClient httpClient, DeleteSelfSelectStockContract.View view) {
        this.httpClient = httpClient;
        this.view = view;
    }

    @Override
    public void deleteSelfSelect(String stockExchange, String stockName, String stockSymbol) {
        httpClient.deleteSelfSelectStock(stockExchange, stockName, stockSymbol).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<MSelectStockDeleteModel>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onNext(MSelectStockDeleteModel mSelfSelectStockModel) {
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
