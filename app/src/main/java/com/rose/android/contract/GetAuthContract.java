package com.rose.android.contract;

/**
 * Created by xiaohuabu on 17/10/18.
 */

public interface GetAuthContract {
  interface View extends BaseContract.View {
    void getAuth();
  }


  interface Presenter extends BaseContract.Presenter {
    void getAuth();
  }
}
