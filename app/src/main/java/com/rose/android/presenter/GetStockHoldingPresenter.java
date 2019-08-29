package com.rose.android.presenter;

import com.rose.android.contract.GetStockHoldingContract;
import com.rose.android.model.MStockHoldingModel;
import com.rose.android.network.HttpClient;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xiaohuabu on 17/9/21.
 */
public class GetStockHoldingPresenter extends BasePresenter implements GetStockHoldingContract.Presenter {
  private MStockHoldingModel stockHoldingModel;
  private GetStockHoldingContract.View view;

  public GetStockHoldingPresenter(HttpClient httpClient, GetStockHoldingContract.View view) {
    this.httpClient = httpClient;
    this.view = view;
  }

  @Override
  public void getStockHolding(int pageNo, int pageSize, int productOrderId) {
    httpClient.getStockHolding(pageNo, pageSize, productOrderId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<MStockHoldingModel>() {
          @Override
          public void onSubscribe(Disposable d) {
            disposable = d;
          }

          @Override
          public void onNext(MStockHoldingModel mStockHoldingModel) {
            stockHoldingModel = mStockHoldingModel;
            view.showNext(mStockHoldingModel, true, disposable);
          }

          @Override
          public void onError(Throwable e) {
            view.showError(e);
          }

          @Override
          public void onComplete() {
            view.requestCallBack(stockHoldingModel);
          }
        });
  }
}
