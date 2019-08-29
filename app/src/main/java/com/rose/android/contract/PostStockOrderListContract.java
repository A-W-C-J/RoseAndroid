package com.rose.android.contract;

import io.reactivex.annotations.Nullable;

/**
 * Created by xiaohuabu on 17/9/21.
 */

public interface PostStockOrderListContract {
  interface View extends BaseContract.View {
    void postStockOrderList(int productOrderId, @Nullable Integer orderStatus, @Nullable Integer timeStatus, int pageNo, int pageSize);
  }

  interface Presenter extends BaseContract.Presenter {
    void postStockOrderList(int productOrderId, @Nullable Integer orderStatus, @Nullable Integer timeStatus, int pageNo, int pageSize);
  }
}
