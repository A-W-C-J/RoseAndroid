package com.rose.android.presenter;

import com.rose.android.contract.GetZoomInfoContract;
import com.rose.android.model.MZoomInfoModel;
import com.rose.android.network.HttpClient;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wenen on 2017/11/15.
 */
public class GetZoomInfoPresenter extends BasePresenter implements GetZoomInfoContract.Presenter {
  private GetZoomInfoContract.View view;
  private MZoomInfoModel zoomInfoModel;

  public GetZoomInfoPresenter(GetZoomInfoContract.View view, HttpClient httpClient) {
    this.view = view;
    this.httpClient = httpClient;
  }

  @Override
  public void getZoomInfo(Long addLoan, String productOrderId) {
    httpClient.getZoomInfo(addLoan, productOrderId).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<MZoomInfoModel>() {
          @Override
          public void onSubscribe(Disposable d) {
            disposable = d;
          }

          @Override
          public void onNext(MZoomInfoModel mZoomInfoModel) {
            zoomInfoModel = mZoomInfoModel;
            view.showNext(zoomInfoModel, true, disposable);
          }

          @Override
          public void onError(Throwable e) {
            view.showError(e);
          }

          @Override
          public void onComplete() {
            view.requestCallBack(zoomInfoModel);
          }
        });
  }
}
