package com.rose.android.contract;

/**
 * Created by wenen on 2017/12/12.
 */

public interface ShoumiPayContract {
  interface View extends BaseContract.View {
    void postShoumiPay(Long amount, Integer source);
  }

  interface Presenter extends BaseContract.Presenter {
    void postShoumiPay(Long amount, Integer source);
  }
}
