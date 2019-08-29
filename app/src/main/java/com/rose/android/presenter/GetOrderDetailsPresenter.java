package com.rose.android.presenter;

import com.rose.android.contract.GetOrderDetailsContract;
import com.rose.android.model.MOrderDetailsModel;
import com.rose.android.network.HttpClient;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xiaohuabu on 17/9/14.
 */
public class GetOrderDetailsPresenter extends BasePresenter implements GetOrderDetailsContract.Presenter {
  private GetOrderDetailsContract.View view;
  private MOrderDetailsModel orderDetailsModel;

  public GetOrderDetailsPresenter(GetOrderDetailsContract.View view, HttpClient httpClient) {
    this.view = view;
    this.httpClient = httpClient;
  }

  @Override
  public void getOrderDetails(int orderId) {
    httpClient.getOrderDetails(orderId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<MOrderDetailsModel>() {
          @Override
          public void onSubscribe(Disposable d) {
            disposable = d;
          }

          @Override
          public void onNext(MOrderDetailsModel mOrderDetailsModel) {
            orderDetailsModel = mOrderDetailsModel;
            view.showNext(mOrderDetailsModel, true, disposable);
          }

          @Override
          public void onError(Throwable e) {
            view.showError(e);
          }

          @Override
          public void onComplete() {
            view.requestCallBack(orderDetailsModel);
          }
        });
  }
}
