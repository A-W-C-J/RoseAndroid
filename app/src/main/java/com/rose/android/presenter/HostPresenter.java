package com.rose.android.presenter;

import com.rose.android.contract.HostContract;
import com.rose.android.model.MHostModel;
import com.rose.android.network.HttpClient;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wenen on 2017/11/1.
 */

public class HostPresenter extends BasePresenter implements HostContract.Presenter {
  private HostContract.View view;
  private MHostModel hostModel;

  public HostPresenter(HttpClient httpClient, HostContract.View view) {
    this.httpClient = httpClient;
    this.view = view;
  }

  @Override
  public void getHost() {
    httpClient.getHostTxt().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
        .subscribe(new Observer<MHostModel>() {
          @Override
          public void onSubscribe(Disposable d) {
            disposable = d;
          }

          @Override
          public void onNext(MHostModel mHostModel) {
            hostModel = mHostModel;
          }

          @Override
          public void onError(Throwable e) {
          }

          @Override
          public void onComplete() {
            view.requestCallBack(hostModel);
          }
        });
  }
}
