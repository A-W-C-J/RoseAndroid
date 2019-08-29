package com.rose.android.contract;

/**
 * Created by wenen on 2017/11/20.
 */

public interface PostBrokerageWithdrawContract {
  interface View extends BaseContract.View {
  void postBrokerageWithdraw();
  }

  interface Presenter extends BaseContract.Presenter {
    void postBrokerageWithdraw();
  }
}
