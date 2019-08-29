package com.rose.android.presenter;

import com.rose.android.contract.PostSettleContract;
import com.rose.android.model.MSettleModel;
import com.rose.android.network.HttpClient;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xiaohuabu on 17/10/18.
 */
public class PostSettlePresenter extends BasePresenter implements PostSettleContract.Presenter {
  private PostSettleContract.View view;
  private MSettleModel settleModel;

  public PostSettlePresenter(PostSettleContract.View view, HttpClient httpClient) {
    this.view = view;
    this.httpClient = httpClient;
  }

  @Override
  public void postSettle(String productOrderId) {
    httpClient.postSettle(productOrderId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<MSettleModel>() {
          @Override
          public void onSubscribe(Disposable d) {
            disposable = d;
          }

          @Override
          public void onNext(MSettleModel mSettleModel) {
            settleModel = mSettleModel;
            view.showNext(mSettleModel, true, disposable);
          }

          @Override
          public void onError(Throwable e) {
            view.showError(e);
          }

          @Override
          public void onComplete() {
            view.requestCallBack(settleModel);
          }
        });
  }

}
