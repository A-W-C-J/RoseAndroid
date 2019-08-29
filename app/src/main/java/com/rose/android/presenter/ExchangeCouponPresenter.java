package com.rose.android.presenter;

import com.rose.android.contract.ExchangeCouponContract;
import com.rose.android.model.MCouponExchangeModel;
import com.rose.android.network.HttpClient;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xiaohuabu on 17/9/20.
 */
public class ExchangeCouponPresenter extends BasePresenter implements ExchangeCouponContract.Presenter {
  private MCouponExchangeModel couponExchangeModel;
  private ExchangeCouponContract.View view;

  public ExchangeCouponPresenter(ExchangeCouponContract.View view, HttpClient httpClient) {
    this.view = view;
    this.httpClient = httpClient;
  }

  @Override
  public void exchangeCoupon(Integer couponId) {
    httpClient.exchangeCoupon(couponId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<MCouponExchangeModel>() {
          @Override
          public void onSubscribe(Disposable d) {
            disposable = d;
          }

          @Override
          public void onNext(MCouponExchangeModel mCouponExchangeModel) {
            couponExchangeModel = mCouponExchangeModel;
            view.showNext(mCouponExchangeModel, true, disposable);
          }

          @Override
          public void onError(Throwable e) {
            view.showError(e);
          }

          @Override
          public void onComplete() {
            view.requestCallBack(couponExchangeModel);
          }
        });
  }

}
