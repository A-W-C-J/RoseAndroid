package com.rose.android.presenter;

import com.rose.android.contract.AuthPhoneContract;
import com.rose.android.model.MAuthPhoneModel;
import com.rose.android.network.HttpClient;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xiaohuabu on 17/10/13.
 */
public class AuthPhonePresenter extends BasePresenter implements AuthPhoneContract.Presenter {
  private MAuthPhoneModel authPhoneModel;
  private AuthPhoneContract.View view;

  public AuthPhonePresenter(HttpClient httpClient, AuthPhoneContract.View view) {
    this.httpClient = httpClient;
    this.view = view;
  }

  @Override
  public void pathPhone(String phone, String password, String captcha) {
    httpClient.patchPhone(phone, password, captcha).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<MAuthPhoneModel>() {
          @Override
          public void onSubscribe(Disposable d) {
            disposable = d;
          }

          @Override
          public void onNext(MAuthPhoneModel mAuthPhoneModel) {
            authPhoneModel = mAuthPhoneModel;
            view.showNext(mAuthPhoneModel, true, disposable);
          }

          @Override
          public void onError(Throwable e) {
            view.showError(e);
          }

          @Override
          public void onComplete() {
            view.requestCallBack(authPhoneModel);
          }
        });
  }
}
