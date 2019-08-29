package com.rose.android.presenter;

import com.rose.android.contract.PostCouponsContract;
import com.rose.android.model.MExchangeCouponModel;
import com.rose.android.network.HttpClient;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xiaohuabu on 17/10/18.
 */
public class PostCouponsPresenter extends BasePresenter implements PostCouponsContract.Presenter {
  private PostCouponsContract.View view;
  private MExchangeCouponModel exchangeCouponModel;

  public PostCouponsPresenter(PostCouponsContract.View view, HttpClient httpClient) {
    this.view = view;
    this.httpClient = httpClient;
  }
  @Override
  public void postCoupons(int couponId) {
    httpClient.postCoupons(couponId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<MExchangeCouponModel>() {
      @Override
      public void onSubscribe(Disposable d) {
        disposable = d;
      }

      @Override
      public void onNext(MExchangeCouponModel mCoupons) {
        exchangeCouponModel=mCoupons;
        view.showNext(mCoupons,true,disposable);
      }

      @Override
      public void onError(Throwable e) {
        view.showError(e);
      }

      @Override
      public void onComplete() {
        view.requestCallBack(exchangeCouponModel);
      }
    });
  }

}
