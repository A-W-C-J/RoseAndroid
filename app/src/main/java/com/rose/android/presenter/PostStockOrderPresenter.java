package com.rose.android.presenter;

import com.rose.android.contract.PostStockOrderContract;
import com.rose.android.model.MStockOrderModel;
import com.rose.android.network.HttpClient;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xiaohuabu on 17/9/21.
 */
public class PostStockOrderPresenter extends BasePresenter implements PostStockOrderContract.Presenter {
  private PostStockOrderContract.View view;
  private MStockOrderModel stockOrderModel;

  public PostStockOrderPresenter(PostStockOrderContract.View view, HttpClient httpClient) {
    this.view = view;
    this.httpClient = httpClient;
  }

  @Override
  public void postStockOrder(int totalQuantity, String symbole, String action, long limitPrice, int productOrderId) {
    httpClient.postStockOrder(totalQuantity, symbole, action, limitPrice, productOrderId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<MStockOrderModel>() {
          @Override
          public void onSubscribe(Disposable d) {
            disposable = d;
          }

          @Override
          public void onNext(MStockOrderModel mStockOrderModel) {
            stockOrderModel = mStockOrderModel;
            view.showNext(mStockOrderModel, true, disposable);
          }

          @Override
          public void onError(Throwable e) {
            view.showError(e);
          }

          @Override
          public void onComplete() {
            view.requestCallBack(stockOrderModel);
          }
        });
  }

}
