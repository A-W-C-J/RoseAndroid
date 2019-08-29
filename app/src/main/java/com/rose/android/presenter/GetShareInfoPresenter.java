package com.rose.android.presenter;

import com.rose.android.contract.GetShareInfoContract;
import com.rose.android.model.MShareInfoModel;
import com.rose.android.network.HttpClient;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wenen on 2017/11/6.
 */
public class GetShareInfoPresenter extends BasePresenter implements GetShareInfoContract.Presenter {
  private MShareInfoModel shareInfoModel;
  private GetShareInfoContract.View view;

  public GetShareInfoPresenter(HttpClient httpClient, GetShareInfoContract.View view) {
    this.httpClient = httpClient;
    this.view = view;
  }

  @Override
  public void getShareInfo() {
    httpClient.getShareInfo().subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<MShareInfoModel>() {
          @Override
          public void onSubscribe(Disposable d) {
            disposable = d;
          }

          @Override
          public void onNext(MShareInfoModel mShareInfoModel) {
            shareInfoModel = mShareInfoModel;
            view.showNext(mShareInfoModel, true, disposable);
          }

          @Override
          public void onError(Throwable e) {
            view.showError(e);
          }

          @Override
          public void onComplete() {
            view.requestCallBack(shareInfoModel);
          }
        });
  }
}
