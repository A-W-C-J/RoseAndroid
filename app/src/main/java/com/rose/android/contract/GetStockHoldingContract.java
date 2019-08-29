package com.rose.android.contract;

import io.reactivex.annotations.Nullable;

/**
 * Created by xiaohuabu on 17/9/21.
 */

public interface GetStockHoldingContract {
  interface View extends BaseContract.View {
    void getStockHolding(int pageNo, @Nullable int pageSize, int productOrderId);
  }

  interface Presenter extends BaseContract.Presenter {
    void getStockHolding(int pageNo, @Nullable int pageSize, int productOrderId);
  }
}
