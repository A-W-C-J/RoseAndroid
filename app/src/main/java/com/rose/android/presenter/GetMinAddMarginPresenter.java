package com.rose.android.presenter;

import com.rose.android.contract.GetMinAddMarginContract;
import com.rose.android.model.MMinAddMarginModel;
import com.rose.android.network.HttpClient;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wenen on 2017/11/16.
 */
public class GetMinAddMarginPresenter extends BasePresenter implements GetMinAddMarginContract.Presenter {
  private GetMinAddMarginContract.View view;
  private MMinAddMarginModel minAddMarginModel;

  public GetMinAddMarginPresenter(GetMinAddMarginContract.View view, HttpClient httpClient) {
    this.view = view;
    this.httpClient = httpClient;
  }

  @Override
  public void getMinAddMargin(String productOrderId) {
    httpClient.getMinAddMargin(productOrderId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<MMinAddMarginModel>() {
          @Override
          public void onSubscribe(Disposable d) {
            disposable = d;
          }

          @Override
          public void onNext(MMinAddMarginModel mMinAddMarginModel) {
            minAddMarginModel = mMinAddMarginModel;
            view.showNext(mMinAddMarginModel, true, disposable);
          }

          @Override
          public void onError(Throwable e) {
            view.showError(e);
          }

          @Override
          public void onComplete() {
            view.requestCallBack(minAddMarginModel);
          }
        });
  }
}
