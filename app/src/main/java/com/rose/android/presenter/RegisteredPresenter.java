package com.rose.android.presenter;

import com.rose.android.UserInfoManager;
import com.rose.android.contract.RegisteredContract;
import com.rose.android.model.MLoginModel;
import com.rose.android.model.MRegiestedModel;
import com.rose.android.network.HttpClient;
import com.rose.android.viewmodel.LoginVM;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xiaohuabu on 17/10/18.
 */

public class RegisteredPresenter extends BasePresenter implements RegisteredContract.Presenter {
  private RegisteredContract.View loginView;
  private MRegiestedModel regiestedModel;

  public RegisteredPresenter(HttpClient httpClient, RegisteredContract.View loginView) {
    this.httpClient = httpClient;
    this.loginView = loginView;
  }
  @Override
  public void registered(String username, String password, String captcha) {
    httpClient.registered(username, password, captcha).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<MRegiestedModel>() {
          @Override
          public void onSubscribe(Disposable d) {
            disposable = d;
          }

          @Override
          public void onNext(MRegiestedModel mregiestedModel) {
            regiestedModel = mregiestedModel;
            loginView.showNext(mregiestedModel, false, disposable);
          }

          @Override
          public void onError(Throwable e) {
            loginView.showError(e);
          }

          @Override
          public void onComplete() {
            MLoginModel loginModel=new MLoginModel();
            loginModel.setCode(regiestedModel.getCode());
            loginModel.setMsg(regiestedModel.getMsg());
            loginModel.setData(regiestedModel.getData());
            loginView.requestCallBack(regiestedModel);
            UserInfoManager.getInstance().setToken(regiestedModel.getData().getToken());
            LoginVM.getInstance().setLoginModel(loginModel);
            UserInfoManager.getInstance().setLoginStatus(true);
          }
        });
  }
}
