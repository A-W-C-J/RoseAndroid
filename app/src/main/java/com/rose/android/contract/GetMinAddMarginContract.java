package com.rose.android.contract;

/**
 * Created by wenen on 2017/11/16.
 */

public interface GetMinAddMarginContract {
  interface View extends BaseContract.View {
    void getMinAddMargin(String productOrderId);
  }

  interface Presenter extends BaseContract.Presenter {
    void getMinAddMargin(String productOrderId);
  }
}
