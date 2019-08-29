package com.rose.android.contract;

import io.reactivex.annotations.Nullable;

/**
 * Created by xiaohuabu on 17/9/13.
 */

public interface GetCouponsContract {
  interface View extends BaseContract.View {
    void getCoupons(@Nullable Integer productId);
  }

  interface Presenter extends BaseContract.Presenter {
    void getCoupons(@Nullable  Integer productId);
  }
}
