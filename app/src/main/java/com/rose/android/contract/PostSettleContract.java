package com.rose.android.contract;

/**
 * Created by xiaohuabu on 17/10/18.
 */

public interface PostSettleContract {
  interface View extends BaseContract.View {
    void postSettle(String productOrderId);
  }

  interface Presenter extends BaseContract.Presenter {
    void postSettle(String productOrderId);
  }
}
