package com.rose.android.presenter;

import com.rose.android.contract.GetAuthCodeContract;
import com.rose.android.model.MAuthCodeModel;
import com.rose.android.network.HttpClient;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xiaohuabu on 17/10/18.
 */
public class GetAuthCodePresenter extends BasePresenter implements GetAuthCodeContract.Presenter {
  private GetAuthCodeContract.View loginView;
  private MAuthCodeModel authCode;

  public GetAuthCodePresenter(HttpClient httpClient, GetAuthCodeContract.View loginView) {
    this.httpClient = httpClient;
    this.loginView = loginView;

  }


  @Override
  public void getAuthCode(String phone, String type) {
    httpClient.getAuthCode(phone, type).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<MAuthCodeModel>() {
          @Override
          public void onSubscribe(Disposable d) {
            disposable = d;
          }

          @Override
          public void onNext(MAuthCodeModel mauthCodeModel) {
            authCode = mauthCodeModel;
            loginView.showNext(mauthCodeModel, true, disposable);
          }

          @Override
          public void onError(Throwable e) {
            loginView.showError(e.toString(), null);
          }

          @Override
          public void onComplete() {
            loginView.requestCallBack(authCode);
          }
        });
  }

}
