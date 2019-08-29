package com.rose.android.presenter;

import com.rose.android.contract.GetCouponsContract;
import com.rose.android.model.MCoupons;
import com.rose.android.network.HttpClient;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xiaohuabu on 17/9/13.
 */
public class GetCouponsPresenter extends BasePresenter implements GetCouponsContract.Presenter {
  private GetCouponsContract.View view;
  private MCoupons coupons;

  public GetCouponsPresenter(GetCouponsContract.View view, HttpClient httpClient) {
    this.view = view;
    this.httpClient = httpClient;
  }

  @Override
  public void getCoupons(Integer productId) {
    httpClient.getCoupons(productId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<MCoupons>() {
      @Override
      public void onSubscribe(Disposable d) {
        disposable = d;
      }

      @Override
      public void onNext(MCoupons mCoupons) {
        coupons = mCoupons;
        view.showNext(mCoupons, true, disposable);
      }

      @Override
      public void onError(Throwable e) {
        view.showError(e);
      }

      @Override
      public void onComplete() {
        view.requestCallBack(coupons);
      }
    });
  }
}
