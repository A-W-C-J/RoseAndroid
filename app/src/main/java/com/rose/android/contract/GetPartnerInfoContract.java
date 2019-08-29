package com.rose.android.contract;

/**
 * Created by wenen on 2017/11/6.
 */

public interface GetPartnerInfoContract {
  interface View extends BaseContract.View {
    void getPartnerInfo();
  }

  interface Presenter extends BaseContract.Presenter {
    void getPartnerInfo();
  }
}
