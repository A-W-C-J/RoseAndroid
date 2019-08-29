package com.rose.android.contract;

/**
 * Created by xiaohuabu on 17/9/20.
 */

public interface GetUserInfoContract {
  interface View extends BaseContract.View {
    void getUserInfo();
  }


  interface Presenter extends BaseContract.Presenter {
    void getUserInfo();
  }
}
