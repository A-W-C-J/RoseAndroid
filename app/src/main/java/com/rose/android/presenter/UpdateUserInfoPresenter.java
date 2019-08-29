package com.rose.android.presenter;

import com.rose.android.contract.UpdateUserInfoContract;
import com.rose.android.model.MUpdateUserInfoModel;
import com.rose.android.network.HttpClient;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xiaohuabu on 17/10/18.
 */

public class UpdateUserInfoPresenter extends BasePresenter implements UpdateUserInfoContract.Presenter{
  private UpdateUserInfoContract.View loginView;
  private MUpdateUserInfoModel updateUserInfoModel;

  public UpdateUserInfoPresenter(HttpClient httpClient, UpdateUserInfoContract.View loginView) {
    this.httpClient = httpClient;
    this.loginView = loginView;

  }
  @Override
  public void updateUserInfo(String username, String password, String captcha, String nickname) {
    httpClient.updateUserInfo(username, password, captcha, nickname).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
        .subscribe(new Observer<MUpdateUserInfoModel>() {
          @Override
          public void onSubscribe(Disposable d) {
            disposable = d;
          }

          @Override
          public void onNext(MUpdateUserInfoModel mUpdateUserInfoModel) {
            updateUserInfoModel = mUpdateUserInfoModel;
            loginView.showNext(mUpdateUserInfoModel, true, disposable);
          }

          @Override
          public void onError(Throwable e) {
            loginView.showError(e);
          }

          @Override
          public void onComplete() {
            loginView.requestCallBack(updateUserInfoModel);
          }
        });
  }
}
