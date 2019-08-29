package com.rose.android.contract;

import io.reactivex.annotations.NonNull;

/**
 * Created by wenen on 2017/11/16.
 */

public interface PostAddMarginContract {
  interface View extends BaseContract.View {
    void postAddMargin(@NonNull Long margin, String productOrderId);
  }

  interface Presenter extends BaseContract.Presenter {
    void postAddMargin(@NonNull Long margin, String productOrderId);
  }
}
