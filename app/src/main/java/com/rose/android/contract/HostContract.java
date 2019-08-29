package com.rose.android.contract;

/**
 * Created by wenen on 2017/11/1.
 */

public interface HostContract {
  interface View extends BaseContract.View {
  void getHost();
  }

  interface Presenter extends BaseContract.Presenter {
    void getHost();
  }
}
