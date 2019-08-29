package com.rose.android.presenter.newstruct;

import com.rose.android.UserInfoManager;
import com.rose.android.contract.newstruct.UserInfoContract;
import com.rose.android.model.newstruct.UserAccountModel;
import com.rose.android.model.newstruct.UserInfoModel;
import com.rose.android.network.HttpClient;
import com.rose.android.presenter.BasePresenter;
import com.rose.android.viewmodel.LoginVM;
import com.rose.android.viewmodel.UserVM;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class UserInfoPresenter extends BasePresenter implements UserInfoContract.Presenter {

    private UserInfoModel userInfoModel;
    private UserAccountModel userAccountModel;
    private UserInfoContract.View view;

    public UserInfoPresenter(HttpClient httpClient, UserInfoContract.View view) {
        this.httpClient = httpClient;
        this.view = view;
    }

    @Override
    public void userAuthenticate(String name, String idCardNo) {

    }

    @Override
    public void updateUserInfo(String nickname, String name, String idCardNo, Integer gender) {

    }

    @Override
    public void requestUserInfo() {
        httpClient.requestUserInfo().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<UserInfoModel>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onNext(UserInfoModel apiResponse) {
                userInfoModel = apiResponse;
                view.showNext(userInfoModel, false, disposable);
            }

            @Override
            public void onError(Throwable e) {
                view.showError(e);
            }

            @Override
            public void onComplete() {
                if (null != userInfoModel) {
                    UserVM.getInstance().setUserInfoModel(userInfoModel);
                    LoginVM.getInstance().udpateUserInfo(userInfoModel);
                    UserInfoManager.getInstance().setToken(userInfoModel.getData().getToken());
                    UserInfoManager.getInstance().setLoginStatus(true);
                    view.requestCallBack(userInfoModel);
                }
            }
        });
    }

    @Override
    public void requestUserBank() {

    }

    @Override
    public void bindUserBank(String bankName, String bankCardNo) {

    }

    @Override
    public void requestUserAccount() {
        httpClient.requestUserAccount().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<UserAccountModel>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onNext(UserAccountModel apiResponse) {
                userAccountModel = apiResponse;
                view.showNext(userAccountModel, false, disposable);
            }

            @Override
            public void onError(Throwable e) {
                view.showError(e);
            }

            @Override
            public void onComplete() {
                if (null != userAccountModel) {
                    UserVM.getInstance().setUserAccountModel(userAccountModel);
                    UserInfoManager.getInstance().setLoginStatus(true);
                    view.requestCallBack(userAccountModel);
                }
            }
        });
    }
}
