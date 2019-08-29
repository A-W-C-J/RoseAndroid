package com.rose.android.contract;

import io.reactivex.annotations.NonNull;

/**
 * Created by wenen on 2017/11/15.
 */

public interface GetZoomInfoContract {
  interface View extends BaseContract.View {
    void getZoomInfo(@NonNull Long addLoan, String productOrderId);
  }

  interface Presenter extends BaseContract.Presenter {
    void getZoomInfo(@NonNull Long addLoan, String productOrderId);
  }
}
