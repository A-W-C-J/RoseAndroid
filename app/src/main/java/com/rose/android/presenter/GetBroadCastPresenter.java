package com.rose.android.presenter;

import com.rose.android.contract.GetBroadcastContract;
import com.rose.android.model.MProductOrderBrodcastModel;
import com.rose.android.network.HttpClient;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wenen on 2017/12/6.
 */
public class GetBroadCastPresenter extends BasePresenter implements GetBroadcastContract.Presenter {
  private GetBroadcastContract.View view;
  private MProductOrderBrodcastModel productOrderBrodcastModel;

  public GetBroadCastPresenter(HttpClient httpClient, GetBroadcastContract.View view) {
    this.httpClient = httpClient;
    this.view = view;
  }

  @Override
  public void getBroadcast() {
    httpClient.getBrodcast().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<MProductOrderBrodcastModel>() {
          @Override
          public void onSubscribe(Disposable d) {
            disposable = d;
          }

          @Override
          public void onNext(MProductOrderBrodcastModel mProductOrderBrodcastModel) {
            productOrderBrodcastModel = mProductOrderBrodcastModel;
            view.showNext(productOrderBrodcastModel, false, disposable);
          }

          @Override
          public void onError(Throwable e) {
            view.showError(e);
          }

          @Override
          public void onComplete() {
            view.requestCallBack(productOrderBrodcastModel);
          }
        });
  }
}
