package com.rose.android.presenter;

import com.rose.android.contract.GetLimitedListContract;
import com.rose.android.model.MLimitedBuyListModel;
import com.rose.android.network.HttpClient;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xiaohuabu on 17/9/19.
 */
public class GetLimitedListPresenter extends BasePresenter implements GetLimitedListContract.Presenter {
  private GetLimitedListContract.View view;
  private MLimitedBuyListModel limitedBuyListModel;

  public GetLimitedListPresenter(GetLimitedListContract.View view, HttpClient httpClient) {
    this.view = view;
    this.httpClient = httpClient;
  }

  @Override
  public void getLimitedList() {
    httpClient.getLimitedList().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<MLimitedBuyListModel>() {
      @Override
      public void onSubscribe(Disposable d) {
        disposable = d;
      }

      @Override
      public void onNext(MLimitedBuyListModel mLimitedBuyListModel) {
        limitedBuyListModel = mLimitedBuyListModel;
        view.showNext(mLimitedBuyListModel, true, disposable);
      }

      @Override
      public void onError(Throwable e) {
        view.showError(e);
      }

      @Override
      public void onComplete() {
        view.requestCallBack(limitedBuyListModel);
      }
    });
  }
}
