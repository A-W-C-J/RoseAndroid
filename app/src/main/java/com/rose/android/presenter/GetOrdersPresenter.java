package com.rose.android.presenter;


import com.rose.android.contract.GetOrdersContract;
import com.rose.android.model.MOrderModel;
import com.rose.android.network.HttpClient;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xiaohuabu on 17/9/12.
 */
public class GetOrdersPresenter extends BasePresenter implements GetOrdersContract.Presenter {
  private GetOrdersContract.View view;
  private MOrderModel orderModel;

  public GetOrdersPresenter(HttpClient httpClient, GetOrdersContract.View view) {
    this.httpClient = httpClient;
    this.view = view;
  }

  @Override
  public void getOrders(Integer pageNo, @Nullable Integer pageSize, @Nullable String status, @Nullable String type,@Nullable String symbol) {
    httpClient.getOrders(pageNo, pageSize, status, type,symbol).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<MOrderModel>() {
          @Override
          public void onSubscribe(Disposable d) {
            disposable = d;
          }

          @Override
          public void onNext(MOrderModel mOrderModel) {
            orderModel = mOrderModel;
            view.showNext(mOrderModel,true,disposable);
          }

          @Override
          public void onError(Throwable e) {
            view.showError(e);
          }

          @Override
          public void onComplete() {
            view.requestCallBack(orderModel);
          }
        });
  }

}
