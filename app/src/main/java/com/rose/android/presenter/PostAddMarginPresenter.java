package com.rose.android.presenter;

import com.rose.android.contract.PostAddMarginContract;
import com.rose.android.model.MAddMarginModel;
import com.rose.android.network.HttpClient;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wenen on 2017/11/16.
 */
public class PostAddMarginPresenter extends BasePresenter implements PostAddMarginContract.Presenter {
    private MAddMarginModel addMarginModel;
    private PostAddMarginContract.View view;

    public PostAddMarginPresenter(HttpClient httpClient, PostAddMarginContract.View view) {
        this.httpClient = httpClient;
        this.view = view;
    }

    @Override
    public void postAddMargin(Long margin, String productOrderId) {
        httpClient.postAddMargin(margin, productOrderId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MAddMarginModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(MAddMarginModel mAddMarginModel) {
                        addMarginModel = mAddMarginModel;
                        view.showNext(addMarginModel, true, disposable);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showError(e);
                    }

                    @Override
                    public void onComplete() {
                        view.requestCallBack(addMarginModel);
                    }
                });
    }

}
