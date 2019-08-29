package com.rose.android.presenter;

import com.rose.android.contract.PutZoomContract;
import com.rose.android.model.MZoomModel;
import com.rose.android.network.HttpClient;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wenen on 2017/11/15.
 */

public class PutZoomPresenter extends BasePresenter implements PutZoomContract.Presenter {
  private PutZoomContract.View view;
  private MZoomModel zoomModel;

  public PutZoomPresenter(PutZoomContract.View view, HttpClient httpClient) {
    this.view = view;
    this.httpClient = httpClient;
  }

  @Override
  public void putZoom(Long addLoan, String productOrderId) {
    httpClient.putZoom(addLoan, productOrderId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<MZoomModel>() {
          @Override
          public void onSubscribe(Disposable d) {
            disposable = d;
          }

          @Override
          public void onNext(MZoomModel mZoomModel) {
            zoomModel = mZoomModel;
            view.showNext(zoomModel, true, disposable);
          }

          @Override
          public void onError(Throwable e) {
            view.showError(e);
          }

          @Override
          public void onComplete() {
            view.requestCallBack(zoomModel);
          }
        });
  }
}
