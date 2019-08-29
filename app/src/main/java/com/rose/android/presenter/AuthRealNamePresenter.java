package com.rose.android.presenter;

import com.rose.android.contract.AuthRealNameContract;
import com.rose.android.model.MAuthRealNameModel;
import com.rose.android.network.HttpClient;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xiaohuabu on 17/9/30.
 */

public class AuthRealNamePresenter extends BasePresenter implements AuthRealNameContract.Presenter {
  private AuthRealNameContract.View view;
  private MAuthRealNameModel authRealNameModel;

  public AuthRealNamePresenter(AuthRealNameContract.View view, HttpClient httpClient) {
    this.view = view;
    this.httpClient = httpClient;
  }

  @Override
  public void postRealName(String realName, String idCardNo) {
    httpClient.postRealName(realName, idCardNo).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<MAuthRealNameModel>() {
          @Override
          public void onSubscribe(Disposable d) {
            disposable = d;
          }

          @Override
          public void onNext(MAuthRealNameModel mAuthRealNameModel) {
            authRealNameModel = mAuthRealNameModel;
            view.showNext(mAuthRealNameModel, true, disposable);
          }

          @Override
          public void onError(Throwable e) {
            view.showError(e);
          }

          @Override
          public void onComplete() {
            view.requestCallBack(authRealNameModel);
          }
        });
  }

}
