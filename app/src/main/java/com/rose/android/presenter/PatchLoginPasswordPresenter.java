package com.rose.android.presenter;

import com.rose.android.contract.PathLoginPasswordContract;
import com.rose.android.model.MLoginPasswordModel;
import com.rose.android.network.HttpClient;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xiaohuabu on 17/10/13.
 */

public class PatchLoginPasswordPresenter extends BasePresenter implements PathLoginPasswordContract.Presenter {
  private MLoginPasswordModel loginPasswordModel;
  private PathLoginPasswordContract.View view;

  public PatchLoginPasswordPresenter(HttpClient httpClient, PathLoginPasswordContract.View view) {
    this.httpClient = httpClient;
    this.view = view;
  }

  @Override
  public void patchPassword(String password, String oldPassword) {
    httpClient.patchLoginPassword(password, oldPassword).observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io()).subscribe(new Observer<MLoginPasswordModel>() {
      @Override
      public void onSubscribe(Disposable d) {
        disposable = d;
      }

      @Override
      public void onNext(MLoginPasswordModel mLoginPasswordModel) {
        loginPasswordModel = mLoginPasswordModel;
        view.showNext(mLoginPasswordModel, true, disposable);
      }

      @Override
      public void onError(Throwable e) {
        view.showError(e);
      }

      @Override
      public void onComplete() {
        view.requestCallBack(loginPasswordModel);
      }
    });
  }

}
