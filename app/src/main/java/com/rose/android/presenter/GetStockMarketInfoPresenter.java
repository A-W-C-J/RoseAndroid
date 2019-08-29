package com.rose.android.presenter;

import com.rose.android.contract.GetStockMarketInfoContract;
import com.rose.android.model.MStockMarketInfoModel;
import com.rose.android.network.HttpClient;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xiaohuabu on 17/10/18.
 */
public class GetStockMarketInfoPresenter extends BasePresenter implements GetStockMarketInfoContract.Presenter {
  private GetStockMarketInfoContract.View view;
  private MStockMarketInfoModel stockMarketInfoModel;

  public GetStockMarketInfoPresenter(GetStockMarketInfoContract.View view, HttpClient httpClient) {
    this.view = view;
    this.httpClient = httpClient;
  }
  @Override
  public void getStockMarketInfo(String symbol, Integer productOrderId) {
    httpClient.getStockMarketInfo(symbol, productOrderId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<MStockMarketInfoModel>() {
          @Override
          public void onSubscribe(Disposable d) {
            disposable = d;
          }

          @Override
          public void onNext(MStockMarketInfoModel mStockMarketInfoModel) {
            stockMarketInfoModel = mStockMarketInfoModel;
            view.showNext(mStockMarketInfoModel, true, disposable);
          }

          @Override
          public void onError(Throwable e) {
            view.showError(e);
          }

          @Override
          public void onComplete() {
            view.requestCallBack(stockMarketInfoModel);
          }
        });
  }
}
