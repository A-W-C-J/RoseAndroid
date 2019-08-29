package com.rose.android.contract;


/**
 * Created by xiaohuabu on 17/10/18.
 */

public interface PostCouponsContract {
  interface View extends BaseContract.View {
    void postCoupons(int couponId);
  }

  interface Presenter extends BaseContract.Presenter {
    void postCoupons(int couponId);
  }
}
