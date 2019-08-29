package com.rose.android.contract;

/**
 * Created by xiaohuabu on 17/9/20.
 */

public interface ExchangeCouponContract {
  interface View extends BaseContract.View {
    void exchangeCoupon(Integer couponId);
  }

  interface Presenter extends BaseContract.Presenter {
    void exchangeCoupon(Integer couponId);
  }
}
