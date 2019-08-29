package com.rose.android.contract;

import io.reactivex.annotations.NonNull;

/**
 * Created by wenen on 2017/11/15.
 */

public interface PutZoomContract {
  interface View extends BaseContract.View {
    void putZoom(@NonNull Long addLoan, String productOrderId);
  }

  interface Presenter extends BaseContract.Presenter {
    void putZoom(@NonNull Long addLoan, String productOrderId);
  }
}
