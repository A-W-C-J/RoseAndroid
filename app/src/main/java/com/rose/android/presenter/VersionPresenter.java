package com.rose.android.presenter;

import android.util.Log;

import com.rose.android.contract.VersionContract;
import com.rose.android.model.MVersionModel;
import com.rose.android.network.HttpClient;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xiaohuabu on 17/10/14.
 */

public class VersionPresenter extends BasePresenter implements VersionContract.Presenter {
    private MVersionModel versionModel;
    private VersionContract.View view;

    public VersionPresenter(HttpClient httpClient, VersionContract.View view) {
        this.httpClient = httpClient;
        this.view = view;
    }

    @Override
    public void postVersion(int terminalType, String versionName, String packageName) {
        Log.d("NO-CHECK-VERSION", "vertionName:"+versionName);
        // TODO: 20/11/2018 暂时不检测版本 
//        httpClient.postVersion(terminalType, versionName, packageName).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<MVersionModel>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//                disposable = d;
//            }
//
//            @Override
//            public void onNext(MVersionModel mVersionModel) {
//                versionModel = mVersionModel;
//                view.showNext(mVersionModel, false, disposable);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                view.showError(e);
//            }
//
//            @Override
//            public void onComplete() {
//                view.requestCallBack(versionModel);
//            }
//        });
    }

}
