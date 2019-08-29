package com.rose.android.contract;

/**
 * Created by wenen on 2017/12/6.
 */

public interface GetBroadcastContract {
  interface View extends BaseContract.View {
    void getBroadcast();
  }

  interface Presenter extends BaseContract.Presenter {
    void getBroadcast();
  }
}
