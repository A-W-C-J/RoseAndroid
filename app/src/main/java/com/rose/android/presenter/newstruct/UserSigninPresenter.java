package com.rose.android.presenter.newstruct;

import android.util.Log;

import com.rose.android.UserInfoManager;
import com.rose.android.contract.newstruct.UserSigninContract;
import com.rose.android.model.newstruct.UserSigninModel;
import com.rose.android.presenter.BasePresenter;

import com.rose.android.network.HttpClient;
import com.rose.android.viewmodel.LoginVM;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class UserSigninPresenter extends BasePresenter implements UserSigninContract.Presenter {

    private UserSigninModel userSigninModel;
    private UserSigninContract.View view;

    public UserSigninPresenter(HttpClient httpClient, UserSigninContract.View view) {
        this.httpClient = httpClient;
        this.view = view;
    }

    @Override
    public void signup(String phone, String password, String captcha, String promotionCode) {

    }

    @Override
    public void signin(String phone, String password) {
        httpClient.signin(phone, password).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<UserSigninModel>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onNext(UserSigninModel apiResponse) {
                userSigninModel = apiResponse;
                view.showNext(userSigninModel, false, disposable);
            }

            @Override
            public void onError(Throwable e) {
                view.showError(e);
            }

            @Override
            public void onComplete() {
                view.requestCallBack(userSigninModel);
                Log.d("USER-SIGNIN", "userSigninModel:" + userSigninModel.toString());
                LoginVM.getInstance().setUserSigninModel(userSigninModel);
                UserInfoManager.getInstance().setToken(userSigninModel.getData().getToken());
                UserInfoManager.getInstance().setLoginStatus(true);
            }
        });
    }

    @Override
    public void signout() {

    }

    @Override
    public void requestCaptcha(String phone) {

    }

    @Override
    public void resetSigninPassword(String phone, String password, String captcha) {

    }
}
