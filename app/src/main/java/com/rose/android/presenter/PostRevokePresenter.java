package com.rose.android.presenter;

import com.rose.android.contract.PostRevokeContract;
import com.rose.android.model.MRevokeModel;
import com.rose.android.network.HttpClient;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xiaohuabu on 17/9/21.
 */
public class PostRevokePresenter extends BasePresenter implements PostRevokeContract.Presenter {
  private MRevokeModel revokeModel;
  private PostRevokeContract.View view;

  public PostRevokePresenter(PostRevokeContract.View view, HttpClient httpClient) {
    this.view = view;
    this.httpClient = httpClient;
  }

  @Override
  public void postRevoke(String stockOrderNo, String productOrderId) {
    httpClient.postRevoke(stockOrderNo, productOrderId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<MRevokeModel>() {
          @Override
          public void onSubscribe(Disposable d) {
            disposable = d;
          }

          @Override
          public void onNext(MRevokeModel mRevokeModel) {
            revokeModel = mRevokeModel;
            view.showNext(mRevokeModel, true, disposable);
          }

          @Override
          public void onError(Throwable e) {
            view.showError(e);
          }

          @Override
          public void onComplete() {
            view.requestCallBack(revokeModel);
          }
        });
  }

}
