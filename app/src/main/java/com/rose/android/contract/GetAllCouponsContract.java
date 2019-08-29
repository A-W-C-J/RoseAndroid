package com.rose.android.contract;


/**
 * Created by xiaohuabu on 17/10/18.
 */

public interface GetAllCouponsContract {
  interface View extends BaseContract.View {
    void getAllCoupons();
  }

  interface Presenter extends BaseContract.Presenter {
    void getAllCoupons();
  }
}
