package com.rose.android.presenter;

import com.rose.android.contract.ShoumiPayContract;
import com.rose.android.model.MShoumiModel;
import com.rose.android.network.HttpClient;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wenen on 2017/12/12.
 */

public class ShoumiPayPresenter extends BasePresenter implements ShoumiPayContract.Presenter {
  private ShoumiPayContract.View view;
  private MShoumiModel shoumiModel;

  public ShoumiPayPresenter(ShoumiPayContract.View view, HttpClient httpClient) {
    this.view = view;
    this.httpClient = httpClient;
  }

  @Override
  public void postShoumiPay(Long amount, Integer source) {
    httpClient.postShoumiPay(amount, source).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<MShoumiModel>() {
      @Override
      public void onSubscribe(Disposable d) {
        disposable = d;
      }

      @Override
      public void onNext(MShoumiModel mShoumiModel) {
        shoumiModel = mShoumiModel;
        view.showNext(mShoumiModel, true, disposable);
      }

      @Override
      public void onError(Throwable e) {
        view.showError(e);
      }

      @Override
      public void onComplete() {
        view.requestCallBack(shoumiModel);
      }
    });
  }

}
