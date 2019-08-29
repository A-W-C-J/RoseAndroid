package com.rose.android.contract;

/**
 * Created by xiaohuabu on 17/9/14.
 */

public interface GetOrderDetailsContract {
  interface View extends BaseContract.View {
    void getOrderDetails(int orderId);
  }

  interface Presenter extends BaseContract.Presenter {
    void getOrderDetails(int orderId);
  }
}
