package com.rose.android.presenter;


import com.rose.android.UserInfoManager;
import com.rose.android.contract.LoginContract;
import com.rose.android.model.MLoginModel;
import com.rose.android.network.HttpClient;
import com.rose.android.viewmodel.LoginVM;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xiaohuabu on 17/9/5.
 */

public class LoginPresenter extends BasePresenter implements LoginContract.Presenter {
    private LoginContract.View loginView;
    private MLoginModel loginModel;

    public LoginPresenter(HttpClient httpClient, LoginContract.View loginView) {
        this.httpClient = httpClient;
        this.loginView = loginView;

    }

    @Override
    public void login(String username, String password) {
        httpClient.login(username, password).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<MLoginModel>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onNext(MLoginModel MLoginModelApiResponse) {
                loginModel = MLoginModelApiResponse;
                loginView.showNext(MLoginModelApiResponse, false, disposable);
            }

            @Override
            public void onError(Throwable e) {
                loginView.showError(e);
            }

            @Override
            public void onComplete() {
                loginView.requestCallBack(loginModel);
                LoginVM.getInstance().setLoginModel(loginModel);
                UserInfoManager.getInstance().setToken(loginModel.getData().getToken());
                UserInfoManager.getInstance().setLoginStatus(true);
            }
        });
    }
}
