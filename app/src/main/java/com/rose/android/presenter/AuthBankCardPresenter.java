package com.rose.android.presenter;

import com.rose.android.contract.AuthBankCardContract;
import com.rose.android.model.MAuthBankCardModel;
import com.rose.android.network.HttpClient;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xiaohuabu on 17/10/9.
 */

public class AuthBankCardPresenter extends BasePresenter implements AuthBankCardContract.Presenter {
  private MAuthBankCardModel authBankCardModel;
  private AuthBankCardContract.View view;

  public AuthBankCardPresenter(AuthBankCardContract.View view, HttpClient httpClient) {
    this.view = view;
    this.httpClient = httpClient;
  }

  @Override
  public void postBankCard(String bankCardNo, String name, String phone) {
    httpClient.postBankCard(bankCardNo, name, phone).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<MAuthBankCardModel>() {
          @Override
          public void onSubscribe(Disposable d) {
            disposable = d;
          }

          @Override
          public void onNext(MAuthBankCardModel mAuthBankCardModel) {
            authBankCardModel=mAuthBankCardModel;
            view.showNext(mAuthBankCardModel,true,disposable);
          }

          @Override
          public void onError(Throwable e) {
            view.showError(e);
          }

          @Override
          public void onComplete() {
            view.requestCallBack(authBankCardModel);
          }
        });
  }
}
