package com.rose.android.contract;

/**
 * Created by wenen on 2017/11/6.
 */

public interface GetShareInfoContract {
  interface View extends BaseContract.View{
    void getShareInfo();
  }

  interface Presenter extends BaseContract.Presenter {
    void getShareInfo();
  }
}
