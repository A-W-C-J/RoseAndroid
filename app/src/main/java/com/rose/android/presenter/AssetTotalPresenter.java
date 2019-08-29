package com.rose.android.presenter;

import com.rose.android.contract.AssetTotalContract;
import com.rose.android.model.MAssetTotalModel;
import com.rose.android.network.HttpClient;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xiaohuabu on 17/10/14.
 */
public class AssetTotalPresenter extends BasePresenter implements AssetTotalContract.Presenter {
    private MAssetTotalModel assetTotalModel;
    private AssetTotalContract.View view;

    public AssetTotalPresenter(HttpClient httpClient, AssetTotalContract.View view) {
        this.httpClient = httpClient;
        this.view = view;
    }

    @Override
    public void getAssetTotal() {
        httpClient.getAssetTotal().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MAssetTotalModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(MAssetTotalModel mAssetTotalModel) {
                        assetTotalModel = mAssetTotalModel;
                        view.showNext(mAssetTotalModel, true, disposable);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showError(e);
                    }

                    @Override
                    public void onComplete() {
                        view.requestCallBack(assetTotalModel);
                    }
                });
    }
}
